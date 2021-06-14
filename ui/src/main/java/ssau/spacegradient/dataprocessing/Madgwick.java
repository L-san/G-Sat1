package ssau.spacegradient.dataprocessing;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ssau.spacegradient.clientapp.client.converter.DataContainer;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;

@Component
public class Madgwick implements Algorithm {
    private boolean isAlive = true;
    private double zeta;
    private double beta;
    private double dt;
    private double accelerometerLSB;
    private double magnetometerLSB;
    private double gyroscopeLSB;//70 mdps/LSB;
    private Filter filter = new Filter();
    private String message;
    private String status;

    private double[] q_est = new double[]{1, 0, 0, 0};
    private double w_bx, w_by, w_bz;
    private DataContainer data = new DataContainer();
    private ProcessedData processedData = new ProcessedData();
    private Consumer<? super ProcessedData> consumer;

    public Madgwick() {
        setSettings(new MadgwickSettings());
        try (FileWriter writerKalman = new FileWriter("dataFiltered.txt", false);
             FileWriter writerMadgwick = new FileWriter("dataMadgwick.txt", false)) {
            writerKalman.write(new Date().toString() + "\n kalman from accelerometer magnetometer gyroscope\n");
            writerMadgwick.write(new Date().toString() + "\n quaternion from madgwick [q0 q1 q2 q3]\n");
            writerKalman.flush();
            writerMadgwick.flush();

        } catch (Exception ignored) {
        }
    }

    @Override
    public double[] getQuaternion() {
        return this.q_est;
    }

    @Override
    public void setConsumer(Consumer<? super ProcessedData> consumer) {
        this.consumer = consumer;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double wx, wy, wz, w_eps_x, w_eps_y, w_eps_z;
        //System.out.println(q_est0+" "+ q_est1+" "+q_est2+" "+q_est3);
        //gyro measurements
        /*double[] shift = new double[]{21.4580000000000,	-68.0660000000000,	-5.88000000000000};
        g[0] -= shift[0];
        g[1] -= shift[1];
        g[2] -= shift[2];*/
        filter.doFiltering(new double[]{a[0] / accelerometerLSB,
                a[1] / accelerometerLSB,
                a[2] / accelerometerLSB,
                m[0] / magnetometerLSB,
                m[1] / magnetometerLSB,
                m[2] / magnetometerLSB,
                g[0] * Math.PI / 180 * gyroscopeLSB,
                g[1] * Math.PI / 180 * gyroscopeLSB,
                g[2] * Math.PI / 180 * gyroscopeLSB});
        double[] z = filter.getX_hat();
        a = new double[]{z[0], z[1], z[2]};
        m = new double[]{z[3], z[4], z[5]};
        g = new double[]{z[6], z[7], z[8]};
        data = new DataContainer(a, m, g);
        data.setMessage(message);
        data.setStatus(status);
        saveProcessedData();

        wx = g[0];
        wy = g[1];
        wz = g[2];

        double nm = Math.sqrt(m[0] * m[0] + m[1] * m[1] + m[2] * m[2]);
        if (nm == 0) {
            nm = 1;
        }
        m[0] /= nm;
        m[1] /= nm;
        m[2] /= nm;

        double na = Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
        if (na == 0) {
            na = 1;
        }
        a[0] /= na;
        a[1] /= na;
        a[2] /= na;

        //gradient descent algorithm
        double[] f_a = new double[]{
                2 * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - a[0],
                2 * (q_est[0] * q_est[1] + q_est[2] * q_est[3]) - a[1],
                2 * (0.5 - q_est[1] * q_est[1] - q_est[1] * q_est[1]) - a[2]};

        double[][] J_a = new double[][]{
                {-2 * q_est[2], 2 * q_est[3], -2 * q_est[0], 2 * q_est[1]},
                {2 * q_est[1], 2 * q_est[0], 2 * q_est[3], 2 * q_est[2]},
                {0, -4 * q_est[1], -4 * q_est[2], 0}};

        //direction of the magnetic field
        double h_x, h_y, h_z;
        h_x = 2 * (m[2] * q_est[0] * q_est[2] + m[1] * q_est[1] * q_est[2] - m[1] * q_est[0] * q_est[3] + m[2] * q_est[1] * q_est[3]) + m[0] * (q_est[0] * q_est[0] + q_est[1] * q_est[1] - q_est[2] * q_est[2] - q_est[3] * q_est[3]);
        h_y = 2 * (-m[2] * q_est[0] * q_est[1] + m[0] * q_est[1] * q_est[2] + m[0] * q_est[0] * q_est[3] + m[2] * q_est[2] * q_est[3]) + m[1] * (q_est[0] * q_est[0] - q_est[1] * q_est[1] + q_est[2] * q_est[2] - q_est[3] * q_est[3]);
        h_z = 2 * (m[1] * q_est[0] * q_est[1] - m[0] * q_est[0] * q_est[2] + m[0] * q_est[1] * q_est[3] + m[1] * q_est[2] * q_est[3]) + m[2] * (q_est[0] * q_est[0] - q_est[1] * q_est[1] - q_est[2] * q_est[2] + q_est[3] * q_est[3]);

        //reference direction of the magnetic field
        double b_x, b_z;
        b_x = Math.sqrt(h_x * h_x + h_y * h_y);
        b_z = h_z;

        double[] f_m = new double[]{
                2 * b_x * (0.5 - q_est[2] * q_est[2] - q_est[3] * q_est[3]) + 2 * b_z * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - m[0],
                2 * b_x * (q_est[1] * q_est[2] - q_est[0] * q_est[3]) + 2 * b_z * (q_est[1] * q_est[0] + q_est[3] * q_est[2]) - m[1],
                2 * b_x * (q_est[0] * q_est[2] + q_est[1] * q_est[3]) + 2 * b_z * (0.5 - q_est[1] * q_est[1] - q_est[2] * q_est[2]) - m[2]};

        double[][] J_m = new double[][]{
                {-2 * b_z * q_est[2], 2 * b_z * q_est[3], -4 * b_x * q_est[2] - 2 * b_z * q_est[0], -4 * b_x * q_est[3] + 2 * b_z * q_est[1]},
                {-2 * b_x * q_est[3] + 2 * b_z * q_est[1], 2 * b_x * q_est[2] + 2 * b_z * q_est[0], 2 * b_x * q_est[1] + 2 * b_z * q_est[3], -2 * b_x * q_est[0] + 2 * b_z * q_est[2]},
                {2 * b_x * q_est[2], 2 * b_x * q_est[3] - 4 * b_z * q_est[1], 2 * b_x * q_est[0] - 4 * b_z * q_est[2], 2 * b_x * q_est[1]}};

        double[] gradient = new double[]{
                f_a[0] * J_a[0][0] + f_a[1] * J_a[1][0] + f_a[2] * J_a[2][0] + f_m[0] * J_m[0][0] + f_m[1] * J_m[1][0] + f_m[2] * J_m[2][0],
                f_a[0] * J_a[0][1] + f_a[1] * J_a[1][1] + f_a[2] * J_a[2][1] + f_m[0] * J_m[0][1] + f_m[1] * J_m[1][1] + f_m[2] * J_m[2][1],
                f_a[0] * J_a[0][2] + f_a[1] * J_a[1][2] + f_a[2] * J_a[2][2] + f_m[0] * J_m[0][2] + f_m[1] * J_m[1][2] + f_m[2] * J_m[2][2],
                f_a[0] * J_a[0][3] + f_a[1] * J_a[1][3] + f_a[2] * J_a[2][3] + f_m[0] * J_m[0][3] + f_m[1] * J_m[1][3] + f_m[2] * J_m[2][3]};

        //in case of захочется только на акселерометре, вот, пжлста
        /*double[] gradient = new double[]{
                f_a[0] * J_a[0][0] + f_a[1] * J_a[1][0] + f_a[2] * J_a[2][0],
                f_a[0] * J_a[0][1] + f_a[1] * J_a[1][1] + f_a[2] * J_a[2][1],
                f_a[0] * J_a[0][2] + f_a[1] * J_a[1][2] + f_a[2] * J_a[2][2],
                f_a[0] * J_a[0][3] + f_a[1] * J_a[1][3] + f_a[2] * J_a[2][3]};*/

        double n = Math.sqrt(gradient[0] * gradient[0] + gradient[1] * gradient[1] + gradient[2] * gradient[2] + gradient[3] * gradient[3]);
        if (n == 0) {
            n = 1;
        }
        gradient[0] /= n;
        gradient[1] /= n;
        gradient[2] /= n;
        gradient[3] /= n;

        w_eps_x = 2 * (gradient[1] * q_est[0] - gradient[0] * q_est[1] - gradient[3] * q_est[2] + gradient[2] * q_est[3]);
        w_eps_y = 2 * (gradient[2] * q_est[0] + gradient[3] * q_est[1] - gradient[0] * q_est[2] - gradient[1] * q_est[3]);
        w_eps_z = 2 * (gradient[3] * q_est[0] - gradient[2] * q_est[1] + gradient[1] * q_est[2] - gradient[0] * q_est[3]);

        w_bx += w_eps_x * zeta * dt;
        w_by += w_eps_y * zeta * dt;
        w_bz += w_eps_z * zeta * dt;

        //use compensated w in poisson's equations!!!
        wx -= w_bx;
        wy -= w_by;
        wz -= w_bz;

        double[] q_omega_dot = new double[4];
        q_omega_dot[0] = -0.5 * (q_est[1] * wx + q_est[2] * wy + q_est[3] * wz);
        q_omega_dot[1] = 0.5 * (q_est[0] * wx + q_est[2] * wz - q_est[3] * wy);
        q_omega_dot[2] = 0.5 * (q_est[0] * wy + q_est[3] * wx - q_est[1] * wz);
        q_omega_dot[3] = 0.5 * (q_est[0] * wz + q_est[1] * wy - q_est[2] * wx);

        //double[] betas = calculateBeta(meanOmega);
        double[] q_est_dot = new double[4];
        q_est_dot[0] = q_omega_dot[0] - beta * gradient[0];
        q_est_dot[1] = q_omega_dot[1] - beta * gradient[1];
        q_est_dot[2] = q_omega_dot[2] - beta * gradient[2];
        q_est_dot[3] = q_omega_dot[3] - beta * gradient[3];

        //poisson's equations
        q_est[0] += q_est_dot[0] * dt;
        q_est[1] += q_est_dot[1] * dt;
        q_est[2] += q_est_dot[2] * dt;
        q_est[3] += q_est_dot[3] * dt;

        //System.out.println("qe0: " + q_est_dot[0] + " qe1: " + q_est_dot[1] + " qe2: " + q_est_dot[2] + " qe3: " + q_est_dot[3]);

        double nq = Math.sqrt(q_est[0] * q_est[0] + q_est[1] * q_est[1] + q_est[2] * q_est[2] + q_est[3] * q_est[3]);
        if (nq == 0) {
            nq = 1;
        }
        q_est[0] /= nq;
        q_est[1] /= nq;
        q_est[2] /= nq;
        q_est[3] /= nq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                isAlive = true;
            }
            isAlive = false;
        } catch (Exception exception) {
            isAlive = false;
        }
    }


    @Override
    public void accept(DataContainer dataContainer) {
        if (isAlive && (consumer != null)) {
            this.data = dataContainer;
            message = dataContainer.getMessage();
            status = dataContainer.getStatus();
            calculatePosition(data.getAccelerometer(), data.getMagnetometer(), data.getGyroscope());
            processedData.setQ(q_est);
            processedData.setRawData(data);
            receiveData().subscribe(consumer);
        }
    }

    public Flux<ProcessedData> receiveData() {
        return Flux.fromArray(new ProcessedData[]{processedData});
    }

    public void setSettings(MadgwickSettings set) {
        this.dt = set.getDt();
        this.accelerometerLSB = set.getAccelerometerLSB();
        this.magnetometerLSB = set.getMagnetometerLSB();
        this.gyroscopeLSB = set.getGyroscopeLSB();
        this.dt = set.getDt();
        this.beta = set.getBeta();
        this.zeta = set.getZeta();
    }

    public void saveProcessedData() {
        try (FileWriter writerKalman = new FileWriter("dataFiltered.txt", true);
             FileWriter writerMadgwick = new FileWriter("dataMadgwick.txt", true)) {
            writerKalman.write(data.getData());
            writerMadgwick.write(Arrays.toString(processedData.getQ()) + "\n");
            writerKalman.flush();
            writerMadgwick.flush();

        } catch (Exception ignored) {
        }
    }
}
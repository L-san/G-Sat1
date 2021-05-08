package ssau.spacegradient.clientapp.client.converter;

import java.util.Arrays;

public class DataContainer {
    private final double[] accelerometer;

    private final double[] magnetometer;

    private final double[] gyroscope;

    private String status = "ok";

    private String message;

    public DataContainer() {
        this.accelerometer = new double[]{0,0,0};
        this.magnetometer = new double[]{0,0,0};
        this.gyroscope = new double[]{0,0,0};
    }

    public DataContainer(double[] accelerometer, double[] magnetometer, double[] gyroscope) {
        this.accelerometer = accelerometer;
        this.magnetometer = magnetometer;
        this.gyroscope = gyroscope;
    }

    public double[] getAccelerometer() {
        return accelerometer;
    }

    public double[] getMagnetometer() {
        return magnetometer;
    }

    public double[] getGyroscope() {
        return gyroscope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "accelerometer: " + Arrays.toString(accelerometer) +
                "\nmagnetometer:  " + Arrays.toString(magnetometer) +
                "\ngyrocsope:     " + Arrays.toString(gyroscope)+
                "\nStatus: "+status+
                "\nMessage: "+message;
    }

    public String getData() {
        return Arrays.toString(accelerometer) +
                " " + Arrays.toString(magnetometer) +
                " " + Arrays.toString(gyroscope)+
                "\n";
    }
}

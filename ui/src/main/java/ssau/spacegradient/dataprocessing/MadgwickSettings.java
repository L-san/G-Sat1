package ssau.spacegradient.dataprocessing;

import javafx.scene.control.TextField;

public class MadgwickSettings {
    private double zeta = 0.01;
    private double beta = 0.01;
    private double dt = 0.01;
    private double accelerometerLSB = 1;
    private double magnetometerLSB = 1;
    private double gyroscopeLSB = 70*1e-3;//70 mdps/LSB

    public MadgwickSettings(){}

    public MadgwickSettings( String beta, String zeta,
                             String accelerometerLSB, String magnetometerLSB, String gyroscopeLSB, String dt) throws Exception {
        try{
            if (!beta.isEmpty()) {
                this.beta = Double.parseDouble(beta);
            }

            if (!zeta.isEmpty()) {
                this.zeta = Double.parseDouble(zeta);
            }

            if (!accelerometerLSB.isEmpty()) {
                this.accelerometerLSB = Double.parseDouble(accelerometerLSB);
            }

            if (!magnetometerLSB.isEmpty()) {
                this.magnetometerLSB = Double.parseDouble(magnetometerLSB);
            }

            if (!gyroscopeLSB.isEmpty()) {
                this.gyroscopeLSB = Double.parseDouble(gyroscopeLSB);
            }
            if(!dt.isEmpty()){
                this.dt = Double.parseDouble(dt);
            }

        }catch (Exception exception){
            throw new Exception("Incorrect telemetry properties");
        }
    }

    public double getZeta() {
        return zeta;
    }

    public double getBeta() {
        return beta;
    }

    public double getDt() {
        return dt;
    }

    public double getAccelerometerLSB() {
        return accelerometerLSB;
    }

    public double getMagnetometerLSB() {
        return magnetometerLSB;
    }

    public double getGyroscopeLSB() {
        return gyroscopeLSB;
    }
}

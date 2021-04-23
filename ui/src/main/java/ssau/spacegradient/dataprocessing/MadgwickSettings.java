package ssau.spacegradient.dataprocessing;

import javafx.scene.control.TextField;

public class MadgwickSettings {
    private double zeta = 0;
    private double beta = 0;
    private double dt = 0.01;
    private double accelerometerLSB = 1;
    private double magnetometerLSB = 1;
    private double gyroscopeLSB = 1;//Math.PI * 0.07 / 180;//70 mdps/LSB
    private double rCoeff = 0;
    private double qCoeff = 0;

    public MadgwickSettings(){}

    public MadgwickSettings( String beta, String zeta,
                             String accelerometerLSB, String magnetometerLSB, String gyroscopeLSB,
                             String rCoeff, String qCoeff) throws Exception {
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

            if (!rCoeff.isEmpty()) {
                this.rCoeff = Double.parseDouble(rCoeff);
            }

            if (!qCoeff.isEmpty()) {
                this.qCoeff = Double.parseDouble(qCoeff);
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

    public double getrCoeff() {
        return rCoeff;
    }

    public double getqCoeff() {
        return qCoeff;
    }
}

package ssau.spacegradient.dataprocessing;

import javafx.scene.control.TextField;
import ssau.spacegradient.clientapp.client.converter.DataContainer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Exchanger;

public class MadgwickSettings {
    private double zeta = 0;
    private double beta = 0;
    private double dt = 0.01;
    private double accelerometerLSB = 1;
    private double magnetometerLSB = 1;
    private double gyroscopeLSB = Math.PI * 0.07 / 180;//Math.PI * 0.07 / 180;//70 mdps/LSB
    private ArrayBlockingQueue<ProcessedData> exchanger;
    private ArrayBlockingQueue<DataContainer> exchangerRX;

    public MadgwickSettings(){}

    public MadgwickSettings( String beta, String zeta,
                             String accelerometerLSB, String magnetometerLSB, String gyroscopeLSB, String dt, ArrayBlockingQueue<ProcessedData> exchanger,ArrayBlockingQueue<DataContainer> exchangerRX) throws Exception {
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
            this.exchanger = exchanger;
            this.exchangerRX = exchangerRX;

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

    public ArrayBlockingQueue<ProcessedData> getExchanger() {
        return exchanger;
    }

    public ArrayBlockingQueue<DataContainer> getExchangerRX() {
        return exchangerRX;
    }
}

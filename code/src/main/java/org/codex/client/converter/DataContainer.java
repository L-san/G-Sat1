package org.codex.client.converter;

import java.util.Arrays;

public class DataContainer {
    private double[] accelerometer;

    private double[] magnetometer;

    private double[] gyroscope;

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

    @Override
    public String toString() {
        return "accelerometer: "+ Arrays.toString(accelerometer) +"\nmagnetometer:  "+ Arrays.toString(magnetometer) +"\ngyrocsope:     "+ Arrays.toString(gyroscope);
    }
}

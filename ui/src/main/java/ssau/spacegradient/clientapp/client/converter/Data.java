package ssau.spacegradient.clientapp.client.converter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("accelerometer")
    @Expose
    private List<Integer> accelerometer = null;
    @SerializedName("magnetometer")
    @Expose
    private List<Double> magnetometer = null;
    @SerializedName("gyroscope")
    @Expose
    private List<Integer> gyroscope = null;

    public List<Integer> getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(List<Integer> accelerometer) {
        this.accelerometer = accelerometer;
    }

    public List<Double> getMagnetometer() {
        return magnetometer;
    }

    public void setMagnetometer(List<Double> magnetometer) {
        this.magnetometer = magnetometer;
    }

    public List<Integer> getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(List<Integer> gyroscope) {
        this.gyroscope = gyroscope;
    }

}


package ssau.spacegradient.clientapp.client.converter;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

//@ProtoClass(ProtocolBuffersConverter.class)
public class Pojo {
   // @ProtoField
    double[] accelerometer;
   // @ProtoField
    double[] gyroscope;
   // @ProtoField
    double[] magnetometer;

    public Pojo(double[] accelerometer, double[] gyroscope, double[] magnetometer) {
        this.accelerometer = accelerometer;
        this.gyroscope = gyroscope;
        this.magnetometer = magnetometer;
    }

    public double[] getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(double[] accelerometer) {
        this.accelerometer = accelerometer;
    }

    public double[] getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(double[] gyroscope) {
        this.gyroscope = gyroscope;
    }

    public double[] getMagnetometer() {
        return magnetometer;
    }

    public void setMagnetometer(double[] magnetometer) {
        this.magnetometer = magnetometer;
    }
}
/*package ssau.spacegradient.clientapp.client.converter;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Pojo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}*/

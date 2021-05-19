package ssau.spacegradient.clientapp.client.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;

public class JsonConverter extends AbstractConverter {
    /*{
  "name":"inertial",
  "data": {
    "accelerometer":[0,0,1],
    "magnetometer":[0.7,0,0.7],
    "gyroscope":[0,0,0]
  }
}*/

    public DataContainer convert(String message) {
        /*Gson gson = new Gson();
        Pojo read = gson.fromJson(message, Pojo.class);
        DataContainer container = new DataContainer(read.getAccelerometer(), read.getMagnetometer(), read.getGyroscope());
        container.setMessage(message);*/
        double[] accelerometer = new double[3];
        double[] magnetometer = new double[3];
        double[] gyroscope = new double[3];
        String substringAccelerometer = "\"accelerometer\":[";
        String substringMagnetometer = "],\"magnetometer\":[";
        String substringGyroscope = "],\"gyroscope\":[";
        try {
            String[] accelerometerStr = message.substring(
                    message.indexOf(substringAccelerometer) + substringAccelerometer.length(),
                    message.indexOf(substringMagnetometer))
                    .split(",");

            String[] magnetometerStr = message.substring(
                    message.indexOf(substringMagnetometer) + substringMagnetometer.length(),
                    message.indexOf(substringGyroscope))
                    .split(",");

            String[] gyroscopeStr = message.substring(
                    message.indexOf(substringGyroscope) + substringGyroscope.length(),
                    message.indexOf("]}"))
                    .split(",");

            for (int i = 0; i < 3; i++) {
                accelerometer[i] = Double.parseDouble(accelerometerStr[i]);
                magnetometer[i] = Double.parseDouble(magnetometerStr[i]);
                gyroscope[i] = Double.parseDouble(gyroscopeStr[i]);
            }
        } catch (Exception exception) {
        }
        return new DataContainer(accelerometer, magnetometer, gyroscope);
        //return container;
    }
}

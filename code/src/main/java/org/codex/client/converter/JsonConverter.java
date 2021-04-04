package org.codex.client.converter;

public class JsonConverter extends AbstractConverter {
    /*{
  "name":"inertial",
  "data": {
    "accelerometer": [0,0,1],
    "magnetometer": [0.7,0,0.7],
    "gyroscope": [0,0,0]
  }
}*/

    public DataContainer convert(String message) {
        double[] accelerometer = new double[3];
        double[] magnetometer = new double[3];
        double[] gyroscope = new double[3];

        try {
            String[] accelerometerStr = message.substring(
                    message.indexOf("accelerometer") + 17,
                    message.indexOf("magnetometer") - 8)
                    .split(",");

            String[] magnetometerStr = message.substring(
                    message.indexOf("magnetometer") + 16,
                    message.indexOf("gyroscope") - 8)
                    .split(",");

            String[] gyroscopeStr = message.substring(
                    message.indexOf("gyroscope") + 13,
                    message.indexOf("}") - 4)
                    .split(",");

            for (int i = 0; i < 3; i++) {
                accelerometer[i] = Double.parseDouble(accelerometerStr[i]);
                magnetometer[i] = Double.parseDouble(magnetometerStr[i]);
                gyroscope[i] = Double.parseDouble(gyroscopeStr[i]);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new DataContainer(accelerometer,magnetometer,gyroscope);
    }
}

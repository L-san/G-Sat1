package ssau.spacegradient.clientapp.client.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;

public class JsonConverter extends AbstractConverter {
    public DataContainer convert(String message) {
        Gson gson = new Gson();
        Pojo read = gson.fromJson(message, Pojo.class);
        DataContainer container = new DataContainer(read.getAccelerometer(), read.getMagnetometer(), read.getGyroscope());
        container.setMessage(message);
        return container;
    }
}

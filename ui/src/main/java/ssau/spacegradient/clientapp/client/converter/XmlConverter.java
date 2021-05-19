package ssau.spacegradient.clientapp.client.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlConverter extends AbstractConverter{

    public DataContainer convert(String message) {
        XmlMapper xmlMapper = new XmlMapper();
        Pojo read = null;
        try {
            read = xmlMapper.readValue(message, Pojo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataContainer container = new DataContainer(read.getAccelerometer(), read.getMagnetometer(), read.getGyroscope());
        return container;
    }
}

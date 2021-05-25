package ssau.spacegradient.clientapp.client.converter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class XmlConverter extends AbstractConverter{

    public DataContainer convert(String message) throws JAXBException {
        SendingFile read;
        JAXBContext context = JAXBContext.newInstance(SendingFile.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(String.valueOf(message));
        read = (SendingFile) unmarshaller.unmarshal(reader);
        return new DataContainer(read.getAccelerometer(), read.getMagnetometer(), read.getGyroscope());
    }
}

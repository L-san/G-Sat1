package ssau.spacegradient.clientapp.client.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlConverterTest {
    String str = "ascii: <?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<sendingFile>\n" +
            "    <accelerometer>334.0</accelerometer>\n" +
            "    <accelerometer>-108.0</accelerometer>\n" +
            "    <accelerometer>-941.0</accelerometer>\n" +
            "    <gyroscope>-17494.0</gyroscope>\n" +
            "    <gyroscope>62.0</gyroscope>\n" +
            "    <gyroscope>-187.0</gyroscope>\n" +
            "    <magnetometer>2150.0</magnetometer>\n" +
            "    <magnetometer>-15.0</magnetometer>\n" +
            "    <magnetometer>-63.0</magnetometer>\n" +
            "</sendingFile>";
    XmlConverter c = new XmlConverter();
    @Test
    void convert() {

    }
}
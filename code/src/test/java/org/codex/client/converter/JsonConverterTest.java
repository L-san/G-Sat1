package org.codex.client.converter;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class JsonConverterTest {

    String string1 = "{\n" +
            "  \"name\":\"inertial\",\n" +
            "  \"data\": {\n" +
            "    \"accelerometer\": [0,0,1],\n" +
            "    \"magnetometer\": [0.7,0,0.7],\n" +
            "    \"gyroscope\": [0,0,0]\n" +
            "  }\n" +
            "}";
    String string1Expected = "accelerometer: [0.0, 0.0, 1.0]\n" +
            "magnetometer:  [0.7, 0.0, 0.7]\n" +
            "gyrocsope:     [0.0, 0.0, 0.0]";

    String string2 = "{\n" +
            "  \"name\":\"inertial\",\n" +
            "  \"data\": {\n" +
            "    \"accelerometer\": [1000,0,0.1],\n" +
            "    \"magnetometer\": [0.777,0,0.777],\n" +
            "    \"gyroscope\": [0,0.001,999]\n" +
            "  }\n" +
            "}";
    String string2Expected = "accelerometer: [1000.0, 0.0, 0.1]\n" +
            "magnetometer:  [0.777, 0.0, 0.777]\n" +
            "gyrocsope:     [0.0, 0.001, 999.0]";

    AbstractConverter converter = new JsonConverter();

    @Test
    public void testConvert() {
        assertEquals(converter.convert(string1).toString(), string1Expected);
        assertEquals(converter.convert(string2).toString(), string2Expected);
    }
}
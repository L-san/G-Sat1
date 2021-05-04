package ssau.spacegradient.clientapp.client.converter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PojoTest {
    /*public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();

        try {

            // JSON file to Java object
            Pojo staff = mapper.readValue(new File("c:\\test\\staff.json"), Pojo.class);

            // JSON string to Java object
            String jsonInString = "{\"name\":\"mkyong\",\"age\":37,\"skills\":[\"java\",\"python\"]}";
            Pojo staff2 = mapper.readValue(jsonInString, Pojo.class);

            // pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff2);

            System.out.println(prettyStaff1);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
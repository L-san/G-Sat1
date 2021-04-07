package ssau.spacegradient.mainapp;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ssau.spacegradient.mainapp.MainApp;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }

}

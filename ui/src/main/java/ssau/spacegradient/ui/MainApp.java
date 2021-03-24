package ssau.spacegradient.ui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        Application.launch(ClientApp.class, args);
    }

}

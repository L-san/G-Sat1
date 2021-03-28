package ssau.spacegradient.ui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ssau.spacegradient.ui.mainapp.MainApp;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }

}

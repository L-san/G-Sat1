package ssau.spacegradient.ui;

import javafx.application.Application;
import org.codex.client.ClientBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ssau.spacegradient.ui.clientapp.ClientApp;
import java.util.concurrent.ArrayBlockingQueue;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        Application.launch(ClientApp.class, args);
    }

}

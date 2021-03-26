package ssau.spacegradient.ui.clientapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ssau.spacegradient.ui.clientapp.ClientApp.StageReadyEvent;


import java.io.IOException;


@Component
public class ClientAppController implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/client.fxml")
    private Resource clientRes;
    private ApplicationContext applicationContext;

    public ClientAppController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(clientRes.getURL());
            fxmlLoader.setControllerFactory(aClass->applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

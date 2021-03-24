package ssau.spacegradient.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ssau.spacegradient.ui.ClientApp.StageReadyEvent;


import java.io.IOException;


@Component
public class ClientController implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/client.fxml")
    private Resource clientRes;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(clientRes.getURL());
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

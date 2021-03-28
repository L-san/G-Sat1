package ssau.spacegradient.ui.mainapp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ssau.spacegradient.ui.StageReadyEvent;

import java.io.IOException;

@Component
public class MainAppController implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/mainwindow.fxml")
    private Resource mainRes;
    private ApplicationContext applicationContext;

    public MainAppController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainRes.getURL());
            fxmlLoader.setControllerFactory(aClass->applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent,1280,720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

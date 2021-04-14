package ssau.spacegradient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ssau.spacegradient.clientapp.Controller;
import ssau.spacegradient.clientapp.client.converter.DataContainer;

import java.util.function.Consumer;

@Component
public class ViewController implements Consumer<DataContainer> {
    private final Controller controller;
    @FXML
    public Box box;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;
    public Label telemetryLabel;


    public ViewController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "ssau.spacegradient.clientapp.beans",
                "ssau.spacegradient.clientapp.client",
                "ssau.spacegradient.clientapp");
        this.controller = context.getBean("controller", Controller.class);
        // System.out.println("i'm here");
    }

    @FXML
    public void startConnection(MouseEvent mouseEvent) {
        //todo где депенденси инжекшн емае
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box.setMaterial(phongMaterial);
        /*RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(3.4699 * 180 / Math.PI);
        rotate.setAxis(new Point3D(0.6966, 0.0540, 0.6966));
        rotate.setNode(box);
        rotate.play();*/

        String ip = null;
        int port = 0;
        boolean isOk = true;
        try {
            ip = ipAddressTextField.getText();
            port = Integer.parseInt(portTextField.getText());
            if (ip.equals("")) {
                throw new IllegalArgumentException("Please, enter ip-address");
            }
            System.out.println(ip + " " + port);
            controller.generateClient(ip, port);
            controller.startClient(this);
        } catch (Exception exception) {
            exception.printStackTrace();
            connStatusLabel.setText("Incorrect host address");
            isOk = false;
        }
        if (isOk) connStatusLabel.setText("Connected to " + ip + ":" + port);
    }

    @FXML
    public void startProcessing(ActionEvent event) {
    }


    @FXML
    public void stopConnection(MouseEvent mouseEvent) {
        //controller.stopClient();
    }

    @Override
    public void accept(DataContainer dataContainer) {
        Platform.runLater(() -> telemetryLabel.setText(dataContainer.toString()));

    }
}


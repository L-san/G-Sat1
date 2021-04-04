import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.codex.controller.Controller;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//public class ViewController {}
@Component
public class ViewController {

    @FXML
    public Box box;
    public Label telemetryLabel;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;
    private Controller controller;

    public ViewController() {
        this.controller = new Controller(new ClassPathXmlApplicationContext("applicationContext.xml"));
    }

    @FXML
    public void startConnection(MouseEvent mouseEvent) {
        //todo где депенденси инжекшн емае
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box.setMaterial(phongMaterial);
        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(3.4699 * 180 / Math.PI);
        rotate.setAxis(new Point3D(0.6966, 0.0540, 0.6966));
        rotate.setNode(box);
        rotate.play();
        
        String ip = null;
        int port = 0;
        boolean isOk = true;
        try {
            ip = ipAddressTextField.getText();
            port = Integer.parseInt(portTextField.getText());
            if (ip == "") {
                throw new IllegalArgumentException("Please, enter ip-address");
            }
            controller.generateClient(ip, port);
            controller.startClient();
        } catch (Exception exception) {
            connStatusLabel.setText("Incorrect host address");
            isOk = false;
        }
        if (isOk) connStatusLabel.setText("Connected to " + ip + ":" + port);
    }

    public void startProcessing(MouseEvent mouseEvent) throws InterruptedException {
        telemetryLabel.setText(controller.receive().toString());
    }

    public void stopProcessing(MouseEvent mouseEvent) {
    }

    public void stopConnection(MouseEvent mouseEvent) {
    }
}


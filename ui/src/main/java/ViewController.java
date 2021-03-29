import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;
import org.codex.client.Client;
import org.codex.client.ClientController;
import org.codex.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void startConnection(ActionEvent actionEvent) {
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
            controller.start();
        } catch (Exception exception) {
            connStatusLabel.setText("Incorrect host address");
            isOk = false;
        }
        if (isOk) connStatusLabel.setText("Connected to " + ip + ":" + port);
    }

    public void startProcessing(ActionEvent actionEvent) throws InterruptedException {
        telemetryLabel.setText(controller.receive());
    }
}


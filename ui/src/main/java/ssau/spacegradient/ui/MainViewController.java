package ssau.spacegradient.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ssau.spacegradient.ui.clientapp.ClientAppService;

@Component
public class MainViewController {
    @FXML
    public Box box;
    public Label telemetryLabel;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;

    private ClientAppService clientAppService;

    @Autowired
    public MainViewController(ClientAppService clientAppService) {
        this.clientAppService = clientAppService;
    }

    @FXML
    public void startConnection(ActionEvent actionEvent) throws InterruptedException {
        String ip = "0.0.0.0";
        int port = 0;
        try {
            ip = ipAddressTextField.getText();
            port = Integer.parseInt(portTextField.getText());
            clientAppService.connectServer(ip, port);
        } catch (Exception ex) {
            connStatusLabel.setText(" Can't connect to " + ip + ":" + port);
        }
        connStatusLabel.setText(" Connected to " + ip + ":" + port);
        telemetryLabel.setText(clientAppService.getMessage());
    }

}

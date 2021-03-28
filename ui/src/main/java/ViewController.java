import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;
import org.codex.client.Client;
import org.springframework.stereotype.Component;
public class ViewController {}
/*@Component
public class ViewController {

    @FXML
    public Box box;
    public Label telemetryLabel;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;
    private Client client;

    public ViewController(Client client) {
        this.client = client;
    }

    @FXML
    public void startConnection(ActionEvent actionEvent) throws InterruptedException {
        String ip = "0.0.0.0";
        int port = 0;
        try {
            ip = ipAddressTextField.getText();
            port = Integer.parseInt(portTextField.getText());
            client.connectServer(ip, port);
        } catch (Exception ex) {
            connStatusLabel.setText(" Can't connect to " + ip + ":" + port);
        }
        connStatusLabel.setText(" Connected to " + ip + ":" + port);
        telemetryLabel.setText(client.getMessage());
    }
*/

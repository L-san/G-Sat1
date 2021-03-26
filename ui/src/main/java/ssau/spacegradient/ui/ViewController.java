package ssau.spacegradient.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssau.spacegradient.ui.clientapp.ClientAppService;

@Component
public class ViewController {
    @FXML
    public Label messageLabel;
    private ClientAppService clientAppService;

    @Autowired
    public ViewController(ClientAppService clientAppService){
        this.clientAppService = clientAppService;
    }

    public void getMessage(ActionEvent actionEvent) throws InterruptedException {
        this.messageLabel.setText(clientAppService.getMessage());
    }

}

package company;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML TextField textFieldOfNewMessage;
    @FXML TextArea  textAreaOfAllMessages;

    private Client client;

    // метод вызывается когда появляется окно приложения
    @FXML
    private void initialize() {
        /*сообщаем клиенту куда закидывать сообщение,
         которые приходят от сервера*/
        client = new Client(textAreaOfAllMessages);
        client.startConnection("localhost", 7320);
    }

    @FXML
    void clickSendButton() {
        client.sendMessage(textFieldOfNewMessage.getText());
    }

}

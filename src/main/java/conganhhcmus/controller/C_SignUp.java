package conganhhcmus.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;


public class C_SignUp implements Initializable{

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private AnchorPane panel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load data here

    }

    //    Functions
    public void SignUp() {
        try {

            // Change Screen

            ChangeScreen("/view/main.fxml", panel);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SignIn() {
        try {

            // Change Screen

            ChangeScreen("/view/signin.fxml", panel);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ChangeScreen(String path, AnchorPane panel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp =  loader.load();
        Scene scene = new Scene(tmp);
        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}

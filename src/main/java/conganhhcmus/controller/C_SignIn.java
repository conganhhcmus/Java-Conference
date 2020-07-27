package conganhhcmus.controller;

import conganhhcmus.utility.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class C_SignIn implements Initializable {
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
    public void Login() {
        try {
            System.out.println("username:"+username.getText()+"\n"+"password:"+password.getText());
            System.out.println("hash_password:"+ Utils.Hash(password.getText()));

            // Change Screen

            ChangeScreen("/view/main.fxml", panel);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SignUp() {
        try {
            // Change Screen

            ChangeScreen("/view/signup.fxml", panel);

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

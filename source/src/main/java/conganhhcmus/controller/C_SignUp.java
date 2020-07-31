package conganhhcmus.controller;

import conganhhcmus.model.M_User;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;


public class C_SignUp implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirm_password;
    @FXML
    private TextField email;
    @FXML
    private TextField fullname;
    @FXML
    private AnchorPane panel;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load data here

    }

    //    Functions
    public void signUp() {
        try {
            if (M_User.isUsernameAlreadyInUse(username.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Username");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("This username is already exists!");

                alert.showAndWait();
            } else if (Utils.isDiffString(password.getText(), confirm_password.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Confirm Password");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("The password confirm does not match!");

                alert.showAndWait();
            } else if (!Utils.isValidPassword(password.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Password");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("Password should not contain any space.\n" +
                        "Password should contain at least one digit(0-9).\n" +
                        "Password length should be between 8 to 15 characters.\n" +
                        "Password should contain at least one lowercase letter(a-z).\n" +
                        "Password should contain at least one uppercase letter(A-Z).\n" +
                        "Password should contain at least one special character ( @, #, %, &, !, $, etc….).");
                alert.showAndWait();
            } else if (!Utils.isValidEmail(email.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Email");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("Email is incorrect!");
                alert.showAndWait();
            } else {
                String hashPassword = Utils.hash(password.getText());
                System.out.println(hashPassword);
                Long userId = M_User.addUser(username.getText(), hashPassword, fullname.getText(), email.getText(), 0);
                // Change Screen
                user = M_User.getUserById(userId);
                changeHome("/view/user_home.fxml");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void signIn() {
        try {

            // Change Screen

            changeScreen("/view/signin.fxml", panel);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScreen(String path, AnchorPane panel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);
        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void changeHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_UserHome home = loader.getController();
        home.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}

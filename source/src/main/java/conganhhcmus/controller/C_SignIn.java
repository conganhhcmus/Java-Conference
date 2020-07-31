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

public class C_SignIn implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private AnchorPane panel;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load data here
    }

    //    Functions
    public void login() {
        try {
            String hashPassword = Utils.hash(password.getText());
            this.user = M_User.getUserByUsername(username.getText());
            if (this.user == null || Utils.isDiffString(user.getPassword(), hashPassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error alert");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password is incorrect!");

                alert.showAndWait();
            } else if (user.getPermission() < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error alert");
                alert.setHeaderText(null);
                alert.setContentText("Account is banned!");

                alert.showAndWait();
            } else if (user.getPermission() == 1) {
                changeAdminHome("/view/admin_home.fxml");
            } else {
                // Change Screen
                changeUserHome("/view/user_home.fxml");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp() {
        try {
            // Change Screen

            changeScreen("/view/signup.fxml");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void changeUserHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_UserHome home = loader.getController();
        home.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void changeAdminHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminHome home = loader.getController();
        home.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}

package conganhhcmus.controller;

import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_User;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class C_UserProfile extends Component implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private Label username_title;

    @FXML
    private TextField password;

    @FXML
    private TextField confirm_password;

    @FXML
    private TextField email;

    @FXML
    private TextField fullname;

    @FXML
    private Button save_change;

    @FXML
    private Button edit;

    @FXML
    private Button upload;

    @FXML
    private Button cancel;

    @FXML
    private ImageView avatar;

    @FXML
    private ImageView avatar_img;

    @FXML
    private AnchorPane panel;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirm_password.setVisible(false);
        save_change.setVisible(false);
        upload.setVisible(false);
        cancel.setVisible(false);
        username.setDisable(true);
        password.setDisable(true);
        email.setDisable(true);
        fullname.setDisable(true);
        edit.setVisible(true);
    }

    public void loadData(User data) {
        user = data;
        username_title.setText(user.getUsername());
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        fullname.setText(user.getFullname());
        email.setText(user.getEmail());
        if (user.getAvatar() == null) {
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
            avatar_img.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
            avatar_img.setImage(new Image(file.toURI().toString()));
        }
    }

    public void logOut() {
        user = null;
        try {
            changeScreen("/view/home.fxml");
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

    public void home() {
        try {
            changeHome("/view/user_home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void edit() {
        confirm_password.setVisible(true);
        save_change.setVisible(true);
        upload.setVisible(true);
        cancel.setVisible(true);
        password.setDisable(false);
        email.setDisable(false);
        fullname.setDisable(false);
        edit.setVisible(false);
        password.setText(null);
    }

    public void cancel() {
        confirm_password.setVisible(false);
        save_change.setVisible(false);
        upload.setVisible(false);
        cancel.setVisible(false);
        username.setDisable(true);
        password.setDisable(true);
        email.setDisable(true);
        fullname.setDisable(true);
        edit.setVisible(true);

        username_title.setText(user.getUsername());
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        fullname.setText(user.getFullname());
        email.setText(user.getEmail());
        if (user.getAvatar() == null) {
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
            avatar_img.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
            avatar_img.setImage(new Image(file.toURI().toString()));
        }
    }

    public void save() {
        try {
            // check
            if (password.getText() == null || password.getText().isEmpty()) {
                if (!Utils.isValidEmail(email.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Validate Email");

                    // Header Text: null
                    alert.setHeaderText(null);
                    alert.setContentText("Email is incorrect!");
                    alert.showAndWait();
                } else {
                    File file = new File(avatar_img.getImage().getUrl().split("file:")[1]);
                    String hashName = Utils.hash(String.valueOf(M_Image.countImg()) + file.getName());
                    String path = "src/main/resources/img/" + hashName + ".png";
                    File dest = new File(path);
                    File destDir = new File(dest.getAbsolutePath());
                    FileUtils.copyFile(file, destDir);

                    Long imgId = M_Image.addImg(file.getName(), hashName);
                    user.setAvatar(imgId);
                    user.setFullname(fullname.getText());
                    user.setEmail(email.getText());

                    M_User.updateUser(user.getId(), user);

                    cancel();
                }

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
            } else {
                File file = new File(avatar_img.getImage().getUrl().split("file:")[1]);
                String hashName = Utils.hash(String.valueOf(M_Image.countImg()) + file.getName());
//                String hashName = Utils.hash(file.getAbsolutePath());
                String path = "src/main/resources/img/" + hashName + ".png";
                File dest = new File(path);
                File destDir = new File(dest.getAbsolutePath());
                FileUtils.copyFile(file, destDir);

                Long imgId = M_Image.addImg(file.getName(), hashName);
                user.setAvatar(imgId);
                user.setPassword(Utils.hash(password.getText()));
                user.setFullname(fullname.getText());
                user.setEmail(email.getText());

                M_User.updateUser(user.getId(), user);

                confirm_password.setVisible(false);
                save_change.setVisible(false);
                upload.setVisible(false);
                cancel.setVisible(false);
                username.setDisable(true);
                password.setDisable(true);
                email.setDisable(true);
                fullname.setDisable(true);
                edit.setVisible(true);

            }
            loadData(user);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(new Stage().getOwner());
        if (file != null) {

            avatar_img.setImage(new Image(file.toURI().toString()));
//            System.out.println(avatar_img.getImage().getUrl().split("file:")[1]);

        }
    }
}

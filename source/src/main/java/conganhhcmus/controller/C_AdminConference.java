package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import tornadofx.control.DateTimePicker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class C_AdminConference implements Initializable {

    @FXML
    private AnchorPane panel;

    @FXML
    private Label username;

    @FXML
    private ImageView img;

    @FXML
    private ImageView avatar;

    @FXML
    private TextField url;

    @FXML
    private TextField conference_name;

    @FXML
    private TextField number_member;

    @FXML
    private TextField address;

    @FXML
    private TextArea description;

    @FXML
    private DateTimePicker start_time;

    @FXML
    private DateTimePicker end_time;

    @FXML
    private Label conference_name_title;

    @FXML
    private Label address_title;

    @FXML
    private Label time_title;

    @FXML
    private Label number;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.setText(null);
        address_title.setText(null);
        conference_name_title.setText(null);
        start_time.setDateTimeValue(LocalDateTime.now());
        end_time.setDateTimeValue(LocalDateTime.now());
        String time = start_time.getDateTimeValue().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")) + " - "
                + end_time.getDateTimeValue().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        time_title.setText("Time: " + time);
    }

    public void add() throws IOException, NoSuchAlgorithmException {
        File imgFile = new File(url.getText());

        String hashName = Utils.hash(String.valueOf(M_Image.countImg()) + imgFile.getName());
        String path = "src/main/resources/img/" + hashName + ".png";
        File dest = new File(path);
        File destDir = new File(dest.getAbsolutePath());
        FileUtils.copyFile(imgFile, destDir);

        Long imgId = M_Image.addImg(imgFile.getName(), hashName);
        Date starttime = Date.from(start_time.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant());
        Date endtime = Date.from(end_time.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant());
        M_Conference.addConference(imgId, conference_name.getText(), address.getText(), starttime, endtime, description.getText(), Long.parseLong(number_member.getText()));

        // Change view
        home();
    }

    public void request() {
        try {
            changeRequest("/view/admin_request.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void account() {
        try {
            changeProfile("/view/admin_profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void home() {
        try {
            changeHome("/view/admin_home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        conference_name_title.setText(conference_name.getText());
        address_title.setText(address.getText());
        number.setText("Number member: " + number_member.getText());
        String time = start_time.getDateTimeValue().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")) + " - "
                + end_time.getDateTimeValue().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        time_title.setText("Time: " + time);

    }

    public void upLoad() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(new Stage().getOwner());
        if (file != null) {
            img.setImage(new Image(file.toURI().toString()));
            url.setText(file.getAbsolutePath());
        }
    }

    public void changeHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminHome profile = loader.getController();
        profile.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void changeRequest(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminRequest temp = loader.getController();
//        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }


    public void changeProfile(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminProfile profile = loader.getController();
        profile.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
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

    public void loadData(User data) {
        username.setText(data.getUsername());
        user = data;
        if (user.getAvatar() == null) {
            avatar.setImage(new Image("/img/avatar.png"));
        } else {
            avatar.setImage(new Image("/img/avatar.png"));
        }
    }
}

package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_Participant;
import conganhhcmus.model.entity.Conference;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import tornadofx.control.DateTimePicker;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class C_AdminEditConference implements Initializable {
    @FXML
    private AnchorPane panel;

    @FXML
    private Label username_title;

    @FXML
    private ImageView avatar;

    @FXML
    private ImageView img;

    @FXML
    private Button edit;

    @FXML
    private Button save_change;

    @FXML
    private Button cancel;

    @FXML
    private Button upload;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField participant;

    @FXML
    private TextField member;

    @FXML
    private TextArea description;

    @FXML
    private DateTimePicker start;

    @FXML
    private DateTimePicker end;

    private User user;

    private Conference conference;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        save_change.setVisible(false);
        cancel.setVisible(false);
        upload.setVisible(false);


        name.setDisable(true);
        address.setDisable(true);
        participant.setDisable(true);
        member.setDisable(true);
        description.setDisable(true);
        start.setDisable(true);
        end.setDisable(true);

    }


    public void account() {
        try {
            changeProfile("/view/admin_profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        edit.setVisible(false);
        save_change.setVisible(true);
        cancel.setVisible(true);
        upload.setVisible(true);

        name.setDisable(false);
        address.setDisable(false);
        participant.setDisable(false);
        member.setDisable(false);
        description.setDisable(false);
        start.setDisable(false);
        end.setDisable(false);

    }

    public void upload() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(new Stage().getOwner());
        if (file != null) {

            img.setImage(new Image(file.toURI().toString()));
//            System.out.println(avatar_img.getImage().getUrl().split("file:")[1]);

        }
    }

    public void back() {
        try {
            changeHome("/view/admin_home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        edit.setVisible(true);
        save_change.setVisible(false);
        cancel.setVisible(false);
        upload.setVisible(false);

        name.setDisable(true);
        address.setDisable(true);
        participant.setDisable(true);
        member.setDisable(true);
        description.setDisable(true);
        start.setDisable(true);
        end.setDisable(true);

        name.setText(conference.getConferencename());
        address.setText(conference.getAddress());
        description.setText(conference.getDescription());
        member.setText(String.valueOf(conference.getMembernumber()));
        participant.setText(String.valueOf(M_Participant.numberJoinConference(conference.getId())));
        start.setDateTimeValue(conference.getStarttime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        end.setDateTimeValue(conference.getEndtime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        String path = "src/main/resources/img/" + M_Image.getImageById(conference.getImageid()).getHashname() + ".png";
        File fileimg = new File(path);
        img.setImage(new Image(fileimg.toURI().toString()));

    }

    public void save() {
        try {
            File file = new File(img.getImage().getUrl().split("file:")[1]);
            String hashName = Utils.hash(String.valueOf(M_Image.countImg()) + file.getName());
            String path = "src/main/resources/img/" + hashName + ".png";
            File dest = new File(path);
            File destDir = new File(dest.getAbsolutePath());
            FileUtils.copyFile(file, destDir);

            Long imgId = M_Image.addImg(file.getName(), hashName);
            conference.setImageid(imgId);
            conference.setMembernumber(Long.parseLong(member.getText()));
            conference.setDescription(description.getText());
            conference.setAddress(address.getText());
            conference.setConferencename(name.getText());
            conference.setStarttime(Date.from(start.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant()));
            conference.setEndtime(Date.from(end.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant()));

            M_Conference.updateConference(conference.getId(), conference);
            cancel();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void changeHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminHome home = loader.getController();
        home.loadData(user);

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

    public void loadData(User data, Conference conference) {
        this.user = data;
        this.conference = conference;
        username_title.setText(user.getUsername());
        if (user.getAvatar() == null) {
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
        }

        name.setText(conference.getConferencename());
        address.setText(conference.getAddress());
        description.setText(conference.getDescription());
        member.setText(String.valueOf(conference.getMembernumber()));
        participant.setText(String.valueOf(M_Participant.numberJoinConference(conference.getId())));
        start.setDateTimeValue(conference.getStarttime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        end.setDateTimeValue(conference.getEndtime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        String path = "src/main/resources/img/" + M_Image.getImageById(conference.getImageid()).getHashname() + ".png";
        File fileimg = new File(path);
        img.setImage(new Image(fileimg.toURI().toString()));
    }
}

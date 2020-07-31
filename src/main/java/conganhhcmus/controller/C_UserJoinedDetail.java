package conganhhcmus.controller;

import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_Participant;
import conganhhcmus.model.entity.Conference;
import conganhhcmus.model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

public class C_UserJoinedDetail implements Initializable {

    @FXML
    private Label username_title;

    @FXML
    private ImageView avatar;

    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView img;

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

    @FXML
    private Button join;

    private User user;
    private Conference conference;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setDisable(true);
        address.setDisable(true);
        participant.setDisable(true);
        member.setDisable(true);
        description.setDisable(true);
        start.setDisable(true);
        end.setDisable(true);
    }

    public void loadData(User data, Conference data_conference) {
        user = data;
        conference = data_conference;
        join.setDisable(true);
        join.setText("Joined");

        if (M_Participant.checkUserInConference(user.getId(), conference.getId()) == 1 && conference.getStarttime().before(new Date())) {
            join.setDisable(false);
            join.setText("Unjoin");
        }

        username_title.setText(data.getUsername());
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

    public void join() {
        M_Participant.deleteRequest(M_Participant.getRequestByUserConference(user.getId(), conference.getId()).getId());
        back();
    }

    public void back() {
        home();
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
            changeHome("/view/user_joined.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_UserJoined temp = loader.getController();
        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void account() {
        try {
            changeProfile("/view/user_profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeProfile(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_UserProfile temp = loader.getController();
        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

}

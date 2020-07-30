package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_Participant;
import conganhhcmus.model.entity.Conference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class C_DetailConference implements Initializable {

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
    private Button join;

    @FXML
    private TextArea description;

    @FXML
    private DateTimePicker start;

    @FXML
    private DateTimePicker end;

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


    public void signUp() {
        try {
            changeScreen("/view/signup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signIn() {
        try {
            changeScreen("/view/signin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back() {
        try {
            changeScreen("/view/home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Permission");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("You cannot join because you are not logged in!");
        alert.showAndWait();
    }

    public void changeScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void loadData(Conference data) {
        conference = data;
        if (M_Participant.numberJoinConference(conference.getId()).equals(conference.getMembernumber())) {
            join.setDisable(true);
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
        File file = new File(path);
        img.setImage(new Image(file.toURI().toString()));
    }
}

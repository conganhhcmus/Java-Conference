package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_Participant;
import conganhhcmus.model.M_User;
import conganhhcmus.model.entity.Conference;
import conganhhcmus.model.entity.Participant;
import conganhhcmus.model.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class C_AdminRequest implements Initializable {
    @FXML
    private AnchorPane panel;

    @FXML
    private Label username;

    @FXML
    private ImageView avatar;

    @FXML
    private ListView list;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void show() {
        List<Pane> tmp = new ArrayList<Pane>();
        List<Participant> requests = M_Participant.getAllRequest();
        for (Participant v : requests) {
            tmp.add(viewPane(v));
        }
        ObservableList<Pane> listRequest = FXCollections.observableArrayList(tmp);
        list.setItems(listRequest);
    }

    public Pane viewPane(Participant request) {
        Conference conference = M_Conference.getConferenceById(request.getConferenceid());
        User user = M_User.getUserById(request.getUserid());

        Pane view = new Pane();
        view.setPrefWidth(597);
        view.setPrefHeight(104);

        String path = "src/main/resources/img/" + M_Image.getImageById(conference.getImageid()).getHashname() + ".png";
        File file = new File(path);
        ImageView img = new ImageView(file.toURI().toString());
        img.setFitHeight(100.0);
        img.setFitWidth(100.0);
        img.setLayoutX(14.0);
        img.setLayoutY(2.0);
        img.setPickOnBounds(true);

        Label name = new Label();
        name.setLayoutX(140.0);
        name.setLayoutY(6.0);
        name.setText(user.getFullname());
        name.setFont(new Font("Arial Bold", 15));

        Label username = new Label();
        username.setLayoutX(140.0);
        username.setLayoutY(29.0);
        username.setText("Username: " + user.getUsername());
        username.setFont(new Font("Arial", 13.0));

        Label email = new Label();
        email.setLayoutX(140.0);
        email.setLayoutY(46.0);
        email.setText("Email: " + user.getEmail());
        email.setFont(new Font("Arial", 13.0));

        Label inf = new Label();
        inf.setLayoutX(140.0);
        inf.setLayoutY(64.0);
        inf.setText("Join to:");
        inf.setFont(new Font("Arial Bold", 13.0));

        Label conferencename = new Label();
        conferencename.setLayoutX(140.0);
        conferencename.setLayoutY(82);
        conferencename.setText(conference.getConferencename());
        conferencename.setFont(new Font("Arial Bold", 15.0));
        conferencename.setTextFill(Paint.valueOf("#0b90c9"));

        Button alow = new Button();
        alow.setLayoutX(516.0);
        alow.setLayoutY(21.0);
        alow.setMnemonicParsing(false);
        alow.setPrefHeight(26);
        alow.setPrefWidth(63);
        alow.setText("Alow");
        alow.setFont(new Font("Arial", 13.0));
        // action join
        alow.setOnMouseClicked(event -> {
            request.setState(1);
            M_Participant.updateParticipant(request.getId(),request);
            show();
        });

        Button deny = new Button();
        deny.setLayoutX(516.0);
        deny.setLayoutY(59.0);
        deny.setMnemonicParsing(false);
        deny.setPrefHeight(26);
        deny.setPrefWidth(63);
        deny.setText("Deny");
        deny.setFont(new Font("Arial", 13.0));
        // action join
        deny.setOnMouseClicked(event -> {
            request.setState(-1);
            M_Participant.updateParticipant(request.getId(),request);
            show();
        });

        view.getChildren().addAll(img, name, username, email, inf, conferencename, alow, deny);
        return view;
    }

    public void conference() {
        try {
            changeConference("/view/admin_home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeConference(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminHome temp = loader.getController();
        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void home() {
        try {
            changeHome("/view/admin_home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeHome(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminHome temp = loader.getController();
        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void user() {
        try {
            changeUser("/view/admin_user.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeUser(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminUser temp = loader.getController();
        temp.loadData(user);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void account() {
        try {
            changeProfile("/view/admin_profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.user = data;
        username.setText(data.getUsername());
        user = data;
        if (user.getAvatar() == null) {
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
        }
        show();
    }
}

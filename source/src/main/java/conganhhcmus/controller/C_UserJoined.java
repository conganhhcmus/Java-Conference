package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_Participant;
import conganhhcmus.model.entity.Conference;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.Utils;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class C_UserJoined implements Initializable {

    @FXML
    private ComboBox view;

    @FXML
    private ComboBox sort;

    @FXML
    private ListView listconference;

    @FXML
    private AnchorPane panel;

    @FXML
    private Label username;

    @FXML
    private ImageView avatar;

    @FXML
    private TextField search;

    private User user;
    private List<Conference> conferences;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.getItems().addAll("List", "Card");
        view.getSelectionModel().select(0);
        sort.getItems().addAll("Date", "Name");
        sort.getSelectionModel().select(0);

        view.setOnAction(e -> {
            int index = view.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                listView(conferences);
            } else {
//                cardView();
            }
        });

        sort.setOnAction(e -> {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                conferences = M_Conference.getAllConferenceJoined(user.getId());
            } else {
                conferences = M_Conference.getAllConferenceJoinedOrderName(user.getId());
            }
            search();
            int temp = view.getSelectionModel().getSelectedIndex();
            if (temp == 0) {
                listView(conferences);
            } else {
//                cardView();
            }
        });


    }

    public void cardView() {
        ObservableList<Conference> conferences = FXCollections.observableArrayList(M_Conference.getAllConference());
        listconference.setItems(conferences);
    }

    public Pane card() {
        Pane card = new Pane();

        return card;
    }
    public void search() {
        if (search.getText().isEmpty()) {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                conferences = M_Conference.getAllConference();
            } else {
                conferences = M_Conference.getAllConferenceOrderName();
            }

            int temp = view.getSelectionModel().getSelectedIndex();
            if (temp == 0) {
                listView(conferences);
            } else {
//                cardView();
            }

        } else {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
//                conferences = M_Conference.getAllConferenceFTSOrderByDate(search.getText());
                List<Conference> temp = new ArrayList<>();
                for (Conference v : conferences) {
                    if (Utils.containsIgnoreCase(v.getConferencename(),search.getText())) {
                        temp.add(v);
                    }
                }
                conferences = temp;
            } else {
//                conferences = M_Conference.getAllConferenceFTSOrderByDate(search.getText());
                List<Conference> temp = new ArrayList<>();
                for (Conference v : conferences) {
                    if (Utils.containsIgnoreCase(v.getConferencename(),search.getText())) {
                        temp.add(v);
                    }
                }
                conferences = temp;
            }

            int temp = view.getSelectionModel().getSelectedIndex();
            if (temp == 0) {
                listView(conferences);
            } else {
//                cardView();
            }
        }
    }
    public void listView(List<Conference> conferences) {
        List<Pane> tmp = new ArrayList<Pane>();
        for (Conference v : conferences) {
            tmp.add(list(v));
        }
        ObservableList<Pane> list = FXCollections.observableArrayList(tmp);
        listconference.setItems(list);
    }

    public Pane list(Conference conference) {
        Pane list = new Pane();
        list.setPrefHeight(104.0);
        list.setPrefWidth(597.0);
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
        name.setText(conference.getConferencename());
        name.setFont(new Font("Arial Bold", 15));

        Label address = new Label();
        address.setLayoutX(140.0);
        address.setLayoutY(29.0);
        address.setText("Address: " + conference.getAddress());
        address.setFont(new Font("Arial", 13.0));

        Label time = new Label();
        time.setLayoutX(140.0);
        time.setLayoutY(46.0);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String str = formatter.format(conference.getStarttime()) + " - " + formatter.format(conference.getEndtime());
        time.setText("Time: " + str);
        time.setFont(new Font("Arial", 13.0));

        Label participant = new Label();
        participant.setLayoutX(140.0);
        participant.setLayoutY(64.0);
        Long cur = M_Participant.numberJoinConference(conference.getId());
        Long total = conference.getMembernumber();
        participant.setText("Participants: " + cur + " members / " + total + " members");
        participant.setFont(new Font("Arial", 13.0));


        Label description = new Label();
        description.setLayoutX(140.0);
        description.setLayoutY(82.0);
        description.setText("Description: " + conference.getDescription());
        description.setFont(new Font("Arial", 13.0));
        description.setWrapText(true);
        description.setPrefHeight(16.0);
        description.setPrefWidth(355.0);

        Button join = new Button();
        join.setLayoutX(511.0);
        join.setLayoutY(21.0);
        join.setMnemonicParsing(false);
        join.setPrefHeight(25);
        join.setPrefWidth(60);
        join.setText("Joined");
        join.setDisable(true);
        join.setFont(new Font("Arial", 13.0));

        // action join
        join.setOnMouseClicked(event -> {

            M_Participant.deleteRequest(M_Participant.getRequestByUserConference(user.getId(), conference.getId()).getId());

            // Load view
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                conferences = M_Conference.getAllConferenceJoined(user.getId());
            } else {
                conferences = M_Conference.getAllConferenceJoinedOrderName(user.getId());
            }
            int temp = view.getSelectionModel().getSelectedIndex();
            if (temp == 0) {
                listView(conferences);
            } else {
//                cardView();
            }
        });

        if (M_Participant.checkUserInConference(user.getId(), conference.getId()) == 1 && conference.getStarttime().after(new Date())) {
            join.setDisable(false);
            join.setText("Unjoin");
        }

        Button detail = new Button();
        detail.setLayoutX(511.0);
        detail.setLayoutY(59.0);
        detail.setPrefHeight(25);
        detail.setPrefWidth(60);
        detail.setMnemonicParsing(false);
        detail.setText("Detail");
        detail.setFont(new Font("Arial", 13.0));
        // action detail
        detail.setOnMouseClicked(event -> {
            try {
                changeDetail("/view/user_joined_detail.fxml", conference);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        list.getChildren().addAll(img, name, address, time, participant, description, join, detail);
        return list;
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

        C_UserProfile profile = loader.getController();
        profile.loadData(user);

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

        C_UserHome temp = loader.getController();
        temp.loadData(user);

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
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
        }
        conferences = M_Conference.getAllConferenceJoined(user.getId());
        listView(conferences);
    }

    public void changeDetail(String path, Conference data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_UserJoinedDetail temp = loader.getController();
        temp.loadData(user, data);

        final Stage appStage = (Stage) panel.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}

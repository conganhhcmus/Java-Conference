package conganhhcmus.controller;

import conganhhcmus.model.M_Conference;
import conganhhcmus.model.M_Image;
import conganhhcmus.model.M_User;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class C_AdminUser implements Initializable {

    @FXML
    private ComboBox sort;

    @FXML
    private AnchorPane panel;

    @FXML
    private Label username;

    @FXML
    private ImageView avatar;

    @FXML
    private ListView list;

    @FXML
    private Button back;

    @FXML
    private TextField search;

    private User user;
    private List<User> listUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sort.getItems().addAll("Date", "Name");
        sort.getSelectionModel().select(0);
        back.setVisible(false);
        sort.setOnAction(e -> {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                listUser = M_User.getAllUserOrderByDate();
            } else {
                listUser = M_User.getAllUserOrderByName();
            }
            search();
            showUser();
        });
    }
    public void search() {
        if (search.getText().isEmpty()) {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                listUser = M_User.getAllUserOrderByDate();
            } else {
                listUser = M_User.getAllUserOrderByName();
            }

            showUser();

        } else {
            int index = sort.getSelectionModel().getSelectedIndex();
            if (index == 0) {
//                conferences = M_Conference.getAllConferenceFTSOrderByDate(search.getText());
                List<User> temp = new ArrayList<>();
                for (User v : listUser) {
                    if (Utils.containsIgnoreCase(v.getUsername(),search.getText())) {
                        temp.add(v);
                    }
                }
                listUser = temp;
            } else {
//                conferences = M_Conference.getAllConferenceFTSOrderByDate(search.getText());
                List<User> temp = new ArrayList<>();
                for (User v : listUser) {
                    if (Utils.containsIgnoreCase(v.getUsername(),search.getText())) {
                        temp.add(v);
                    }
                }
                listUser = temp;
            }

            showUser();
        }
    }

    public void showUser() {
        List<Pane> tmp = new ArrayList<Pane>();
        for (User v : listUser) {
            tmp.add(viewPane(v));
        }
        ObservableList<Pane> listRequest = FXCollections.observableArrayList(tmp);
        list.setItems(listRequest);
    }

    public void showUserDetail() {

    }

    public Pane viewPane(User data) {

        Pane view = new Pane();
        view.setPrefWidth(597);
        view.setPrefHeight(104);
        String path = null;
        if (data.getAvatar() == null) {
            path = "src/main/resources/img/avatar.png";
        } else {
            path = "src/main/resources/img/" + M_Image.getImageById(data.getAvatar()).getHashname() + ".png";
        }

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
        name.setText(data.getFullname());
        name.setFont(new Font("Arial Bold", 15));

        Label username = new Label();
        username.setLayoutX(140.0);
        username.setLayoutY(29.0);
        username.setText("Username: " + data.getUsername());
        username.setFont(new Font("Arial", 13.0));

        Label email = new Label();
        email.setLayoutX(140.0);
        email.setLayoutY(52.0);
        email.setText("Email: " + data.getEmail());
        email.setFont(new Font("Arial", 13.0));

        Label inf = new Label();
        inf.setLayoutX(140.0);
        inf.setLayoutY(76.0);
        inf.setText("Joined:");
        inf.setFont(new Font("Arial Bold", 13.0));

        Label number = new Label();
        number.setLayoutX(190.0);
        number.setLayoutY(76);
        number.setText(String.valueOf(M_User.numberOfConference(data.getId())));
        number.setFont(new Font("Arial Bold", 15.0));
        number.setTextFill(Paint.valueOf("#0b90c9"));

        Button ban = new Button();
        ban.setLayoutX(506.0);
        ban.setLayoutY(21.0);
        ban.setMnemonicParsing(false);
        ban.setPrefHeight(26);
        ban.setPrefWidth(63);
        ban.setText("Ban");
        if (data.getPermission() == -1) ban.setText("Unban");
        ban.setFont(new Font("Arial", 13.0));
        // action join
        ban.setOnMouseClicked(event -> {
//            ban.setText("Unban");
            if (data.getPermission() == 0) data.setPermission(-1);
            else data.setPermission(0);

            M_User.updateUser(data.getId(), data);
            showUser();
        });

        Button detail = new Button();
        detail.setLayoutX(506.0);
        detail.setLayoutY(59.0);
        detail.setMnemonicParsing(false);
        detail.setPrefHeight(26);
        detail.setPrefWidth(63);
        detail.setText("Detail");
        detail.setFont(new Font("Arial", 13.0));
        // action join
        detail.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Function alert");
            alert.setHeaderText(null);
            alert.setContentText("Function is not installed!");

            alert.showAndWait();
//            back.setVisible(true);
//            search.setPromptText("Search Conference");
//            showUserDetail();
        });

        view.getChildren().addAll(img, name, username, email, inf, number, ban, detail);
        return view;
    }

    public void back() {
        back.setVisible(false);
        search.setPromptText("Search User");
        showUser();
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

    public void request() {
        try {
            changeRequest("/view/admin_request.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void changeRequest(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent tmp = loader.load();
        Scene scene = new Scene(tmp);

        C_AdminRequest temp = loader.getController();
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
        user = data;
        username.setText(data.getUsername());
        if (user.getAvatar() == null) {
            avatar.setImage(new javafx.scene.image.Image("/img/avatar.png"));
        } else {
            File file = new File("src/main/resources/img/" + M_Image.getImageById(user.getAvatar()).getHashname() + ".png");
            avatar.setImage(new Image(file.toURI().toString()));
        }
        listUser = M_User.getAllUserOrderByDate();
        showUser();
    }
}

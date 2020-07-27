package conganhhcmus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App extends Application{
    static Session session;
    static Transaction tx;

    @Override
    public void start(final Stage primaryStage) throws Exception {
//        beginSession();
        Parent root = FXMLLoader.load(getClass().getResource("/view/signin.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    static public void beginSession(){
//        session = SessionUtil.getSession();
//        tx = session.beginTransaction();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}

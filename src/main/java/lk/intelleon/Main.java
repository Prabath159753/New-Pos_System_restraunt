package lk.intelleon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:52 PM
 **/

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode =  FXMLLoader.load(this.getClass().getResource("/view/MainDashBoardForm.fxml"));
        Scene scene = new Scene(rootNode);
        stage.setTitle("Super_Market");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }
}

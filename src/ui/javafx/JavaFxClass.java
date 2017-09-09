package ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * Created by barakm on 25/08/2017
 */
public class JavaFxClass extends Application {

    public void init(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane root = FXMLLoader.load(getClass().getResource("UiXml.fxml"));
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.show();
    }
}

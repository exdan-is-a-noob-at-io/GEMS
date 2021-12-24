package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Controller.SampleController;
import sample.Model.Event;
import sample.Model.Monitor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
        primaryStage.setTitle("GEMS");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("sample/initialQRCode.PNG")); //Rickroll QR Code???
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(windowEvent -> {
            Monitor.checkoutAll();
            SampleController.onClose();
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

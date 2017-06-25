package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;


/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 */
public class Main extends Application {
    String style;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/screens/MainMenu.fxml"));
        stage.setTitle("Kwibble");
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("screens/Main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        InetAddress IP= InetAddress.getLocalHost();
        System.out.println("IP of my system is := "+IP.getHostAddress());
        System.out.println("Hoi vera");
    }
}

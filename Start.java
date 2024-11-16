import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class Start extends Application {
    static Mode1GUI mode1GUI;
    static Mode2GUI mode2GUI;
    static Mode3GUI mode3GUI;
    static MainMenu mainMenu;
    static Mode1 mode1;
    static Mode2 mode2;
    static Mode3 mode3;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException{
//        for (int i = 0; i < boxes.length; i++) {
//            boxes[i] = new BoxCreator();
//        }
        //get conditions form the we
        //Parent fxmlDoc = FXMLLoader.load(getClass().getResource("src/MainMenuGUI.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MainMenu");


        WebScrape scrape = new WebScrape();
        mode1 = new Mode1(scrape);
        mode2 = new Mode2(scrape.getConditions());
        mode3 = new Mode3(scrape.getConditions());


        mode1GUI = new Mode1GUI(root,scene);
        mode1GUI.hide();
        mode2GUI = new Mode2GUI(root,scene,scrape);
        mode2GUI.hide();
        mode3GUI = new Mode3GUI(root,scene);
        mode3GUI.hide();
        mainMenu = new MainMenu(root,scene,scrape);

        primaryStage.show();
    }

    public MainMenu getMainMenu() { return mainMenu; }

    public Mode1GUI getMode1GUI() { return mode1GUI; }

    public Mode2GUI getMode2GUI() { return mode2GUI; }
}



//Dijkstra's algorithm
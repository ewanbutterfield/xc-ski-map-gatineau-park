import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;

public class MainMenu extends GUI {
    private Text mode1Button;
    private Text mode2Button;
    private Text mode3Button;
    private Text welcomeMessage;

    public MainMenu(Group r,Scene base, WebScrape scrape) {
        root = r;

        welcomeMessage = new Text(300,200,"Welcome to the Gatineau Park pathing tool. The mode selection is below.");
        welcomeMessage.setFont(Font.font(20));
        root.getChildren().add(welcomeMessage);


        mode1Button = addButton(100,400,"MODE 1: Park Wide Info",20);
        mode1Button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        mode1Button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });
        mode1Button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.updateTable();
                Start.mode1GUI.show();
                Start.mode2GUI.hide();
                Start.mode3GUI.hide();
                Start.mainMenu.hide();
            }
        });
        root.getChildren().add(mode1Button);


        mode2Button = addButton(500,400,"MODE 2: Choose your route", 20);
        mode2Button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        mode2Button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });
        mode2Button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.show();
                Start.mode3GUI.hide();
                Start.mainMenu.hide();
            }
        });
        root.getChildren().add(mode2Button);

        mode3Button = addButton(900,400,"MODE 3: Have a generated route", 20);
        mode3Button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        mode3Button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });
        mode3Button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.hide();
                Start.mode3GUI.show();
                Start.mainMenu.hide();
            }
        });
        root.getChildren().add(mode3Button);



    }

    public void hide() {
        mode1Button.setVisible(false);
        mode2Button.setVisible(false);
        mode3Button.setVisible(false);
        welcomeMessage.setVisible(false);
    }

    public void show() {
        mode1Button.setVisible(true);
        mode2Button.setVisible(true);
        mode3Button.setVisible(true);
        welcomeMessage.setVisible(true);
    }

}


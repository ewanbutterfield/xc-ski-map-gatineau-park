import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public abstract class GUI {
    public int HEIGHT = 700;
    public int WIDTH = 1300;
//    private BoxCreator[] boxes = new BoxCreator[65];
//    private int boxNumber = 0;
    //private ClickBox[] clickBoxes = new ClickBox[35];
    //private LinkedList<String> information;
//    static Mode1 mode1;
//    static Mode2 mode2;
//    static Mode3 mode3;
    static Group root;

    /**
     * TESTER
     */
//    public static void main(String[] args) {
//        WebScrape scrape = new WebScrape();
//        Mode2 mode2 =  new Mode2(scrape.getConditions());
//
//        mode2.addSegment("1");
//        mode2.addSegment("2");
//        mode2.addSegment("3");
//        mode2.removeSegment("1");
//        System.out.println(mode2.getOutput());
//    }

    //add map image


//    public void hideMap() {
//        map.setVisible(false);
//    }
//    public void showMap() { map.setVisible(true); }

//    public void setInformation(LinkedList<String> info) { information = info; }
//    public LinkedList<String> getInformation() {return information;}

//    public void setRoot(Group r) { root = r; }
//    public Group getRoot() { return root; }

    public int getWIDTH() {return WIDTH;}
    public int getHEIGHT() {return HEIGHT;}

    public Text addButton(int x, int y, String name, int fontSize) {
        Text a = new Text(x, y, name);
        a.setUnderline(true);
        a.setFont(Font.font(fontSize));
        return a;
    }



//    /**
//     * below is code used to create the boxes. selected all the points surrounding a route section with mouse to create boxes.
//     * It is not a part of the final program
//     */
//    public void createBoxes(Scene base) {
//        base.setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                boxes[boxNumber].add(event.getX(), event.getY());
//            }
//        });
//
//        base.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER) {
//                    System.out.println("Enter");
//                    boxNumber++;
//                }
//                if (event.getCode() == KeyCode.S) {
//                    try {
//                        FileWriter fw = new FileWriter("resources/addpoints.txt", true);
//                        BufferedWriter bw = new BufferedWriter(fw);
//                        PrintWriter outFile = new PrintWriter(bw);
//                        for (int i = 0; i < boxNumber; i++) {
//                            outFile.println(boxes[i].stringPoints);
//                        }
//                        outFile.close();
//                        Platform.exit();
//                    } catch (Exception e) {
//                        System.err.println("Error");
//                    }
//                }
//            }
//        });
//    }
}


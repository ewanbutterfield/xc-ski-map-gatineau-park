import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Mode2GUI extends GUI {
    private int boxNumber = 0;
    private ClickBox[] clickBoxes = new ClickBox[31];
    private double totalDistance = 0;
    private boolean[] conditions = new boolean[31];
    private Text generateStatsButton;
    private Text statsOutput;
    private Rectangle statsOutputBackground;
    private Text mainMenuButton;
    private Text mode3button;
    private String dateLastUpdated;
    private Text clearButton;
    private ImageView map;
    private Rectangle mainMenuButtonBackground = new Rectangle(10, 15, 100,180);
    private Text showConditionsButton;
    private boolean mapConditionsAreShown = false;

    public ClickBox[] getClickBoxes() { return clickBoxes; }

    public Mode2GUI(Group r, Scene base, WebScrape scrape) {
        root = r;
        addMap();
        clickBoxes = setUpBoxes();
        dateLastUpdated = scrape.getDateLastUpdated();
        toggleConditionsOnMap();

        mainMenuButtonBackground.setFill(Color.WHITE);
        root.getChildren().add(mainMenuButtonBackground);
        //add button to generate the total stats
        generateStatsButton = addButton(1100, 50, "Generate Statistics", 20);
        root.getChildren().add(generateStatsButton);
        mainMenuButton = addButton(50,50,"Main Menu",20);
        mainMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        mainMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });

        mainMenuButton.setY(50);
        root.getChildren().add(mainMenuButton);
        mainMenuButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.hide();
                Start.mainMenu.show();
            }
        });

        mode3button = addButton(50,50,"Mode 3",20);
        mode3button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        mode3button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });

        mode3button.setY(100);
        root.getChildren().add(mode3button);
        mode3button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.hide();
                Start.mainMenu.hide();
                Start.mode3GUI.show();
            }
        });

        //generate rectangle and text to display stats
        statsOutputBackground = new Rectangle(30, 650, 1000, 40);
        statsOutputBackground.setFill(Color.WHITE);
        statsOutput = new Text(150, 670, " Statistic will appear here once generated.");
        statsOutput.setFont(Font.font(15));
        root.getChildren().add(statsOutputBackground);
        root.getChildren().add(statsOutput);

        //cool visual effects
        generateStatsButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                generateStats();
            }
        });
        //cool visual effects
        generateStatsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }
        });
        generateStatsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.DEFAULT);
            }
        });

        clearButton = addButton(850,700,"Clear",20);
        clearButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        clearButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        clearButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clear();
            }
        });
        root.getChildren().add(clearButton);

        showConditionsButton = addButton(150,700,"Show/Hide Conditions",20);
        showConditionsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        showConditionsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        showConditionsButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleConditionsOnMap();
            }
        });
        root.getChildren().add(showConditionsButton);
    }

    public void clear(){
        for (int i = 0; i < clickBoxes.length; i++) {
            clickBoxes[i].setNumTimesSelected(0);
            clickBoxes[i].setColour();
        }
    }

    public void generateStats() {
        totalDistance = 0;
        Start.mode2.clearRoute();
        for (int i = 0; i < 30; i++) {
            //only include information from selected routes
            for (int j = 0; j < clickBoxes[i].getNumTimesSelected(); j++) {
                Start.mode2.addSegment(clickBoxes[i].getSegmentID());
            }
        }
        statsOutput.setText(Start.mode2.getOutput(dateLastUpdated));
        System.out.println(Start.mode2.getOutput(dateLastUpdated));
    }


    public ClickBox[] setUpBoxes() {
        String[] stringPoints;
        ClickBox[] clickBoxes = new ClickBox[31];
        try {
            //get the points to create the click boxes
            Scanner input = new Scanner(new File("resources/addpoints.txt"));
            int i = 0;
            while (input.hasNextLine()) {
                stringPoints = input.nextLine().split(",");
                double[] points = new double[stringPoints.length];
                for (int j = 0; j < points.length; j++) {
                    points[j] = Double.valueOf(stringPoints[j]); // convert values from string to double
                }
                clickBoxes[i] = new ClickBox(points, i); // pass the points to class that creates the click box
                clickBoxes[i].set();
                root.getChildren().add(clickBoxes[i].getClickBox());
                i++;
            }

        } catch (IOException e) {
            System.err.println("File not found");
        }
        return clickBoxes;
    }

    public void addMap() {
        try {
            Image image = new Image(new FileInputStream("resources/map.png"));
            map = new ImageView(image);
            map.setY(0);
            map.setX(0);
            map.setPreserveRatio(true);
            map.setFitHeight(HEIGHT);
            map.setVisible(true);
            root.getChildren().add(map);
            map.toBack();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }

    }

    private void toggleConditionsOnMap () {
        if (!mapConditionsAreShown) {
            RouteSegment[] allRouteSegments = Start.mode2.getAllRouteSegments();
            for (int i = 0; i < 30; i++) {
                clickBoxes[i].setColourToConditions(allRouteSegments[i].getConditions());
            }
            mapConditionsAreShown = !mapConditionsAreShown;
        } else if (mapConditionsAreShown) {
            RouteSegment[] allRouteSegments = Start.mode2.getAllRouteSegments();
            for (int i = 0; i < 30; i++) {
                clickBoxes[i].setColour();
            }
            mapConditionsAreShown = !mapConditionsAreShown;
        }
    }


    public void hide() {
        map.setVisible(false);
        generateStatsButton.setVisible(false);
        statsOutput.setVisible(false);
        statsOutputBackground.setVisible(false);
        for (int i = 0; i < 31; i++) {
            clickBoxes[i].getClickBox().setVisible(false);
        }
        mainMenuButton.setVisible(false);
        mainMenuButtonBackground.setVisible(false);
        clearButton.setVisible(false);
        mode3button.setVisible(false);
        showConditionsButton.setVisible(false);

    }
    public void show() {
        map.setVisible(true);
        generateStatsButton.setVisible(true);
        statsOutput.setVisible(true);
        statsOutputBackground.setVisible(true);
        for (int i = 0; i < 31; i++) {
            clickBoxes[i].getClickBox().setVisible(true);
        }
        mainMenuButton.setVisible(true);
        mainMenuButtonBackground.setVisible(true);
        clearButton.setVisible(true);
        mode3button.setVisible(true);
        showConditionsButton.setVisible(true);
        Start.mode3GUI.showOptions();
    }
}

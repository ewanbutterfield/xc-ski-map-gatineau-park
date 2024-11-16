import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.LinkedList;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class Mode3GUI extends GUI{
    private Text generate = new Text();
    private Text mainMenuButton;
    private Text option1 = new Text();
    private Text option2 = new Text();
    private Text option3 = new Text();
    private Text errorMessage;
    private Scene base;
    private ChoiceBox<String> parkingLot = new ChoiceBox<>();
    private ChoiceBox<String> percentGood = new ChoiceBox<>();
    private TextField distanceInput = new TextField();
    private VBox inputs = new VBox();
    private Text conditionsLegend = new Text();
    private Text lotLegend = new Text();
    private Text distanceLegend = new Text();
    private LinkedList<LinkedList<RouteSegment>> bestRoutes;

    public Mode3GUI(Group r, Scene scene) {
        base = scene;
        root = r;
//        LinkedList<LinkedList<RouteSegment>> bestRoutes = mode3.getBestRoutes(12, 0, 0);
        inputs.setPadding(new Insets(300,50,100,100));
        inputs.setSpacing(25);
        addMainMenuButton();
        addConditionsDropDown();
        addParkingDropDown();
        addDistanceField();
        addConfirmButton();
        addErrorMessage();
        addOptionDisplays();
        root.getChildren().add(inputs);


    }

    private void addErrorMessage() {
        errorMessage = new Text();
        errorMessage.setFont(Font.font(10));
        errorMessage.setFill(RED);
        inputs.getChildren().add(errorMessage);
    }

    private void addOptionDisplays() {
        option1 = addButton(1250, 400, "Option1", 20);
        option1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        option1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        option1.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode2GUI.clear();
                optionClicked(0);
                Start.mode1GUI.hide();
                Start.mainMenu.hide();
                Start.mode3GUI.hide();
                Start.mode2GUI.show();
            }
        });
        root.getChildren().add(option1);

        option2 = addButton(1250, 425, "Option2", 20);
        option2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        option2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        option2.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode2GUI.clear();
                optionClicked(1);
                Start.mode1GUI.hide();
                Start.mainMenu.hide();
                Start.mode3GUI.hide();
                Start.mode2GUI.show();
            }
        });
        root.getChildren().add(option2);

        option3 = addButton(1250, 450, "Option3", 20);
        option3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        option3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        option3.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode2GUI.clear();
                optionClicked(2);

                Start.mode1GUI.hide();
                Start.mainMenu.hide();
                Start.mode3GUI.hide();
                Start.mode2GUI.show();
            }
        });
        root.getChildren().add(option3);
    }

    private void optionClicked(int optionNum) {
        if (bestRoutes.size() < 1) {
            errorMessage.setText("No Routes Found");
        } else if (bestRoutes.size() > 1) {
            for (int i = 1; i < bestRoutes.get(optionNum).size() - 1; i++) {
                Start.mode2GUI.getClickBoxes()[Integer.valueOf(bestRoutes.get(optionNum).get(i).getSegmentID())].toggleSelected();
            }
            Start.mode2GUI.generateStats();
        }
    }
    private void addConfirmButton() {
        generate.setText("Generate");
        generate.setFont(Font.font(15));
        generate.setUnderline(true);
        generate.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        generate.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});
        generate.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                processingText();
            }
        });

        generate.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getDistance(distanceInput.getText()) > 0) {
                     bestRoutes = Start.mode3.getBestRoutes(getDistance(distanceInput.getText()),
                            getPercentInDec(percentGood.getValue()), getParkingLotInID(parkingLot.getValue()));
                    errorMessage.setVisible(true);
                    errorMessage.setText("Done");
                    errorMessage.setFill(BLACK);
                }
            }
        });
        inputs.getChildren().add(generate);
    }

    private void processingText() {
        errorMessage.setText("Generating Route _ | / - ] _");
        errorMessage.setFill(BLUE);
        errorMessage.setVisible(true);
    }

    private int getParkingLotInID(String input) {
        return Integer.valueOf(input.substring(1));
    }

    private double getPercentInDec(String input) {
        if(input.equals("50%")) {
            return  50;
        }
        else if(input.equals("75%")) {
            return  75;
        }
        else if(input.equals("100%")) {
            return  100;
        }
        else {
            return 0;
        }

    }

    private double getDistance(String input) {
        try {
            double distance = Double.valueOf(input);
            if (distance > 0 && distance < 50) {
                return distance;
            }
            else {
                errorMessage.setFill(RED);
                errorMessage.setText("Invalid Distance.");
            }
        } catch (NumberFormatException e) {
            errorMessage.setFill(RED);
            errorMessage.setText("Invalid Distance");
        }
        return -1;

    }


    private void addConditionsDropDown() {
        conditionsLegend.setText("Restrict the minimum percentage of good conditions:");
        conditionsLegend.setFont(Font.font(20));
        inputs.getChildren().add(conditionsLegend);

        percentGood.getItems().addAll("No Restriction", "50%", "75%", "100%");
        percentGood.setValue("No Restrictions");
        inputs.getChildren().add(percentGood);
    }

    private void addParkingDropDown() {
        lotLegend.setText("Pick which parking lot to begin from:");
        lotLegend.setFont(Font.font(20));
        inputs.getChildren().add(lotLegend);

        parkingLot.getItems().addAll("P5", "P6", "P7", "P8", "P9", "P10","P11", "P12");
        parkingLot.setValue("P5");
        inputs.getChildren().add(parkingLot);
    }

    private void addMainMenuButton() {
        mainMenuButton = addButton(1200,100,"Main Menu",20);
        mainMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                base.setCursor(Cursor.OPEN_HAND);
            }});
        mainMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { base.setCursor(Cursor.DEFAULT); }});

        mainMenuButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.hide();
                Start.mode3GUI.hide();
                Start.mainMenu.show();
            }
        });
        root.getChildren().add(mainMenuButton);
    }

    private void addDistanceField(){
        distanceLegend.setText("Input how far you would like to ski in kilometres: (Larger distance take longer, no distances above 50km)");
        distanceLegend.setFont(Font.font(20));
        inputs.getChildren().add(distanceLegend);

        inputs.getChildren().add(distanceInput);
    }



    public void show() {
        generate.setVisible(true);
        mainMenuButton.setVisible(true);
        inputs.setVisible(true);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
    }

    public void hide() {
        generate.setVisible(false);
        mainMenuButton.setVisible(false);
        inputs.setVisible(false);
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
    }

    public void showOptions() {
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
    }
}

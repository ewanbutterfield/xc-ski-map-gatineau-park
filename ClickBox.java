import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ClickBox {
    private int segmentID;
    private Polygon clickBox;
    private int numTimesSelected = 0;
    public ClickBox(double[] points, int id) {
        clickBox = new Polygon(points);
        segmentID = id;
    }
    public Polygon getClickBox() { return clickBox; }
    public int getSegmentID() { return segmentID; }
    public int getNumTimesSelected() { return numTimesSelected; }

    /**
     * set visuals and mouse event for the box
     */
    public void set() {
        clickBox.setFill(Color.BLACK);
        clickBox.setOpacity(0.2);
        clickBox.setOnMouseReleased(event -> {
            System.out.println(segmentID);
            toggleSelected();
        });
    }

    public void setNumTimesSelected(int numTimesSelected) {
        this.numTimesSelected = numTimesSelected;
    }

    public void toggleSelected() {
        if(numTimesSelected == 2) {
            numTimesSelected = 0;
        }
        else {
            numTimesSelected++;
        }
        setColour();
    }

    public void setColour() {
        if(numTimesSelected == 0) {
            clickBox.setFill(Color.BLACK);
            clickBox.setOpacity(0.2);
        }
        else if (numTimesSelected == 1) {
            clickBox.setFill(Color.ORANGE);
            clickBox.setOpacity(0.6);
        }
        else if (numTimesSelected == 2){
            clickBox.setFill(Color.PURPLE);
            clickBox.setOpacity(0.6);
        }
    }

    public void setColourToConditions(boolean conditionsAreGood) {
        if (conditionsAreGood) {
            clickBox.setFill(Color.GREEN);
            clickBox.setOpacity(0.8);
        } else {
            clickBox.setFill(Color.RED);
            clickBox.setOpacity(0.8);
        }
    }

}

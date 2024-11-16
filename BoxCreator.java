import javafx.scene.shape.Polygon;

import java.io.File;
import java.util.Scanner;

public class BoxCreator {
    String stringPoints;

    public BoxCreator() {
        stringPoints = "";
// Scanner input = new Scanner(new File("resources/addpoints.txt"));
//        String stringPoints = input.nextLine();

    }

    public void add(double x, double y) {
        stringPoints += x + "," + y + ",";
    }

    public double[] getPoints(){
        String[] arrayPoints = stringPoints.split(",");
        double[] points = new double[stringPoints.length()];

        for (int i = 0; i < arrayPoints.length; i++) {
            points[i] = Double.valueOf(arrayPoints[i]);
        }

        return points;
    }
}
    
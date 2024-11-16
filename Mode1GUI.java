import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Mode1GUI extends GUI{
    private Text mainMenuButton = new Text();
    private GridPane table = new GridPane();
    private BorderPane borderPane = new BorderPane();

    public Mode1GUI(Group r, Scene base) {
        root = r;
        setUpTable();


        mainMenuButton = addButton(100,100,"Main Menu",20);
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
        root.getChildren().add(mainMenuButton);
        mainMenuButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Start.mode1GUI.hide();
                Start.mode2GUI.hide();
                Start.mainMenu.show();
            }
        });
    }

    public void setUpTable() {
        borderPane.setCenter(table);
        BorderPane.setMargin(table,new Insets(300,250,300,250));

        table.setHgap(10);
        table.setVgap(10);
        Text snowTemp = new Text("Snow Temperature: ");
        snowTemp.setFont(Font.font(20));
        table.add(snowTemp,0,0);
        Text trailsRecommended = new Text("Trails Recommended (% of total km): ");
        trailsRecommended.setFont(Font.font(20));
        table.add(trailsRecommended,0,1);
        Text recStartPoints = new Text("Recommended Starting Points: ");
        recStartPoints.setFont(Font.font(20));
        table.add(recStartPoints,0,2);
        Text wax = new Text("Recommended Wax: ");
        wax.setFont(Font.font(20));
        table.add(wax,0,3);
        Text snowBase = new Text("Snow Base: ");
        snowBase.setFont(Font.font(20));
        table.add(snowBase,0,4);
        Text lastUpdated = new Text("Last Updated: ");
        lastUpdated.setFont(Font.font(20));
        table.add(lastUpdated,0,5);
        root.getChildren().add(borderPane);
    }

    public void updateTable() {
        table.add(new Text(Start.mode1.getSnowTemp()),1,0);
        table.add(new Text(Start.mode1.getPercentRecommended()),1,1);
        table.add(new Text(Start.mode1.getStartPoints()),1,2);
        table.add(new Text(Start.mode1.getWax()),1,3);
        table.add(new Text(Start.mode1.getSnowBase()),1,4);
        table.add(new Text(Start.mode1.getDateLastUpdated()),1,5);

    }

    public void show() {
        mainMenuButton.setVisible(true);
        borderPane.setVisible(true);
    }

    public void hide() {
        mainMenuButton.setVisible(false);
        borderPane.setVisible(false);
    }
}

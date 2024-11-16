//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.Page;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.WebResponse;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.jsoup.select.Elements;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.io.*;
import java.util.LinkedList;







public class WebScrape {
    private LinkedList<String> conditions = new LinkedList<>();
    private String dateLastUpdated;
    private String snowTemp;
    private String percentRecommended;
    private String startPoints;
    private String wax;
    private String snowBase;


    public WebScrape() {
        String link = "http://ncc-ccn.gc.ca/places/ski-conditions";
        try {
        FileWriter fw = new FileWriter("resources/input.html", false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter outFile = new PrintWriter(bw);
        System.setProperty("webdriver.chrome.driver","resources/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(link);
        driver.findElement(By.id("classic-trails")).click();
        outFile.println(driver.getPageSource());
        outFile.close();
        driver.close();
        }catch (IOException e) {
            System.out.println("Something went wrong");
        }
        //System.out.println("running");                            // For testing/debugging purposes
        try {

                File input = new File("resources/input.html");
                Document doc = Jsoup.parse(input, "UTF-8", link);
                //find the right information

                // Information for mode1
                Element parkWideTable = doc.select("table#conditions-general").first();
                Elements rows1 = parkWideTable.select("tr");
                Element snowTempCell = rows1.select("td").get(0);
                snowTemp = snowTempCell.text();
                Element percentRecommendedCell = rows1.select("td").get(1);
                percentRecommended = percentRecommendedCell.text();
                Element startPointsCell = rows1.select("td").get(3);
                startPoints = startPointsCell.text();
                Element waxCell = rows1.select("td").get(4);
                wax = waxCell.text();
                Element snowBaseCell = rows1.select("td").get(6);
                snowBase = snowBaseCell.text();

                //Information for mode2 and mode3
                Element tab = doc.select("div#classic-trails table").first();
                Element table = tab.select("table.table").first();
                Elements rows = table.select("tr");
                Elements cells = rows.select("td");

                Element date = cells.select("td").get(2);
                dateLastUpdated = date.text();

                String[] cond = cells.text().split("km");
                for (int i = 0; i < 63; i++) {
                    cond[i] += "km";
                    conditions.add(cond[i]);
                    System.out.println(conditions.get(i));
                    System.out.println(i);
                }

                conditions.remove(2);
                conditions.remove(2);
                conditions.remove(12);
                conditions.remove(20);
                conditions.remove(20);
                conditions.remove(22);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(26);
                conditions.remove(27);
                conditions.remove(29);
                conditions.remove(29);
                conditions.remove(29);
                conditions.remove(29);
                conditions.remove(29);
                conditions.remove(30);
                conditions.remove(30);
                conditions.remove(30);
                conditions.remove(31);
                conditions.remove(31);

            } catch (IOException e) {
            }
            System.out.println("Done");
    }

    public LinkedList<String> getConditions() { return conditions; }

    public String getSnowTemp() { return snowTemp; }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public String getPercentRecommended() {
        return percentRecommended;
    }

    public String getStartPoints() {
        return startPoints;
    }

    public String getWax() {
        return wax;
    }

    public String getSnowBase() {
        return snowBase;
    }
}

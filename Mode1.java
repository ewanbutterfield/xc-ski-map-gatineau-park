public class Mode1 {
    private String dateLastUpdated;
    private String snowTemp;
    private String percentRecommended;
    private String startPoints;
    private String wax;
    private String snowBase;

    public Mode1(WebScrape scrape) {
        dateLastUpdated = scrape.getDateLastUpdated();
        snowTemp = scrape.getSnowTemp();
        percentRecommended = scrape.getPercentRecommended();
        startPoints = scrape.getStartPoints();
        wax = scrape.getWax();
        snowBase = scrape.getSnowBase();
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public String getSnowTemp() {
        return snowTemp;
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

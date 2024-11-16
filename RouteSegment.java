public class RouteSegment {
    private boolean conditionsAreGood;
    private double distance;
    private boolean isSelected = false;
    private String segmentID;
    public RouteSegment(boolean isGood, double d, String id) {
        conditionsAreGood = isGood;
        distance = d;
        segmentID = id;
    }

    public double getDistance() { return distance; }

    public boolean getConditions() { return conditionsAreGood; }

    public String getSegmentID() { return segmentID; }

}

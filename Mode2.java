import java.util.LinkedList;

public class Mode2 {
    private RouteSegment[] AllRouteSegments = new RouteSegment[43];
    private LinkedList<RouteSegment> chosenRoute = new LinkedList<>();

    public Mode2(LinkedList<String> segmentsInfo) {
        RouteSegment[] temp = createRouteSegments(segmentsInfo);
        for (int i = 0; i < 31; i++) {
            AllRouteSegments[i] = temp[i];
        }
        for (int i = 31; i < 43; i++) {
            AllRouteSegments[i] = new RouteSegment(false,0,Integer.toString(i));
        }

    }

    public RouteSegment[] getAllRouteSegments() {
        return AllRouteSegments;
    }

    /**
     * Create each route segments with its respective information
     * @param segmentsInfo List of information to be interpreted and added to correct RouteSegment
     * @return an array of route segments that have all their information constructed/added in
     */
    private RouteSegment[] createRouteSegments(LinkedList<String> segmentsInfo) {
        RouteSegment[] routeSegments = new RouteSegment[31];
        for (int i = 0; i < segmentsInfo.size(); i++) {
            boolean conditionsReccomended = false;
            double distance;
            //get the correct information from the string
            String temp = segmentsInfo.get(i).split("2020 ")[0];
            String conditions = temp.split("d ")[0];
            conditions += "d";
            if (conditions.equals("Recommended") || conditions.equals(" Recommended") ) {
                conditionsReccomended = true;
            }
            String stringDistance = segmentsInfo.get(i).split("2020")[1].split(" km")[0];
            distance = Double.valueOf(stringDistance);
            routeSegments[i] = new RouteSegment(conditionsReccomended,distance,Integer.toString(i));
        }
        return routeSegments;
    }


    /**
     * Adds a segment to the chosenRoute LinkedList
     * @param id is the unique identification tag of the segment to be added to the chosenRoute
     */
    public void addSegment(int id) {

        for (int i = 0; i < AllRouteSegments.length; i++ ) {
            if (AllRouteSegments[i].getSegmentID().equals(Integer.toString(id))) {
                chosenRoute.add(AllRouteSegments[i]);
            }
        }

    }


    public void clearRoute() { chosenRoute.clear(); }

    /**
     * Removes a segment from the chosenRoute LinkedList
     * @param id is the unique identification tag of the segment to be removed from the chosenRoute
     */
    public void removeSegment(String id) {
        int index = 0;
        for(int i =0; i < chosenRoute.size(); i++ ) {
            if (chosenRoute.get(i).getSegmentID().equals(id)) {
                index = i;
            }
        }
        chosenRoute.remove(index);

    }

    /**
     * gets the information relevant to the chosenRoute that must be outputted to the GUI
     * @return a String variable containing all the important information about the route
     */
    public String getOutput(String dateUpdated) {
        String printout = "";
        double totalDistance = getRouteTotalDistance();
        LinkedList<Boolean> conditions = getRouteConditions();


        printout += "Distance: " + totalDistance + " km ";
        // Create String to display fraction of good conditions
        int numtrue = 0;
        int numfalse = 0;
        for(int i = 0; i < conditions.size(); i++) {
            if(conditions.get(i)) {
                numtrue++;
            }
            else {
                numfalse++;
            }
        }
        String overallConditions;

        //Combine all output parts and return
        overallConditions = numtrue + "/" + (numtrue+numfalse) + " Recommended";
        printout += "| Conditions: " + overallConditions;
        printout += " | Last Updated: " + dateUpdated;
        return printout;
    }

    /**
     * Finds the sum of all the distance fields in the segment objects, in the chosenRoute LinkedList
     * @return the total distance of the chosen route
     */
    private double getRouteTotalDistance() {
        double totalDistance = 0;
        for(int i = 0; i < chosenRoute.size(); i++ ) {
            totalDistance += chosenRoute.get(i).getDistance();
        }

        return totalDistance;
    }

    /**
     * Compiles all the conditions fields in the different segment objects into one array
     * @return an array of all the conditions on the chosen route
     */
    private LinkedList<Boolean> getRouteConditions() {
        LinkedList<Boolean> allConditions = new LinkedList<>();
        for(int i = 0; i < chosenRoute.size(); i++ ) {
            allConditions.add(chosenRoute.get(i).getConditions());
        }
        return allConditions;
    }

}

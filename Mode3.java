import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Mode3 {
    private LinkedList<LinkedList<Integer>> allRoutesByID = new LinkedList<>();
    private RouteSegment[] AllRouteSegments = new RouteSegment[43];
    Graph graph;


    public Mode3(LinkedList<String> segmentsInfo) {
        // Add all the route segments
        RouteSegment[] temp = createRouteSegments(segmentsInfo);
        for (int i = 0; i < 31; i++) {
            AllRouteSegments[i] = temp[i];
        }
        // Add all the parking lots as route segments
        for (int i = 31; i < 43; i++) {
            AllRouteSegments[i] = new RouteSegment(false, 0, Integer.toString(i));
        }

        // For generating the routes. Once generated it is then stored in text file and the following code is no longer needed

       createGraph();
    }

    public double[] getRouteSummary(LinkedList<RouteSegment> route) {
        double totalDistance = 0;
        double numGoodCond = 0;
        double numBadCond = 0;
        double lot;
        for (int i = 1; i < route.size() - 1; i++) {
            totalDistance += route.get(i).getDistance();
            if (route.get(i).getConditions()) {
                numGoodCond += 1;
            } else {
                numBadCond += 1;
            }
        }
        lot = Double.valueOf(route.getFirst().getSegmentID());
        return new double[]{totalDistance, numGoodCond, numBadCond, lot, 0};
    }

    private LinkedList<double[]> linkRoutesByObject() {
        LinkedList<double[]> routeSummary = new LinkedList<>();
        for (int i = 0; i < allRoutesByID.size(); i++) {
            LinkedList<RouteSegment> route = new LinkedList<>();
            for (int j = 0; j < allRoutesByID.get(i).size(); j++) {
                route.add(AllRouteSegments[allRoutesByID.get(i).get(j)]); //Segment ID = to its index in allRoutes[]
            }
            routeSummary.add(getRouteSummary(route));
            routeSummary.get(i)[4] = i;
        }
        return routeSummary;
    }
    /**
     * Create each route segments with its respective information
     * @param segmentsInfo List of information to be interpreted and added to correct RouteSegment
     * @return an array of route segments that have all their information constructed/added in
     */
    public RouteSegment[] createRouteSegments(LinkedList<String> segmentsInfo) {
        RouteSegment[] routeSegments = new RouteSegment[31];
        for (int i = 0; i < segmentsInfo.size(); i++) { //connect information to its relative click box
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
            routeSegments[i] = new RouteSegment(conditionsReccomended, distance, Integer.toString(i));
        }
        return routeSegments;
    }

    /**
     * Creates the unweighted graph used to generate routes
     * Created heavily based on the implementation from website below
     * https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
     */
    private void createGraph() {
        int numNodes = 43;
        graph = new Graph(numNodes);
        String[] connections;
        int source;
        int destination;
        try {
            //get the points to create the click boxes
            Scanner input = new Scanner(new File("resources/RouteConnections.txt"));
            int i = 0;
            while (input.hasNextLine()) {
                connections = input.nextLine().split(",");
                source = Integer.valueOf(connections[0]);
                destination = Integer.valueOf(connections[1]);
                graph.addEgde(source, destination);
            }
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /**
     * Input the generated all possible routes from the text file, into a List of routes;
     * These routes being a list of route segment IDs
     */
    private void inputPossibleRoutes() {
        allRoutesByID.clear();
        try {
            // Input all possible routes from file
            Scanner input = new Scanner(new File("resources/PossibleRoutes"));
            int a = 0;
            // Turn string of indexes into a list
            while (input.hasNextLine()) {
                String[] StringRoutePoints = input.nextLine().split(",");
                int last = StringRoutePoints.length - 1;
                for (int i = 0; i < StringRoutePoints.length; i++) {
                    StringRoutePoints[i] = StringRoutePoints[i].substring(1);
                }
                StringRoutePoints[last] = StringRoutePoints[last].substring(0, StringRoutePoints[last].length() - 1);
                LinkedList<Integer> intRoutePoints = new LinkedList<>();
                for (int i = 0; i < StringRoutePoints.length; i++) {
                    intRoutePoints.add(Integer.valueOf(StringRoutePoints[i]));
                }
                allRoutesByID.add(intRoutePoints);
            }
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        // Add the end of the route to the list
        for (int i = 0; i < allRoutesByID.size(); i++) {
            Stack<Integer> repeatedSegments = new Stack<>();
            int j = 0;
            while (!(allRoutesByID.get(i).getLast().equals(allRoutesByID.get(i).get(j)))) {
                repeatedSegments.push(allRoutesByID.get(i).get(j));
                j++;
            }
            if (allRoutesByID.get(i).get((allRoutesByID.get(i).size() - 3)).equals(allRoutesByID.get(i).getLast())) {
                allRoutesByID.get(i).add(j + 1, allRoutesByID.get(i).get(allRoutesByID.get(i).size() - 2));
            }
            while (repeatedSegments.size() > 0) {
                allRoutesByID.get(i).add(repeatedSegments.pop());
            }
        }
    }

    /**
     * used to generate all possible nodes from a given starting node
     * @param route contains all nodes of the current path
     * @param previousNode contains the previous node in the path
     */
    private void GenerateRoutes(LinkedList<String> route, int previousNode, PrintWriter outFile, double distance, double target) {
        int currentNode;
        int counter = 0;
        while (counter < graph.getNumPossibleDestinations(previousNode)) {
            currentNode = graph.getDestinationNode(previousNode, counter);
            if (!route.contains(Integer.toString(currentNode)) && distance + 5 < target) {
                distance += AllRouteSegments[currentNode].getDistance();
                route.add(Integer.toString(currentNode));
                GenerateRoutes(route, currentNode, outFile, distance, target);
            } else if (route.contains(Integer.toString(currentNode))) {
                route.add(Integer.toString(currentNode));
                System.out.println(route);
                outFile.println(route);
                route.removeLast();
            }
//            else {
//                route.add(Integer.toString(currentNode));
//            }
            counter++;
        }
        route.removeLast();
    }


    /**
     * Get the best routes to return to query
     * @param distance the target distance of the query
     * @param percentGood the minimum percentage of good conditions for the query
     * @param requestedlot the parking lot that the route must start from
     * @return an ordered 2D list of routes that meet the conditions from best to worst
     */
    public LinkedList<LinkedList<RouteSegment>> getBestRoutes(double distance, double percentGood, int requestedlot) {
        requestedlot += 30;

        LinkedList<String> route = new LinkedList<>();
        route.add("0");
        route.set(0, Integer.toString(requestedlot));
        try {
            FileWriter fw = new FileWriter("resources/PossibleRoutes", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outFile = new PrintWriter(bw);
            GenerateRoutes(route, requestedlot, outFile, 0, distance);
            outFile.close();
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        inputPossibleRoutes();
        LinkedList<double[]> sortedRouteSummaries = linkRoutesByObject();
        System.out.println("//");
        sortedRouteSummaries = sortByDistanceDifference(distance,sortedRouteSummaries);

        // Get sorted list from best to worst
        for(int i = 0;  i < sortedRouteSummaries.size(); i++) {
            for(int j = 0; j < sortedRouteSummaries.get(i).length ; j++) {
                System.out.print(sortedRouteSummaries.get(i)[j] + ", ");
            }
            System.out.println();
        }
        LinkedList<LinkedList<RouteSegment>> bestRoutes = new LinkedList<>();
        // Eliminate routes that do not meet the requirements
        for (int i = 0; i < sortedRouteSummaries.size(); i++) {
            double numGoodCond = sortedRouteSummaries.get(i)[1];
            double totalCond = sortedRouteSummaries.get(i)[2] + numGoodCond;
            if (((numGoodCond/totalCond)*100 >= percentGood)) {
                LinkedList<RouteSegment> temp = getObjectifiedRoute(allRoutesByID.get((int)sortedRouteSummaries.get(i)[4]));
                bestRoutes.add(temp);
            }
        }
        return bestRoutes;

    }

    /**
     * sort the route summaries by how close they are to the target distance
     * @param targetDistance the target distance on which to base the sort
     * @return the sorted linked list of route summaries
     */
    private LinkedList<double[]> sortByDistanceDifference(double targetDistance, LinkedList<double[]> sortedRouteSummaries) {
        int numSwitches = 0;
        int pivotIndex = 0;

        if(sortedRouteSummaries.size() <= 1) {
            return sortedRouteSummaries;
        }
        else {
            double pivotDifference = Math.abs(sortedRouteSummaries.get(pivotIndex)[0] - targetDistance);
            for (int i = 1; i < sortedRouteSummaries.size(); i++) {
                double difference = Math.abs(sortedRouteSummaries.get(i)[0] - targetDistance);
                if (difference < pivotDifference) {
                    double[] temp = sortedRouteSummaries.get(pivotIndex + 1 + numSwitches);
                    sortedRouteSummaries.set(pivotIndex + 1 + numSwitches,sortedRouteSummaries.get(i));
                    sortedRouteSummaries.set(i,temp);
                    numSwitches++;
                }
            }
            double[] temp = sortedRouteSummaries.get(pivotIndex);
            sortedRouteSummaries.set(pivotIndex,sortedRouteSummaries.get(pivotIndex + numSwitches));
            sortedRouteSummaries.set(pivotIndex + numSwitches,temp);

            LinkedList<double[]> partOne = new LinkedList<>();
            for (int i = 0; i <= numSwitches; i++) {
                partOne.add(sortedRouteSummaries.get(i));
            }
            LinkedList<double[]> partTwo = new LinkedList<>();
            for (int i = numSwitches + 1; i < sortedRouteSummaries.size(); i++) {
                partTwo.add(sortedRouteSummaries.get(i));
            }
            partOne = sortByDistanceDifference(targetDistance, partOne);
            partTwo = sortByDistanceDifference(targetDistance, partTwo);

            sortedRouteSummaries.clear();
            for (int i = 0; i < partOne.size(); i++) {
                sortedRouteSummaries.add(partOne.get(i));
            }
            for (int i = 0; i < partTwo.size(); i++) {
                sortedRouteSummaries.add(partTwo.get(i));
            }
        }
        /*
        LinkedList<double[]> sortedRouteSummaries = new LinkedList<>();
        sortedRouteSummaries.add(routeSummary.get(0));
        double difference;
        // Double for loop iterative list sort
        for (int i = 1; i < routeSummary.size(); i++) {
            // get the difference of current route distance to target distance
            difference = Math.abs(routeSummary.get(i)[0] - targetDistance);
            boolean found = false;
            for(int j = 0; !found; j++) {
                //compare to distances in array to place it in the correct place
                double comparisonDiff = Math.abs(sortedRouteSummaries.get(j)[0] - targetDistance);
                if (difference < comparisonDiff ) {
                    sortedRouteSummaries.add(j, routeSummary.get(i));
                    found = true;
                }
                else if (j == sortedRouteSummaries.size() - 1 ) {
                    sortedRouteSummaries.add(routeSummary.get(i));
                    found = true;
                }

            }
        }
        */
        return sortedRouteSummaries;
    }

    private LinkedList<RouteSegment> getObjectifiedRoute(LinkedList<Integer> routeByID) {
        LinkedList<RouteSegment> route = new LinkedList<>();
        for (int j = 0; j < routeByID.size(); j++) {
            route.add(AllRouteSegments[routeByID.get(j)]); //Segment ID = to its index in allRoutes[]
        }
        return route;
    }
}
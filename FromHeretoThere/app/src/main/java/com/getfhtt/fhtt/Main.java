package com.getfhtt.fhtt;

public class Main {

    public static void main(String[] args) {
        // example code
        WebConnectHelper http = new WebConnectHelper();
        float test = http.getFuelPrice("electric");
        System.out.println("Electric test : " + test);
        // add a verbose parameter for steps
        http.getFuelPrice("e85", false);
        // get the longitude/latitude position of the input

        System.out.println(http.geoLocation("Kingston Ontario"));
        // get the time needed to drive from Kingston to Ontario
        Route myTravel = new Route("Queen's University, Kingston, Ontario", "Cateraqui Center, Kingston, Ontario", "transit");
        if (myTravel.isLoaded()) {
            System.out.println("Route from " + myTravel.getStartAddress() + " to " + myTravel.getEndAddress() + ", " + myTravel.getCopyright());
            System.out.println("Duration = " + myTravel.getTravelTime());
            System.out.println("Distance = " + myTravel.getDistance() / 1000 + "km");
        } else {
            System.out.println("No route found");
        }

    }
}

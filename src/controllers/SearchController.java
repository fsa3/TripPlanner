package controllers;

import entities.SearchResult;
import entities.TripPackage;

import java.util.ArrayList;

public class SearchController {
    private final SearchResult searchResult;
    private ArrayList<TripPackage> tPackages;

    public SearchController(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public void createTripPackages() {
        TripPackage package1 = new TripPackage("Best Value", searchResult);
        package1.testPackage();
        TripPackage package2 = new TripPackage("High End", searchResult);
        package2.testPackage();
        TripPackage package3 = new TripPackage("Luxury", searchResult);
        package3.testPackage();

        tPackages = new ArrayList<>();
        tPackages.add(package1);
        tPackages.add(package2);
        tPackages.add(package3);
    }

    public ArrayList<TripPackage> getPackages() {
        return tPackages;
    }
}

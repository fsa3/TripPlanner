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
        package1.bestValue();
        TripPackage package2 = new TripPackage("High End", searchResult);
        package1.highEnd();

        tPackages = new ArrayList<>();
        tPackages.add(package1);
        //tPackages.add(package2);
    }

    public ArrayList<TripPackage> getPackages() {
        return tPackages;
    }
}

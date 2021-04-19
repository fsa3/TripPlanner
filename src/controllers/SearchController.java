package controllers;

import entities.SearchResult;
import entities.TripPackage;
import entities.User;

import java.util.ArrayList;

public class SearchController {
    private final SearchResult searchResult;
    private ArrayList<TripPackage> tPackages;
    private User user;

    public SearchController(SearchResult searchResult, User user) {
        this.searchResult = searchResult;
        this.user = user;
    }

    public void createTripPackages() {
        TripPackage package1 = new TripPackage("Best Value", searchResult, user);
        package1.bestValue();
        TripPackage package2 = new TripPackage("High End", searchResult, user);
        package2.highEnd();
        TripPackage package3 = new TripPackage("Luxury", searchResult, user);
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

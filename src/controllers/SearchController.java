package controllers;

import entities.SearchResult;
import entities.TripPackage;
import entities.User;

import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class SearchController {
    private final SearchResult searchResult;
    private ArrayList<TripPackage> tPackages;
    private User user;

    public SearchController(SearchResult searchResult, User user) {
        this.searchResult = searchResult;
        this.user = user;
    }

    public void createTripPackages() {
        tPackages = new ArrayList<>();

        TripPackage package1 = new TripPackage("Best Value", searchResult, user);
        package1.bestValue();
        tPackages.add(package1);

        TripPackage package2 = new TripPackage("High End", searchResult, user);
        package2.highEnd();
        tPackages.add(package2);

        if(DAYS.between(searchResult.getStartDate(), searchResult.getEndDate()) >= 4) {
            TripPackage package3 = new TripPackage("All In", searchResult, user);
            tPackages.add(package3);
            package3.allIn();
        }

        TripPackage package4 = new TripPackage("Relaxation", searchResult, user);
        package4.relaxation();
        tPackages.add(package4);

    }

    public ArrayList<TripPackage> getPackages() {
        return tPackages;
    }
}

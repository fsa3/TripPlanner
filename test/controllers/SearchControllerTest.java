package controllers;

import entities.*;
import flightSystem.flightplanner.entities.Flight;
import hotelSystem.entities.Accommodation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchControllerTest {
    private final String dep = "Reykjavík";
    private final String dest = "Akureyri";
    private final LocalDate depDate = LocalDate.of(2020, 1, 8);
    private final LocalDate returnDate = LocalDate.of(2020, 1, 15);
    private final int adults = 2;
    private final int children = 1;
    private SearchController searchController;
    private ArrayList<TripPackage> tPackages;

    @BeforeEach
    void setUp() {
        SearchResult sResult = new SearchResult(depDate, returnDate, dep, dest, adults, children);
        sResult.search();
        searchController = new SearchController(sResult);
        searchController.createTripPackages();
        tPackages = searchController.getPackages();
    }

    @AfterEach
    void tearDown() {
        searchController = null;
    }

    @Test
    public void testNumberOfOutFlights() {
        for(TripPackage tPackage : tPackages)  {
            assertTrue(tPackage.getOutFlights().size() > 0);
        }
    }

    @Test
    public void testNumberOfInFlights() {
        for(TripPackage tPackage : tPackages)  {
            assertTrue(tPackage.getInFlights().size() > 0);
        }
    }

    @Test
    public void testOutFlightDepLocation() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getOutFlights()) {
                assertEquals(dep, f.getDeparture());
            }
        }
    }

    @Test
    public void testOutFlightDestLocation() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getOutFlights()) {
                assertEquals(dest, f.getArrival());
            }
        }
    }

    @Test
    public void testInFlightDepLocation() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getInFlights()) {
                assertEquals(dest, f.getDeparture());
            }
        }
    }

    @Test
    public void testInFlightDestLocation() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getInFlights()) {
                assertEquals(dep, f.getArrival());
            }
        }
    }

    @Test
    public void testInFlightDate() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getInFlights()) {
                assertEquals(returnDate, f.getDeparture());
            }
        }
    }

    @Test
    public void testOutFlightDate() {
        for(TripPackage tPackage : tPackages) {
            for(Flight f : tPackage.getOutFlights()) {
                assertEquals(depDate, f.getDeparture());
            }
        }
    }

    @Test
    public void testNumberOfHotels() {
        for(TripPackage tPackage : tPackages) {
            assertTrue(tPackage.getHotels().size() > 0);
        }
    }

    @Test
    public void testHotelLocation() {
        for(TripPackage tPackage : tPackages) {
            for(Accommodation h : tPackage.getHotels()) {
                assertEquals(dest, h.getLocation());
            }
        }
    }

    @Test
    public void testNumberOfDayTrips() {
        for(TripPackage tPackage : tPackages) {
            assertTrue(tPackage.getDayTrips().size() > 0);
        }
    }

    @Test
    public void testDayTripLocation() {
        for(TripPackage tPackage : tPackages) {
            for(DayTrip dt : tPackage.getDayTrips()) {
                assertEquals(dest, dt.getCity());
            }
        }
    }
}
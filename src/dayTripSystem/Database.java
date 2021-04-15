package dayTripSystem;

//import com.sun.org.apache.xpath.internal.operations.String;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Database {
    private ObservableList<Trip> allTrips;
    private ObservableList<Account> allAccounts;
    private ObservableList<Booking> allBookings;

    private static Database DBSingleton = null;

    private Database() {
        this.allTrips = getTrip();
        this.allAccounts = getAccount();
        this.allBookings = getBooking();
    }

    public static Database getInstance() {
        if (DBSingleton == null) {
            DBSingleton = new Database();
        }
        return DBSingleton;
    }

    // Trip til að profa virkni
    Date date1 = new Date(System.currentTimeMillis());
    Trip trip1 = new Trip("001", "Isafjordur", date1, "abc@abc.com", 30, 10, false, "Skiing", null, 30, false, 200);
    Account account1 = new Account("2107002260", "Ragna", "Thorsdottir", "123", "rdt1@hi.is", "8637809", null, null);

    public void GenerateData() {
        String[] stadsetning = {"Reykjavik", "Akureyri", "Egilsstadir", "Isafjordur"};
        String[] host = {"abc@abc.com", "ballibumba@bumbuferdir.is", "visiticeland@icelandtrips.is",
                "niceland@niceland.is", "mosi@mositrips.is"};
        Date[] dags = {new Date(System.currentTimeMillis())};
        String[] flokkur = {"Hiking", "Sailing", "Skiing", "Biking", "City Tour"};
        int max = 40;
        int min = 10;

        //Geymsla: new Date(System.currentTimeMillis())
        int[] verd = {50, 100, 150, 200, 250, 300};

        for (int i = 0; i < 30; i++) {
            AddTrip(new Trip(String.valueOf(i), stadsetning[i % 4], dags[i % 6],
                    host[i % 5], max, min, false, flokkur[i % 5], null, max, false, verd[i % 6]));
        }
    }

    public void AddTrip(Trip t) {
        allTrips.add(t);
    }

    public ObservableList<Trip> getAllTrips() {
        return allTrips;
    }

    public void setAllTrips(ObservableList<Trip> allTrips) {
        this.allTrips = allTrips;
    }

    public void AddAccount(Account a) {
        allAccounts.add(a);
    }

    public ObservableList<Account> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(ObservableList<Account> allAccounts) {
        this.allAccounts = allAccounts;

    }

    public void AddBooking(Booking b) {
        allBookings.add(b);
    }

    public ObservableList<Booking> getAllBookings() {
        return allBookings;
    }

    public void setAllBookings(ObservableList<Booking> allBookings) {
        this.allBookings = allBookings;

    }

    public ObservableList<Account> addPassengers(Booking b) {
        ObservableList<Account> passengers = FXCollections.observableArrayList();
        passengers.add(b.getAccountUnit());
        return passengers;
    }

    private ObservableList<Account> getAccount() {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        accounts.add(account1);
        return accounts;
    }

    private ObservableList<Booking> getBooking() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        bookings.add(new Booking(trip1, account1, false, 100, true, 2));
        return bookings;
    }

    private ObservableList<Trip> getTrip() {
        ObservableList<Trip> trips = FXCollections.observableArrayList();

        trips.add(trip1);
        trips.add(new Trip("002", "Reykjavik", date1, "abc@abc.com", 30, 10, false, "City Tour", null, 30, false, 70));
        trips.add(new Trip("003", "Egilsstadir", date1, "abc@abc.com", 30, 10, false, "Sailing", null, 30, false, 300));
        trips.add(new Trip("004", "Akureyri", date1, "abc@abc.com", 30, 10, false, "Cycling", null, 30, false, 100));
        trips.add(new Trip("005", "Reykjavik", date1, "abc@abc.com", 30, 10, false, "Hiking", null, 30, false, 50));
        return trips;
    }
// Spyrja út í endurkvæma lausn
    /*private Trip trip1 = new Trip();

    private ObservableList<Trip> prufuadferd() {
        allTrips.add(trip1);
        return allTrips;
    }*/


    public ObservableList<PaymentInfo> getPaymentInfo(Account a) {
        ObservableList<PaymentInfo> paymentInfo = FXCollections.observableArrayList();
        paymentInfo.add(a.getPayInfo());
        return paymentInfo;
    }


}

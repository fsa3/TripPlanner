package dayTripSystem;

//import com.sun.org.apache.xpath.internal.operations.String;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Database {
    private ObservableList<Trip> allTrips;
    private ObservableList<Account> allAccounts;
    private ObservableList<Booking> allBookings;
    //private ObservableList<PaymentInfo> allPaymentInfo;

    private static Database DBSingleton = null;

    private Database() {
        this.allTrips = getTrip();
        this.allAccounts = getAccount();
        this.allBookings = getBooking();
        GenerateData();
        //this.allPaymentInfo = getPaymentInfo();
    }

    public static Database getInstance() {
        if (DBSingleton == null) {
            DBSingleton = new Database();
        }
        return DBSingleton;
    }

    // Trip til að profa virkni
    /*Date date1 = new Date(System.currentTimeMillis());
    Trip trip1 = new Trip("001", "Isafjordur", date1, "abc@abc.com", 30, 10, false, "Skiing", null, 30, false, 200);
    Account account1 = new Account("2107002260", "Ragna", "Thorsdottir", "123", "rdt1@hi.is", "8637809", null, null);*/

    //Notkun GenerateData();
    //Fyrir: Ekkert
    //Eftir: Gögn verda til i Database klasanum
    public void GenerateData() {

        //Buum til TripData
        String[] stadsetning = {"Reykjavík", "Akureyri", "Egilsstadir", "Isafjordur"};
        String[] host = {"abc@abc.com", "ballibumba@bumbuferdir.is", "visiticeland@icelandtrips.is",
                "niceland@niceland.is", "mosi@mositrips.is"};
        //ATH????
        Date[] dags = new Date[31];
        for(int i = 1; i <= 31; i++) {
            dags[i-1] = new GregorianCalendar(2021, Calendar.MAY, i).getTime();
        }
        String[] flokkur = {"Hiking", "Sailing", "Skiing", "Biking", "City Tour"};
        int max = 40;
        int min = 10;
        int[] verd = {50, 100, 150, 200, 250, 300};

        // For lykkja sem byr til 30 random Trip hluti
        for(int j = 0; j < 31; j++) {
            for (int i = 0; i < 8; i++) {
                AddTrip(new Trip(String.valueOf(i), stadsetning[i % 4], dags[j],
                        host[i % 5], max, min, false, flokkur[i % 5], null, max, false, verd[i % 6]));
            }
        }


        //Buum til AccountData:
        String[] kennitala = {"2107002260", "0311002590", "0101012250", "1010702669", "1001002000"};
        String[] fornafn = {"Ragna", "Inga", "Erna", "Emilía", "Ragnar"};
        String[] eftirnafn = {"Thorsdottir", "Asgeirsdottir", "Gunnarsdottir", "Rafnsdottir", "Bender"};
        String[] lykilord = {"123", "abc", "blom", "gluggi345", "tre"};
        String[] netfang = {"rdt1hi.is", "ila7@hi.is", "egg25@hi.is", "eor5@hi.is", "rthb2@hi.is"};
        String[] simi = {"8637809", "6813866", "7724586", "8772435", "5520051"};
        PaymentInfo[] kortaupp = {new PaymentInfo(kennitala[0], "07/24", "4162333344445555", "123"),
                new PaymentInfo(kennitala[1], "08/22", "1111222255556666", "808"),
                new PaymentInfo(kennitala[2], "01/25", "3113444472278998", "420"),
                new PaymentInfo(kennitala[3], "10/30", "4030403040304030", "666"),
                new PaymentInfo(kennitala[4], "11/09", "6666000066660000", "101")};
        //ATH????? veit ekki hvernig a ad utfaera cart:((
        ArrayList[] kerra = {new ArrayList()};

        //For-lykkja sem byr til 5 Account hluti:
        for (int i = 0; i < 5; i++) {
            AddAccount(new Account(kennitala[i], fornafn[i], eftirnafn[i], lykilord[i], netfang[i],
                    simi[i], kortaupp[i], /*kerra[i]*/null));
        }


        //Buum til BookingData:
        float[] afslattur = {100, 10, 20};
        int[] saeti = {1, 2, 3, 4};
        Random rn = new Random();
/*
        //For-lykkja sem byr til 30 random bokanir:
        for (int i = 0; i < 30; i++) {
            AddBooking(new Booking(allTrips.get(i), allAccounts.get(i), rn.nextBoolean(),
                    afslattur[i % 3], rn.nextBoolean(), saeti[i % 4]));
        }
*/
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

    /*public void AddPaymentInfo(Account a) {
        allPaymentInfo.add(a.getPayInfo());
    }

    public ObservableList<PaymentInfo> getAllPaymentInfo() {
        return allPaymentInfo;
    }

    public void setAllPaymentInfo(ObservableList<PaymentInfo> allPaymentInfo) {
        this.allPaymentInfo = allPaymentInfo;
    }*/

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

    public ObservableList<PaymentInfo> getPaymentInfo(Account a) {
        ObservableList<PaymentInfo> payInfo = FXCollections.observableArrayList();
        payInfo.add(a.getPayInfo());
        return payInfo;
    }

    private ObservableList<Account> getAccount() {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        //accounts.add(account1);
        return accounts;
    }

    private ObservableList<Booking> getBooking() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        //bookings.add(new Booking(trip1, account1, false, 100, true, 2));
        return bookings;
    }

    private ObservableList<Trip> getTrip() {
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        /*trips.add(trip1);
        trips.add(new Trip("002", "Reykjavik", date1, "abc@abc.com", 30, 10, false, "City Tour", null, 30, false, 70));
        trips.add(new Trip("003", "Egilsstadir", date1, "abc@abc.com", 30, 10, false, "Sailing", null, 30, false, 300));
        trips.add(new Trip("004", "Akureyri", date1, "abc@abc.com", 30, 10, false, "Cycling", null, 30, false, 100));
        trips.add(new Trip("005", "Reykjavik", date1, "abc@abc.com", 30, 10, false, "Hiking", null, 30, false, 50));*/
        return trips;
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        for(Trip t : db.allTrips) {
            System.out.println(t.toString());
        }
    }

}

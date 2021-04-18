package dayTripSystem;

import java.util.ArrayList;

public class Account {
    private String userID;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private PaymentInfo payInfo;
    private ArrayList cart;


    public Account(String userID, String firstName, String lastName,
                   String password, String email, String phoneNumber,
                   PaymentInfo payInfo, ArrayList cart) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.payInfo = payInfo;
        this.cart = cart;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PaymentInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PaymentInfo payInfo) {
        this.payInfo = payInfo;
    }

    public ArrayList getCart() {
        return cart;
    }

    public void setCart(Trip t) {
        cart.add(t);
        // gamalt: á að uppfæra cart eftir að bookingController gerir addToCart()
        //Uppfaert sma
    }

}


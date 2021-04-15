package dayTripSystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Booking {
    private Trip tripUnit;
    private Account accountUnit;
    private boolean pickUp;
    private float discount;
    private boolean paymentConfirmed;
    private int nmbRes;

    private PaymentInfo paymentInfo;

    public Booking(Trip tripUnit, Account accountUnit, boolean pickUp,
                   float discount, boolean paymentConfirmed, int nmbRes) {
        this.tripUnit = tripUnit;
        this.accountUnit = accountUnit;
        this.pickUp = pickUp;
        this.discount = discount;
        this.paymentConfirmed = paymentConfirmed;
        this.nmbRes = nmbRes;
    }

    public Trip getTripUnit() {
        return this.tripUnit;
    }

    public Account getAccountUnit() {
        return this.accountUnit;
    }

    /*public float setPrice(float f) {
        f = price;
        return f;
    }*/

    public float setDiscount(float f) {
        float price = tripUnit.getPrice();
        return (f / 100) * price;
    }

    public void setNmbRes(int t) {
        //Event listener her ?
        if (tripUnit.getIsFullyBooked()) {
            tripUnit.setCapacity(tripUnit.getCapacity() - t);
        } else System.out.println("Sold Out");
    }

    public int getNmbRes() {
        return this.nmbRes;
    }

    /*private void addToPassList(boolean b) {
        if (setPaymentConfirmed() && tripUnit.isFullyBooked() && !tripUnit.isCanceledTrip()) {
            DB.addPassengers();
        } else System.out.println("Not Available");

    }*/

    public boolean setPaymentConfirmed() {
        String regex = "^[0-9]{3}$";
        Pattern p = Pattern.compile(regex);
        String str = paymentInfo.getCvv();
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public boolean getPaymentConfirmed() {
        return this.paymentConfirmed;
    }

    public boolean setPickUp() {
        //Button listener sem returns true ef hakad i else false.
        return false;
    }


}

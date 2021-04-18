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
        this.paymentInfo = accountUnit.getPayInfo();
    }

    public Trip getTripUnit() {
        return this.tripUnit;
    }

    public Account getAccountUnit() {
        return this.accountUnit;
    }

    public float setDiscount(float f) {
        float price = tripUnit.getPrice();
        return (f / 100) * price;
    }

    //tekur frá pláss í ferðinni, minnkar capacity
    //skilar true ef það tóks, false ef það voru ekki nægilega mörg laus pláss
    public boolean reserveSpot() {
        if (nmbRes > tripUnit.getCapacity()) {
            return false;
        }
        if (!tripUnit.getIsFullyBooked()) {
            tripUnit.setCapacity(tripUnit.getCapacity() - nmbRes);
            return true;
        } else return false;
    }

    public int getNmbRes() {
        return this.nmbRes;
    }

    /*private void addToPassList(boolean b) {
        if (setPaymentConfirmed() && tripUnit.isFullyBooked() && !tripUnit.isCanceledTrip()) {
            DB.addPassengers();
        } else System.out.println("Not Available");

    }*/

    public boolean isPaymentConfirmed() {
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

    //Bókar ferð, skilar true ef það tókst
    public boolean book() {
        return isPaymentConfirmed() && reserveSpot();
    }

}

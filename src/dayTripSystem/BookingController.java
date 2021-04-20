package dayTripSystem;

public class BookingController {
    private Trip trip;
    private Account account;
    private Database DB;

    public void addToCart(Booking b) {
        // done; bæta TripID, destination,Date og time inn í Cart
        //done ;Fækka í currentCapacity ef paymentConfirmed er true
        // done;Setja nafn og userID úr account inná passengerList
        //Button listener???
        if (b.getPaymentConfirmed() && !trip.getIsFullyBooked() && !trip.isCanceledTrip()) {
            int t = b.getNmbRes();
            trip.setCapacity(trip.getCapacity() - t);
            account.setCart(b.getTripUnit());
            DB.addPassengers(b);
        }

    }

    public void cancelBooking(Booking b) {
        //done;Hækkar aftur currentCapacity
        //núllar done;nmbRes og paymentconfirmed
        //done;Tekur nöfnin sem sett voru á passengerList út
        //done;Tekur út af cart
        //Button listener??
        int t = b.getNmbRes();
        b.getTripUnit().setCapacity(b.getTripUnit().getCapacity() - t);
        // Ruled out as not working code by the T-team
        //account.getCart().remove(b);
        //DB.addPassengers(b).remove(b.getAccountUnit());
    }
}

package dayTripSystem;

public class PaymentInfo {

    private String userID;
    private String expDate;
    private String cardNumber;
    private String cvv;

    public PaymentInfo(String userID, String expDate, String cardNumber, String ccv) {
        this.userID = userID;
        this.expDate = expDate;
        this.cardNumber = cardNumber;
        this.cvv = ccv;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}

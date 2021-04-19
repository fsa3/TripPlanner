package dayTripSystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentInfo {

    private String userID;
    private String expDate;
    private String cardNumber;
    private String cvv;
    private String paymentName;

    public PaymentInfo(String userID, String expDate, String cardNumber, String ccv, String paymentName) {
        this.userID = userID;
        this.expDate = expDate;
        this.cardNumber = cardNumber;
        this.cvv = ccv;
        this.paymentName = paymentName;
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

    public String getPaymentName() {
        return paymentName;
    }

    public boolean validate() {
        Pattern kortanr = Pattern.compile("\\b[0-9]{16}\\b");
        Matcher kortanrTekk = kortanr.matcher(getCardNumber());
        boolean kortnrTF = kortanrTekk.find();
        Pattern cvvReg = Pattern.compile("\\b([0-9]{3})\\b");
        Matcher cvvTekk = cvvReg.matcher(getCvv());
        boolean cvvTF = cvvTekk.find();
        Pattern expDate = Pattern.compile("[0-9]+");
        Matcher expDateTekk = expDate.matcher(getExpDate());
        boolean expDateTF = expDateTekk.find();
        Pattern kortNafn = Pattern.compile("([A-Za-z áæóíúýþöÁÆÓÍÚÞÖðé]+)\\s([A-Za-z áæóíúýþöÁÆÓÍÚÞÖðé]+)");
        Matcher kortNafnTekk = kortNafn.matcher(getPaymentName());
        boolean kortNafnTF = kortNafnTekk.find();

        if(kortnrTF && cvvTF && expDateTF && kortNafnTF) {
            return true;
        }

        else{
            return false;
        }
    }
}

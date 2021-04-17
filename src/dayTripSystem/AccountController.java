package dayTripSystem;

import java.util.ArrayList;

public class AccountController {
    private Account accountUser;
    private SearchController SC;

    public AccountController(Account account) {
        this.accountUser = account;
    }

    public Account newAccount(String userID, String firstName, String lastName, String password, String email,
                              String phoneNumber, PaymentInfo payInfo, ArrayList cart) {
        accountUser = new Account(userID, firstName, lastName, password, email, phoneNumber, payInfo, cart);
        return accountUser;
    }

    public Account logIn(String userID, String password) {
        return SC.getAccountByIDandPassword(userID, password);
    }


}

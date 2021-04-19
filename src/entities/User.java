package entities;

public class User {
    private int ID;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String ssNum;
    private String favoriteActivity;
    private int minHotelRating = 0;

    public User(String email, String firstName, String lastName, String password, String phoneNumber, String ssNum, int id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.ssNum = ssNum;
        this.ID = id;
    }

    public User(String email, String firstName, String lastName, String password, String phoneNumber, String ssNum, int id, String favoriteActivity, int minHotelRating) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.ssNum = ssNum;
        this.ID = id;
        this.favoriteActivity = favoriteActivity;
        this.minHotelRating = minHotelRating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSsNum() {
        return ssNum;
    }

    public void setSsNum(String ssNum) {
        this.ssNum = ssNum;
    }

    public String getFavoriteActivity() {
        return favoriteActivity;
    }

    public void setFavoriteActivity(String favoriteActivity) {
        this.favoriteActivity = favoriteActivity;
    }

    public int getMinHotelRating() {
        return minHotelRating;
    }

    public void setMinHotelRating(int minHotelRating) {
        this.minHotelRating = minHotelRating;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public int getId() {
        return ID;
    }
}

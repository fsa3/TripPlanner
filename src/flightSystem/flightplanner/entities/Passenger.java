package flightSystem.flightplanner.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger extends Person{
    private boolean insurance = false;
    private boolean luggage = false;
    private String healthIssues = "";
    private boolean wantsFood = false;
    // ?? afhv string
    private String extraLuggage = "";
    private String allergies = "";
    private boolean wheelchair = false;
    // skodum þetta betur, þessi constructor er insane
    public Passenger(int id, String firstName, String lastName, String kennitala, String email, String phoneNumber) {
        super(id, firstName, lastName, kennitala, email, phoneNumber);
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setLuggage(boolean luggage) {
        this.luggage = luggage;
    }

    public boolean isLuggage() {
        return luggage;
    }

    public void setHealthIssues(String healthIssues) {
        this.healthIssues = healthIssues;
    }

    public String getHealthIssues() {
        return healthIssues;
    }

    public void setWantsFood(boolean wantsFood) {
        this.wantsFood = wantsFood;
    }

    public boolean isWantsFood() {
        return wantsFood;
    }

    public void setExtraLuggage(String extraLuggage) {
        this.extraLuggage = extraLuggage;
    }

    public String getExtraLuggage() {
        return extraLuggage;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public boolean isWheelchair() {
        return wheelchair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return this.getFirstName().equals(((Passenger) o).getFirstName()) && this.getLastName().equals(((Passenger) o).getLastName());
    }
}

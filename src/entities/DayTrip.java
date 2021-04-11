package entities;

import java.util.Objects;

public class DayTrip {
    private String name;
    private String city;
    private int numHours;
    private boolean familyFriendly;

    public DayTrip(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumHours() {
        return numHours;
    }

    public void setNumHours(int numHours) {
        this.numHours = numHours;
    }

    public boolean isFamilyFriendly() {
        return familyFriendly;
    }

    public void setFamilyFriendly(boolean familyFriendly) {
        this.familyFriendly = familyFriendly;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTrip dayTrip = (DayTrip) o;
        return numHours == dayTrip.numHours && familyFriendly == dayTrip.familyFriendly && name.equals(dayTrip.name) && city.equals(dayTrip.city);
    }
}

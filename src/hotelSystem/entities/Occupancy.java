package hotelSystem.entities;


import java.sql.Date;
import java.text.SimpleDateFormat;


//Er þetta lokaútfærsla af date from og to? Gerði bara svoldið hér, þurfum að ræða þetta betur.
public class Occupancy {
    private Date dateFrom;
    private Date dateTo;

    public Occupancy(Date from, Date to) {
        this.dateFrom = from;
        this.dateTo = to;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public boolean isOccupied(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Date from = getDateFrom();
        Date to = getDateTo();

        //hotfix
        if (fmt.format(date).equals(fmt.format(from))) {
            return true;
        }

        return date.after(from) && date.before(to);
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        Date sqlDateFrom = new Date(now);
        Date sqlDateTo = new Date(now + (1000 * 60 * 60 * 24 * 7));
        System.out.println(sqlDateFrom);
        System.out.println(sqlDateTo);

        Occupancy test = new Occupancy(sqlDateFrom, sqlDateTo);
        System.out.println(test.getDateFrom());
        System.out.println(test.getDateTo());

        Date occtest1 = new Date(now - (1000 * 60 * 60 * 24 * 7));
        Date occtest2 = new Date(now + (1000 * 60 * 60 * 24 * 4));
        Date occtest3 = new Date(now + (1000 * 60 * 60 * 24 * 10));

        System.out.println(test.isOccupied(occtest1) + " date " + occtest1);
        System.out.println(test.isOccupied(occtest2) + " date " + occtest2);
        System.out.println(test.isOccupied(occtest3) + " date " + occtest3);
    }

}

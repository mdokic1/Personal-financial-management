package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private double budget;
    private int totalLimit;
    private int monthLimit;

    public Account(double budget, int totalLimit, int monthLimit){
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    protected Account(Parcel in) {
        budget = in.readDouble();
        totalLimit = in.readInt();
        monthLimit = in.readInt();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public Account() {

    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
    }

    public int getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(budget);
        dest.writeInt(totalLimit);
        dest.writeInt(monthLimit);
    }
}

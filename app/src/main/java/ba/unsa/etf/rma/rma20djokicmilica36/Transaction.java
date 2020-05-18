package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction implements Parcelable {
    private Integer transaction_id;
    private LocalDate date;
    private double amount;
    private String title;
    private transactionType type;
    private String itemDescription;
    private Integer transactionInterval;
    private LocalDate endDate;


    protected Transaction(Parcel in) {
        amount = in.readDouble();
        title = in.readString();
        itemDescription = in.readString();
        if (in.readByte() == 0) {
            transactionInterval = null;
        } else {
            transactionInterval = in.readInt();
        }
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                Objects.equals(date, that.date) &&
                Objects.equals(title, that.title) &&
                type == that.type &&
                Objects.equals(itemDescription, that.itemDescription) &&
                Objects.equals(transactionInterval, that.transactionInterval) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, title, type, itemDescription, transactionInterval, endDate);
    }

    public Transaction(LocalDate dat, double amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate) {

        this.date = dat;
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.itemDescription = desc;
        this.transactionInterval = trInterval;
        this.endDate = endDate;
    }

    public Transaction(Integer transaction_id, LocalDate dat, double amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate) {

        this.transaction_id = transaction_id;
        this.date = dat;
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.itemDescription = desc;
        this.transactionInterval = trInterval;
        this.endDate = endDate;
    }

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public transactionType getType() {
        return type;
    }

    public void setType(transactionType type) {
        this.type = type;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(Integer transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(title);
        dest.writeString(itemDescription);
        if (transactionInterval == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(transactionInterval);
        }
    }
}

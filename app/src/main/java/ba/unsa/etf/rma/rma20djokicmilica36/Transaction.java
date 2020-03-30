package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private LocalDate date;
    private int amount;
    private String title;
    private transactionType type;
    private String itemDescription;
    private Integer transactionInterval;
    private LocalDate endDate;


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

    public Transaction(LocalDate dat, int amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate) {

        this.date = dat;
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.itemDescription = desc;
        this.transactionInterval = trInterval;
        this.endDate = endDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
}

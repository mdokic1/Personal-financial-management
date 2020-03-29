package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private int amount;
    private String title;
    private transactionType type;
    private String itemDescription;
    private Integer transactionInterval;
    private LocalDate endDate;

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

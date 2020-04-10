package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDetailPresenter implements ITransactionDetailPresenter {
    private ITransactionDetailView view;
    private ITransactionListInteractor interactor;



    private Context context;

    public TransactionDetailPresenter(ITransactionDetailView view, Context context) {
        this.view       = view;
        this.interactor = new TransactionListInteractor();

        this.context    = context;
    }

    public ITransactionListInteractor getInteractor() {
        return interactor;
    }

    @Override
    public void setTransaction(Parcelable transaction) {
        this.transaction = (Transaction)transaction;
    }


    private Transaction transaction;

    public static ArrayList<String> izborTipa1 = new ArrayList<String>(){
        {
            add("Individual payment");
            add("Purchase");
            add("Regular payment");
            add("Individual income");
            add("Regular income");
        }
    };

    public ArrayList<String> getIzborTipa1() {
        return izborTipa1;
    }

    public static ArrayList<String> izborTipa2 = new ArrayList<String>(){
        {
            add("Regular payment");
            add("Individual payment");
            add("Purchase");
            add("Individual income");
            add("Regular income");
        }
    };

    public ArrayList<String> getIzborTipa2() {
        return izborTipa2;
    }

    public static ArrayList<String> izborTipa3 = new ArrayList<String>(){
        {
            add("Purchase");
            add("Regular payment");
            add("Individual payment");
            add("Individual income");
            add("Regular income");
        }
    };

    public ArrayList<String> getIzborTipa3() {
        return izborTipa3;
    }

    public static ArrayList<String> izborTipa4 = new ArrayList<String>(){
        {
            add("Individual income");
            add("Purchase");
            add("Regular payment");
            add("Individual payment");
            add("Regular income");
        }
    };

    public ArrayList<String> getIzborTipa4() {
        return izborTipa4;
    }

    public static ArrayList<String> izborTipa5 = new ArrayList<String>(){
        {
            add("Regular income");
            add("Individual income");
            add("Purchase");
            add("Regular payment");
            add("Individual payment");
        }
    };

    public ArrayList<String> getIzborTipa5() {
        return izborTipa5;
    }


    public TransactionDetailPresenter(Context context) {
        this.context    = context;
    }

    @Override
    public void create(LocalDate dat, double amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate){


        this.transaction = new Transaction(dat, amount, title, type, desc, trInterval, endDate);
    }

    @Override
    public ArrayList<Transaction> getModel(){
        return interactor.get();
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void refreshTransactionsChange(int indeks, Transaction t) {
        view.changeTransaction(interactor.get(), indeks, t);
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsRemove(int indeks){
        view.removeTransaction(interactor.get(), indeks);
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsAdd(Transaction t) {
        view.addTransaction(interactor.get(), t);
        view.notifyTransactionListDataSetChanged();
    }


}

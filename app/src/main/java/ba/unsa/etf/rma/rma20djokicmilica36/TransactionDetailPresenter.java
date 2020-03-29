package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDetailPresenter implements ITransactionDetailPresenter {
    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private Context context;

    public ITransactionListInteractor getInteractor() {
        return interactor;
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
    public void create(LocalDate dat, int amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate){


        this.transaction = new Transaction(dat, amount, title, type, desc, trInterval, endDate);
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    public ITransactionListView getView() {
        return view;
    }


}

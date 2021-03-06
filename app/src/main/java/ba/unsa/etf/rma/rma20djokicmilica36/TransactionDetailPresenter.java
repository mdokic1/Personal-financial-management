package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionDetailPresenter implements ITransactionDetailPresenter, TransactionListInteractor.OnTransactionsGetDone{
    private ITransactionDetailView view;
    private ITransactionListInteractor interactor = new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone) this, "");
    private Context context;

    private Transaction transaction;

    public TransactionDetailPresenter(ITransactionDetailView view, Context context) {
        this.view       = view;
       // this.interactor = new TransactionListInteractor();
        this.context    = context;
    }

    public ITransactionListInteractor getInteractor() {
        return interactor;
    }

    @Override
    public void setTransaction(Parcelable transaction) {
        this.transaction = (Transaction)transaction;
    }

    @Override
    public void azurirajBudzet(String budzet, String totalLimit, String monthLimit) {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "change account").execute(budzet, totalLimit, monthLimit);
    }

    @Override
    public void azurirajAccount() {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "refresh account").execute();
    }


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
        return interactor.getTransact();
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void refreshTransactionsChange(String id, String date, String amount, String title, String transactionType, String desc, String trInterval, String endDate) {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "change transaction").execute(id, date, amount, title, transactionType, desc, trInterval, endDate);
        //view.changeTransaction(interactor.getTransact(), indeks, t);
        //view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsRemove(String id){
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "delete transaction").execute(id);
        //view.removeTransaction(interactor.getTransact(), indeks);
        //view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsAdd(String date, String amount, String title, String transactionType, String desc, String trInterval, String endDate) {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "add transaction").execute(date, amount, title, transactionType, desc, trInterval, endDate);
        //view.addTransaction(interactor.getTransact(), t);
        //view.notifyTransactionListDataSetChanged();
    }

    /*@Override
    public void changeAccount(String budget, String totalLim, String monthLim) {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this, "change account").execute(budget,
                totalLim, monthLim);

        /*stari.setBudget(novi.getBudget());
        stari.setMonthLimit(novi.getMonthLimit());
        stari.setTotalLimit(novi.getTotalLimit());*/

   // }


    @Override
    public void onDone(ArrayList<Transaction> results) {

    }

    @Override
    public void returnGraphs(ArrayList<Float> mjesecniGrafPotrosnja, ArrayList<Float> mjesecniGrafZarada, ArrayList<Float> mjesecniGrafUkupno, ArrayList<Float> sedmicniGrafPotrosnja, ArrayList<Float> sedmicniGrafZarada, ArrayList<Float> sedmicniGrafUkupno, ArrayList<Float> dnevniGrafPotrosnja, ArrayList<Float> dnevniGrafZarada, ArrayList<Float> dnevniGrafUkupno) {

    }

    @Override
    public void returnAccount(Account racun) {
       view.setAccount(racun);
    }

    /*@Override
    public void onAddDone(Transaction transakcija) {
        view.addTransaction(interactor.getTransact(), transakcija);
        view.notifyTransactionListDataSetChanged();
        /*new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)
                this, "get transactions").execute("");*/
    //}

}

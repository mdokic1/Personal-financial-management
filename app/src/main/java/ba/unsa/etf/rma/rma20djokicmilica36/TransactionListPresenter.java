package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionListPresenter implements ITransactionListPresenter, TransactionListInteractor.OnTransactionsGetDone {
    private ITransactionListView view;
    //private ITransactionListInteractor interactor = new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this, " transactions");
     private ITransactionListInteractor interactor;
    private Context context;

    public static ArrayList<String> filtriranje = new ArrayList<String>(){
        {
            add("Filter by");
            add("All types");
            add("Individual payment");
            add("Purchase");
            add("Regular payment");
            add("Individual income");
            add("Regular income");
        }
    };

    public static ArrayList<String> sortiranje = new ArrayList<String>(){
        {
            add("Sort by");
            add("Price - Ascending");
            add("Price - Descending");
            add("Title - Ascending");
            add("Title - Descending");
            add("Date - Ascending");
            add("Date - Descending");
        }
    };

    public ITransactionListInteractor getInteractor() {
        return interactor;
    }

    public ArrayList<String> getSortiranje() {
        return sortiranje;
    }

    public ArrayList<String> getFiltriranje() {
        return filtriranje;
    }


    public TransactionListPresenter(ITransactionListView view, Context context) {
        this.view       = view;
        this.context    = context;
    }
    @Override
    public void refreshTransactions() {
       /* view.setTransactions(interactor.getTransact());
        view.notifyTransactionListDataSetChanged();*/
    }

    @Override
    public void refreshTransactionsByDate() {
        //view.setTransactions(interactor.getByDate());
        //view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshByDateTypeSorted(String typeId, String sort, String month, String year){
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "refresh transactions").execute(typeId, sort, month, year);
    }

    @Override
    public void refreshTransactionsByTypeSorted(String type, String sortType){
        /*new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)
                this, "GET transactions").execute(type, sortType);*/
        //view.setTransactionsByType(interactor.getByTypeSorted(type, sortType));
        //view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void increaseTransactionsMonth(String datum){
        int mj = 0, god = 0;
        int zarez = datum.indexOf(',');
        String mjesec = datum.substring(0, zarez);
        String godina = datum.substring(zarez + 1);
        if(mjesec.equals("January")) mj = 1;
        if(mjesec.equals("February")) mj = 2;
        if(mjesec.equals("March")) mj = 3;
        if(mjesec.equals("April")) mj = 4;
        if(mjesec.equals("May")) mj = 5;
        if(mjesec.equals("June")) mj = 6;
        if(mjesec.equals("July")) mj = 7;
        if(mjesec.equals("August")) mj = 8;
        if(mjesec.equals("September")) mj = 9;
        if(mjesec.equals("October")) mj = 10;
        if(mjesec.equals("November")) mj = 11;
        if(mjesec.equals("December")) mj = 12;

        god = Integer.valueOf(godina);
        if(mj <= 11){
            mj++;
        }
        else{
            mj = 1;
            god++;
        }
        view.setDate(Months.values()[mj - 1] + "," + Integer.toString(god));

    }

    @Override
    public void decreaseTransactionsMonth(String datum){
        int mj = 0, god = 0;
        int zarez = datum.indexOf(',');
        String mjesec = datum.substring(0, zarez);
        String godina = datum.substring(zarez + 1);
        if(mjesec.equals("January")) mj = 1;
        if(mjesec.equals("February")) mj = 2;
        if(mjesec.equals("March")) mj = 3;
        if(mjesec.equals("April")) mj = 4;
        if(mjesec.equals("May")) mj = 5;
        if(mjesec.equals("June")) mj = 6;
        if(mjesec.equals("July")) mj = 7;
        if(mjesec.equals("August")) mj = 8;
        if(mjesec.equals("September")) mj = 9;
        if(mjesec.equals("October")) mj = 10;
        if(mjesec.equals("November")) mj = 11;
        if(mjesec.equals("December")) mj = 12;

        god = Integer.valueOf(godina);
        if(mj >= 2){
            mj--;
        }
        else{
            mj = 12;
            god--;
        }
        view.setDate(Months.values()[mj - 1] + "," + Integer.toString(god));
    }

    @Override
    public double RefreshAmount(){
        double global = 0;
        /*for(Transaction t : interactor.getTransact()){
            if (t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.REGULARINCOME){
                if(t.getType() == transactionType.REGULARINCOME){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global += t.getAmount()*number;
                }
                else{
                    global += t.getAmount();
                }
            }
            else{
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global -= t.getAmount()*number;
                }
                else{
                    global -= t.getAmount();
                }
            }
        }*/
        return global;
    }

    @Override
    public int RefreshLimit() {
        //return getInteractor().getBModel().racun.getTotalLimit();
        return 0;
    }


    @Override
    public void onDone(ArrayList<Transaction> results) {
        view.setTransactions(results);
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void getTransactions(String query){
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)
                this, "get transactions").execute(query);

    }
}

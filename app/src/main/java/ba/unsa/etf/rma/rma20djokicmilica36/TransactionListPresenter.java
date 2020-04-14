package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionListPresenter implements ITransactionListPresenter {
    private ITransactionListView view;
    private static ITransactionListInteractor interactor;
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
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }
    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsByDate() {
        view.setTransactions(interactor.getByDate());
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshTransactionsByTypeSorted(String type, String sortType){
        view.setTransactionsByType(interactor.getByTypeSorted(type, sortType));
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void increaseTransactionsMonth(){
        view.setDate(interactor.increaseMonth());

    }

    @Override
    public void decreaseTransactionsMonth(){
        view.setDate(interactor.decreaseMonth());

    }

    @Override
    public double RefreshAmount(){
        double global = 0;
        for(Transaction t : getInteractor().get()){
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
        }
        return global;
    }

    @Override
    public int RefreshLimit() {
        return getInteractor().getBModel().racun.getTotalLimit();
    }


}

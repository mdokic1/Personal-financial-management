package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionListPresenter implements ITransactionListPresenter {
    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private Context context;



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
    public void increaseTransactionsMonth(){
        view.setDate(interactor.increaseMonth());

    }

    @Override
    public void decreaseTransactionsMonth(){
        view.setDate(interactor.decreaseMonth());

    }


}

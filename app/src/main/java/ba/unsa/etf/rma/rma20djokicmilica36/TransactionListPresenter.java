package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;

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
    public void increaseTransactionsMonth(){
        view.setDate(interactor.increaseMonth().toString());
        //view.notifyDateChanged();
    }

    @Override
    public void decreaseTransactionsMonth(){
        view.setDate(interactor.decreaseMonth().toString());
        //view.notifyDateChanged();
    }


}

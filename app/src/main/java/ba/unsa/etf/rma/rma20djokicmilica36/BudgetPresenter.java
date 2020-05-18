package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.os.Parcelable;

public class BudgetPresenter implements IBudgetPresenter {
    private IBudgetView view;
    private ITransactionListInteractor interactor;
    private Context context;

    private Account account;

    public BudgetPresenter(IBudgetView view, Context context) {
        this.view       = view;
        //this.interactor = new TransactionListInteractor();
        this.context    = context;
    }

    public ITransactionListInteractor getInteractor() {
        return interactor;
    }

    @Override
    public void create(double budget, int total, int month){

        this.account = new Account(budget, total, month);
    }

    @Override
    public void setAccount(Parcelable account) {
        this.account = (Account)account;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void ChangeAccount(Account stari, Account novi) {
        view.Change(getInteractor().getBModel().racun, novi);
    }
}

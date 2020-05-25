package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.os.Parcelable;

import java.util.ArrayList;

public class BudgetPresenter implements IBudgetPresenter, TransactionListInteractor.OnTransactionsGetDone {
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
    public void changeAccount(String budget, String totalLim, String monthLim) {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this, "change account").execute(budget,
                totalLim, monthLim);
    }

    @Override
    public void refreshAccount() {
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this, "refresh account").execute();
    }


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


}

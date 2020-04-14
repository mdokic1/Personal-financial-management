package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionListPresenter {
    void refreshTransactions();

    void refreshTransactionsByDate();

    void refreshTransactionsByTypeSorted(String type, String sortType);

    void increaseTransactionsMonth();

    void decreaseTransactionsMonth();

    ArrayList<String> getFiltriranje();

    ArrayList<String> getSortiranje();

    ITransactionListInteractor getInteractor();

    double RefreshAmount();

    int RefreshLimit();
}

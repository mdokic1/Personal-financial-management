package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionListPresenter {
    void refreshTransactions();

    void refreshTransactionsByDate();

    void refreshByDateTypeSorted(String typeId, String sort, String month, String year);

    void refreshTransactionsByTypeSorted(String type, String sortType);

    void increaseTransactionsMonth(String datum);

    void decreaseTransactionsMonth(String datum);

    ArrayList<String> getFiltriranje();

    ArrayList<String> getSortiranje();

    ITransactionListInteractor getInteractor();

    double RefreshAmount();

    int RefreshLimit();

    void getTransactions(String query);
}

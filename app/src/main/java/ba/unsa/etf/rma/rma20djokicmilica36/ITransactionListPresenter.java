package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionListPresenter {
    void refreshTransactions();

    void refreshTransactionsByDate();

    void increaseTransactionsMonth();

    void decreaseTransactionsMonth();

    void refreshTransactionsByType(String selectedItem);

    ArrayList<String> getFiltriranje();
}

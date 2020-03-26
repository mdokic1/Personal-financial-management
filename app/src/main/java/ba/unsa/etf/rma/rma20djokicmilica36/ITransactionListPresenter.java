package ba.unsa.etf.rma.rma20djokicmilica36;

public interface ITransactionListPresenter {
    void refreshTransactions();

    void refreshTransactionsByDate();

    void increaseTransactionsMonth();

    void decreaseTransactionsMonth();
}

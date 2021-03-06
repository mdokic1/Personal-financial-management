package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

interface ITransactionListView {
    void setTransactions(ArrayList<Transaction> transactions);
    void notifyTransactionListDataSetChanged();

    void setDate(String date);

    void setTransactionsByType(ArrayList<Transaction> transactions);

    void setTransactionsSorted(ArrayList<Transaction> transactions);

    void setRacun(Account racun);
}

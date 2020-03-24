package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

interface ITransactionListView {
    void setTransactions(ArrayList<Transaction> transactions);
    void notifyTransactionListDataSetChanged();
}

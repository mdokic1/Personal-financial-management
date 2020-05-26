package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionDetailView {


    void notifyTransactionListDataSetChanged();

    void changeTransaction(ArrayList<Transaction> transactions, int indeks, Transaction t);

    void removeTransaction(ArrayList<Transaction> transactions, int indeks);

    void addTransaction(ArrayList<Transaction> transactions, Transaction t);


    void setTransactions(ArrayList<Transaction> results);

    void setAccount(Account racun);
}

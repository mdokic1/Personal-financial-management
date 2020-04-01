package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

interface ITransactionDetailPresenter {

    void create(LocalDate dat, double amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate);

    Transaction getTransaction();


    ArrayList<Transaction> getModel();

    ArrayList<String> getIzborTipa1();
    ArrayList<String> getIzborTipa2();
    ArrayList<String> getIzborTipa3();
    ArrayList<String> getIzborTipa4();
    ArrayList<String> getIzborTipa5();

    void refreshTransactionsChange(int indeks, Transaction t);

    void refreshTransactionsRemove(int indeks);

    void refreshTransactionsAdd(Transaction t);

    ITransactionListInteractor getInteractor();


}

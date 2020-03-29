package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

interface ITransactionDetailPresenter {

    void create(LocalDate dat, int amount, String title, transactionType type, String desc, Integer trInterval, LocalDate endDate);

    Transaction getTransaction();

    ArrayList<String> getIzborTipa1();
    ArrayList<String> getIzborTipa2();
    ArrayList<String> getIzborTipa3();
    ArrayList<String> getIzborTipa4();
    ArrayList<String> getIzborTipa5();
}

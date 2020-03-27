package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionListInteractor {


    String increaseMonth();

    String decreaseMonth();

    ArrayList<Transaction> getByDate();

    ArrayList<Transaction> get();

    ArrayList<Transaction> getByType(String type);
}

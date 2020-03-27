package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public interface ITransactionListInteractor {


    ArrayList<Transaction> getByTypeSorted(String type, String sort);

    String increaseMonth();

    String decreaseMonth();

    ArrayList<Transaction> getByDate();

    ArrayList<Transaction> get();

}

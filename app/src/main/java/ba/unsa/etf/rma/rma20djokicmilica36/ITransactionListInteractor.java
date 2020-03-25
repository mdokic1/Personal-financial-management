package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ITransactionListInteractor {
    ArrayList<Transaction> get();

    LocalDate increaseMonth();

    LocalDate decreaseMonth();
}

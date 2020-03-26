package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsModel {

    public static LocalDate trDatum = LocalDate.now();

    public LocalDate getTrDatum() {
        return trDatum;
    }


    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {

        {
            add(new Transaction(LocalDate.of(2020, 1, 4), 500, "Transakcija1",
                    transactionType.INDIVIDUALPAYMENT, "Proizvod1", null, null));

            add(new Transaction(LocalDate.of(2020, 2, 16), 1000, "Transakcija2",
                    transactionType.REGULARPAYMENT, "Proizvod2", 30, LocalDate.of(2020, 6, 16)));

        }

    };

}

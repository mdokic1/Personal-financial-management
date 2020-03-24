package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsModel {
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public void load() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            transactions.add(new Transaction(LocalDate.of(2020, 1, 4), 500, "Transakcija1",
            transactionType.INDIVIDUALPAYMENT, "Proizvod1", null, null));

            transactions.add(new Transaction(LocalDate.of(2020, 2, 16), 1000, "Transakcija2",
                    transactionType.REGULARPAYMENT, "Proizvod2", 30, LocalDate.of(2020, 6, 16)));
        }

    }


}

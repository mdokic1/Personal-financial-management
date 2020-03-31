package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsModel {

    public static LocalDate trDatum = LocalDate.now();

    public static Account racun = new Account(10000, 2000, 2000 );

    public static void setTransactions(ArrayList<Transaction> transactions) {
        TransactionsModel.transactions = transactions;
    }

    public Account getRacun() {
        return racun;
    }

    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {

        {
            add(new Transaction(LocalDate.of(2020, 1, 4), 50, "Transakcija1",
                    transactionType.INDIVIDUALPAYMENT, "Proizvod1", null, null));

            add(new Transaction(LocalDate.of(2020, 2, 16), 100, "Transakcija2",
                    transactionType.REGULARPAYMENT, "Proizvod2", 30, LocalDate.of(2020, 6, 16)));

            add(new Transaction(LocalDate.of(2020, 2, 20), 400, "Transakcija3",
                    transactionType.REGULARINCOME, null, 30, LocalDate.of(2020, 8, 20)));

        }

    };

}

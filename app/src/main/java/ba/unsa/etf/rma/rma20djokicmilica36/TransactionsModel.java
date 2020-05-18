package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsModel {

    public static LocalDate trDatum = LocalDate.now();


    /*public static void setTransactions(ArrayList<Transaction> transactions) {
        TransactionsModel.transactions = transactions;
    }*/


    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>() {

        {
            add(new Transaction(LocalDate.of(2020,3,25), 253.0, "Poklon", transactionType.INDIVIDUALPAYMENT, "Kupovina poklona za rodjendan", null, null));
            add(new Transaction(LocalDate.of(2020, 1, 1), 150.65, "Struja", transactionType.REGULARPAYMENT, "Placanje racuna", 30, LocalDate.of(2020, 12, 1)));
            add(new Transaction(LocalDate.of(2020, 5, 6), 900, "Laptop", transactionType.PURCHASE, "Poslovni laptop", null, null));
            add(new Transaction(LocalDate.of(2019, 12, 31), 10000.0, "Nagradna igra", transactionType.INDIVIDUALINCOME, null, null, null));
            add(new Transaction(LocalDate.of(2020, 1, 12), 85.0, "Stipendija", transactionType.REGULARINCOME, null, 60, LocalDate.of(2020, 9, 1)));
            add(new Transaction(LocalDate.of(2020, 3, 1), 90.0, "Kredit", transactionType.REGULARPAYMENT, "Rata kredita za stan", 16, LocalDate.of(2020, 12, 1)));
            add(new Transaction(LocalDate.of(2020, 4, 1), 41.95, "Namirnice", transactionType.INDIVIDUALPAYMENT, "Merkator", null, null));
            add(new Transaction(LocalDate.of(2020, 4, 5), 400.0, "Ormar", transactionType.PURCHASE, "Namjestaj za sobu", null, null));
            add(new Transaction(LocalDate.of(2020, 5, 31), 8500.0, "Bingo dobitak", transactionType.INDIVIDUALINCOME, null, null,null));
            add(new Transaction(LocalDate.of(2020, 1, 5), 868.0, "Plata", transactionType.REGULARINCOME, null, 35, LocalDate.of(2020, 12, 8)));
            add(new Transaction(LocalDate.of(2020, 7, 27), 1000.0, "Godisnji odmor", transactionType.INDIVIDUALPAYMENT, "Rezervacija hotela i kupovina karata", null, null));
            add(new Transaction(LocalDate.of(2020, 7, 16), 155.0, "Zamrzivac", transactionType.PURCHASE, "Opremanje kuhinje", null, null));
            add(new Transaction(LocalDate.of(2020, 1, 3), 432.90, "Penzija", transactionType.REGULARINCOME, null, 28, LocalDate.of(2021, 2, 18)));
            add(new Transaction(LocalDate.of(2020,2,1), 30.0, "Odvoz smeca", transactionType.REGULARPAYMENT, "Placanje odvoza smeca", 15, LocalDate.of(2020, 5, 1)));
            add(new Transaction(LocalDate.of(2020, 4, 16), 608.0, "Poklon", transactionType.INDIVIDUALINCOME, null, null, null));
            add(new Transaction(LocalDate.of(2020, 5, 18), 600.0, "Haljina", transactionType.PURCHASE, "Haljina za svadbu", null, null));
            add(new Transaction(LocalDate.of(2020, 10, 6), 700.50, "Zimnica", transactionType.INDIVIDUALPAYMENT, "Krompir, luk, paprike", null, null));
            add(new Transaction(LocalDate.of(2020, 3, 16), 100.0, "Internet", transactionType.REGULARPAYMENT, "Telemach", 10, LocalDate.of(2020, 8, 16)));
            add(new Transaction(LocalDate.of(2020, 1, 9), 64.5, "Naknada", transactionType.REGULARINCOME, null, 90, LocalDate.of(2021, 1, 9)));
            add(new Transaction(LocalDate.of(2020, 2, 23), 756.0, "Nagrada", transactionType.INDIVIDUALINCOME, null, null, null));
        }

    };

}

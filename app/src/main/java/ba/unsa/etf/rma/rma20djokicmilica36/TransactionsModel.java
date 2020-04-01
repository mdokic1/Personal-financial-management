package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsModel {

    public static LocalDate trDatum = LocalDate.now();

    public static Account racun = new Account(61041.9, 2000, 2000 );

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
            /*add(new Transaction(LocalDate.of(2020, 1, 27), 150.0, "Ogrlica", transactionType.PURCHASE, "Zlatni lanac sa smaragdom", null, null));
            add(new Transaction(LocalDate.of(2019, 6, 18), 3200.0, "Plata", transactionType.REGULARINCOME, null, 30, LocalDate.of(2020, 12, 18)));
            add(new Transaction(LocalDate.of(2020, 2, 5), 16.95, "KONZUM - kasa", transactionType.INDIVIDUALPAYMENT, "Kupovina namire u KONZUM-u", null, null));
            add(new Transaction(LocalDate.of(2020, 4, 14), 235.0, "Elektrotehnički fakultet - isplata", transactionType.INDIVIDUALINCOME, null, null, null));
            add(new Transaction(LocalDate.of(2020, 3, 7), 30.0, "Netflix", transactionType.REGULARPAYMENT, "Mjesečna članarina", 30, LocalDate.of(2020, 9, 7)));
            add(new Transaction(LocalDate.of(2020, 1, 14), 800.0, "Apple Watch Series 3", transactionType.PURCHASE, "Pametni sat kompanije Apple", null, null));
            add(new Transaction(LocalDate.of(2020, 5, 1), 150.0, "Stipendija - uplata", transactionType.REGULARINCOME, null, 30, LocalDate.of(2020, 11, 1)));
            add(new Transaction(LocalDate.of(2020, 3, 19), 169.5, "NIKE - kasa", transactionType.INDIVIDUALPAYMENT, "Nike AirForce One tene", null, null));
            add(new Transaction(LocalDate.of(2019,10,5), 17.6, "Račun", transactionType.REGULARPAYMENT, "Mjesečni račun za vodu", 30, LocalDate.of(2020, 10, 5)));
            add(new Transaction(LocalDate.of(2020, 2, 3), 200.0, "Koncert", transactionType.INDIVIDUALINCOME, "Koncert klasične muzike za gitaru", null, null));
            add(new Transaction(LocalDate.of(2020,3,3), 55.0, "Školski pribor", transactionType.INDIVIDUALPAYMENT, "Knjige, sveske i olovke", null, null));
            add(new Transaction(LocalDate.of(2019, 11, 15), 5.5, "Sir", transactionType.PURCHASE, "Sir gauda 500g", null, null));
            add(new Transaction(LocalDate.of(2020, 1, 1), 3000.0, "BINGO", transactionType.INDIVIDUALINCOME, "Dobitnički listić", null, null));
            add(new Transaction(LocalDate.of(2020, 4, 23), 1.0, "Kafa", transactionType.REGULARPAYMENT, "Aparat za kafu", 1, LocalDate.of(2021, 10, 23)));
            add(new Transaction(LocalDate.of(2020, 2, 20), 50.0, "Amazon narudžba", transactionType.PURCHASE, "Drveni prsten koji sija u mraku", null, null));
            add(new Transaction(LocalDate.of(2019, 6, 6), 20.0, "Apple+", transactionType.REGULARPAYMENT, "Mjesečna članarina za servis Apple+", 25, LocalDate.of(2020, 8, 6)));
            add(new Transaction(LocalDate.of(2020, 2, 18), 100.0, "Džeparac", transactionType.REGULARINCOME, null, 30, LocalDate.of(2020, 9, 18)));
            add(new Transaction(LocalDate.of(2020, 1, 12), 600.0, "Apple AirPods Pro", transactionType.PURCHASE, "Bluetooth in-ear slušalice kompanije Apple", null, null));
            add(new Transaction(LocalDate.of(2020, 4, 5), 200.0, "Google", transactionType.REGULARINCOME, null, 30, LocalDate.of(2020, 12, 5)));
            add(new Transaction(LocalDate.of(2020, 6, 3), 40.0, "Torta", transactionType.PURCHASE, "Domaća torta iz Ramisa", null, null));
            add(new Transaction(LocalDate.of(2020, 3, 5), 26.95, "DM - kasa", transactionType.INDIVIDUALPAYMENT, "Kupovina zdravih slatkiša i čajeva", null, null));*/

        }

    };

}

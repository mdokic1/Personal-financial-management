package ba.unsa.etf.rma.rma20djokicmilica36;

import com.github.mikephil.charting.data.BarDataSet;

import java.util.ArrayList;

public interface ITransactionListInteractor {


    ArrayList<Transaction> getByTypeSorted(String type, String sort);

    boolean CheckTotalLimit(Transaction t);

    boolean CheckMonthLimit(Transaction t);

    ArrayList<Transaction> getByDate();

    int pocetakSedmice();

    int getMjLimit();

    //ArrayList<Transaction> get();

    public TransactionsModel getModel();
    public BudgetModel getBModel();

    int getTotLimit();

    float MjesecnaPotrosnja(int mjesec);

    float MjesecnaZarada(int mjesec);

    float MjesecnoUkupno(int mjesec);

    float SedmicnaPotrosnja(int sedmica);

    float SedmicnaZarada(int sedmica);

    float SedmicnoUkupno(int sedmica);

    float DnevnaPotrosnja(int dan);

    float DnevnaZarada(int dan);

    float DnevnoUkupno(int dan);

    ArrayList<Transaction> getTransact();
}

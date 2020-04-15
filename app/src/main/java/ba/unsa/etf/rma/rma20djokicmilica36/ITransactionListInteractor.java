package ba.unsa.etf.rma.rma20djokicmilica36;

import com.github.mikephil.charting.data.BarDataSet;

import java.util.ArrayList;

public interface ITransactionListInteractor {


    ArrayList<Transaction> getByTypeSorted(String type, String sort);

    String increaseMonth();

    String decreaseMonth();

    boolean CheckTotalLimit(Transaction t);

    boolean CheckMonthLimit(Transaction t);

    ArrayList<Transaction> getByDate();

    int getMjLimit();

    ArrayList<Transaction> get();

    public TransactionsModel getModel();
    public BudgetModel getBModel();

    int getTotLimit();

    float MjesecnaPotrosnja(int mjesec);

    float MjesecnaZarada(int mjesec);

    float Ukupno(int mjesec);
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionListInteractor implements ITransactionListInteractor {
    LocalDate trDatum = TransactionsModel.trDatum;
    @Override
    public ArrayList<Transaction> get() {
        return TransactionsModel.transactions;
    }

    @Override
    public String increaseMonth(){
        int mj = trDatum.getMonthValue();
        int god = trDatum.getYear();
        if(mj <= 11){
            mj++;
            trDatum = trDatum.withMonth(mj);
            trDatum = trDatum.withYear(god);
        }
        else{
            mj = 1;
            god++;
            trDatum = trDatum.withMonth(mj);
            trDatum = trDatum.withYear(god);
        }
        return (Months.values()[trDatum.getMonthValue() - 1] + "," + Integer.toString(trDatum.getYear()));
    }

    @Override
    public String decreaseMonth(){
        int mj = trDatum.getMonthValue();
        int god = trDatum.getYear();
        if(mj >= 2){
            mj--;
            trDatum = trDatum.withMonth(mj);
            trDatum = trDatum.withYear(god);
        }
        else{
            mj = 12;
            god--;
            trDatum = trDatum.withMonth(mj);
            trDatum = trDatum.withYear(god);
        }
        return (Months.values()[trDatum.getMonthValue() - 1] + "," + Integer.toString(trDatum.getYear()));
    }

}

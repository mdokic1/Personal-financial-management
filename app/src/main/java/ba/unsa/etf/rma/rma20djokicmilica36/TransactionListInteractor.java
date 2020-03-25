package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionListInteractor implements ITransactionListInteractor {
    private LocalDate trDatum;
    @Override
    public ArrayList<Transaction> get() {
        return TransactionsModel.transactions;
    }

    @Override
    public LocalDate increaseMonth(){
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
        return trDatum;
    }

    @Override
    public LocalDate decreaseMonth(){
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
        return trDatum;
    }

}

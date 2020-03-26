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
    public ArrayList<Transaction> getByDate(){
        ArrayList<Transaction> odgovarajuce = new ArrayList<>();
        ArrayList<Transaction> sve = get();
        for(Transaction t : sve){
            if(t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().getMonthValue() == trDatum.getMonthValue() && t.getDate().getYear() == trDatum.getYear()){
                    odgovarajuce.add(t);
                }
            }

            if (t.getType() == transactionType.REGULARPAYMENT || t.getType() == transactionType.REGULARINCOME) {
                if(t.getDate().getMonthValue() <= trDatum.getMonthValue() && t.getEndDate().getMonthValue() >= trDatum.getMonthValue()
                  && t.getDate().getYear() <= trDatum.getYear() && t.getEndDate().getYear() >= trDatum.getYear()){
                    odgovarajuce.add(t);
                }
            }
        }
        return odgovarajuce;
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

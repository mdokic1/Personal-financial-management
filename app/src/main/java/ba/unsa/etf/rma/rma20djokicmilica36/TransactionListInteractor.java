package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

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
    public ArrayList<Transaction> getByTypeSorted(String type, String sortType) {
        ArrayList<Transaction> odgovarajuce = new ArrayList<>();
        ArrayList<Transaction> sortirane = new ArrayList<>();
        ArrayList<Transaction> sve = getByDate();
        for(Transaction t : sve){
            if(t.getType().equals(transactionType.INDIVIDUALPAYMENT) && type.equals("Individual payment")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.REGULARPAYMENT) && type.equals("Regular payment")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.PURCHASE) && type.equals("Purchase")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.REGULARINCOME) && type.equals("Regular income")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.INDIVIDUALINCOME) && type.equals("Individual income")){
                odgovarajuce.add(t);
            }
            if(type.equals("All types")){
                odgovarajuce.add(t);
            }
        }

        if(sortType.equals("Sort by")){
            return odgovarajuce;
        }

        if(sortType.equals("Price - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingInt(Transaction::getAmount)).collect(Collectors.toList());
        }

        if(sortType.equals("Price - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingInt(Transaction::getAmount).reversed()).collect(Collectors.toList());
        }

        if(sortType.equals("Title - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getTitle)).collect(Collectors.toList());
        }

        if(sortType.equals("Title - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getTitle).reversed()).collect(Collectors.toList());
        }

        if(sortType.equals("Date - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getDate)).collect(Collectors.toList());
        }

        if(sortType.equals("Date - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getDate).reversed()).collect(Collectors.toList());
        }


        return sortirane;
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

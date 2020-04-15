package ba.unsa.etf.rma.rma20djokicmilica36;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionListInteractor implements ITransactionListInteractor {

    LocalDate trDatum = TransactionsModel.trDatum;
    TransactionsModel model;
    BudgetModel bModel;;



    public TransactionsModel getModel() {
        return model;
    }
    public BudgetModel getBModel() {
        return bModel;
    }

    ArrayList<Integer> brojDanaUMjesecu = new ArrayList<Integer>(){
        {
            add(31);
            add(29);
            add(31);
            add(30);
            add(31);
            add(30);
            add(31);
            add(31);
            add(30);
            add(31);
            add(30);
            add(31);
        }
    };

    @Override
    public int getTotLimit() {
        return bModel.racun.getTotalLimit();
    }

    @Override
    public float MjesecnaPotrosnja(int mjesec) {
        float potrosnja = 0.0f;
        for(Transaction t : get()){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().getYear() == LocalDate.now().getYear() && t.getDate().getMonthValue() == mjesec){
                    potrosnja += t.getAmount();
                }
            }
            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();
                if(!(pocetak.getYear() > LocalDate.now().getYear() || kraj.getYear() < LocalDate.now().getYear())){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    if(t.getDate().getMonthValue() == mjesec){
                        potrosnja += t.getAmount();
                    }
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(!(novi.getYear() == LocalDate.now().getYear())){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                    while(novi.getYear() == LocalDate.now().getYear() && novi.compareTo(t.getEndDate()) <= 0){
                        if(novi.getMonthValue() == mjesec){
                            potrosnja += t.getAmount();
                        }

                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }

            }
        }
        return potrosnja;
    }

    @Override
    public float MjesecnaZarada(int mjesec) {
        float zarada = 0.0f;

        for(Transaction t : get()){
            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().getYear() == LocalDate.now().getYear() && t.getDate().getMonthValue() == mjesec){
                    zarada += t.getAmount();
                }
            }
            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();
                if(!(pocetak.getYear() > LocalDate.now().getYear() || kraj.getYear() < LocalDate.now().getYear())){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    if(t.getDate().getMonthValue() == mjesec){
                        zarada += t.getAmount();
                    }
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(!(novi.getYear() == LocalDate.now().getYear())){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                    while(novi.getYear() == LocalDate.now().getYear() && novi.compareTo(t.getEndDate()) <= 0){
                        if(novi.getMonthValue() == mjesec){
                            zarada += t.getAmount();
                        }

                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }

            }
        }

        return zarada;
    }

    @Override
    public float MjesecnoUkupno(int mjesec) {
       float ukupno = 0.0f;
       for(int i = 1; i <= 12; i++){
           if(i <= mjesec){
               ukupno += (MjesecnaZarada(i) - MjesecnaPotrosnja(i));
           }
       }
       return ukupno;

    }

    @Override
    public float SedmicnaPotrosnja(int sedmica) {
        float potrosnja = 0.0f;

        LocalDate pocetak = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1 + 7*(sedmica - 1));
        LocalDate kraj = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 7 + 7*(sedmica - 1));

        for(Transaction t : get()){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().compareTo(pocetak) >= 0 && t.getDate().compareTo(kraj) <= 0){
                    potrosnja += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate poc = t.getDate();
                LocalDate kr = t.getEndDate();

                if(poc.compareTo(kraj) <= 0 && kr.compareTo(pocetak) >= 0){
                     if(poc.compareTo(pocetak) >= 0 && poc.compareTo(kraj) <= 0){
                         potrosnja += t.getAmount();
                     }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(pocetak) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    while(novi.compareTo(pocetak) >= 0 && novi.compareTo(kraj) <= 0 && novi.compareTo(t.getEndDate()) <= 0){
                        potrosnja += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }
            }
        }

        return potrosnja;
    }

    @Override
    public float SedmicnaZarada(int sedmica) {
        float zarada = 0.0f;

        LocalDate pocetak = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1 + 7*(sedmica - 1));
        LocalDate kraj = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 7 + 7*(sedmica - 1));

        for(Transaction t : get()){
            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().compareTo(pocetak) >= 0 && t.getDate().compareTo(kraj) <= 0){
                    zarada += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate poc = t.getDate();
                LocalDate kr = t.getEndDate();

                if(poc.compareTo(kraj) <= 0 && kr.compareTo(pocetak) >= 0){
                    if(poc.compareTo(pocetak) >= 0 && poc.compareTo(kraj) <= 0){
                        zarada += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(pocetak) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    while(novi.compareTo(pocetak) >= 0 && novi.compareTo(kraj) <= 0 && novi.compareTo(t.getEndDate()) <= 0){
                        zarada += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }
            }
        }

        return zarada;
    }

    @Override
    public float SedmicnoUkupno(int sedmica) {
        float ukupno = 0.0f;

        for(int i = 1; i <= 4; i++){
            if(i <= sedmica){
                ukupno += (SedmicnaZarada(i) - SedmicnaPotrosnja(i));
            }
        }

        return ukupno;
    }

    @Override
    public float DnevnaPotrosnja(int dan) {
        float potrosnja = 0.0f;

        LocalDate datum = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dan);

        for(Transaction t : get()){

            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().compareTo(datum) == 0){
                    potrosnja += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();

                if(pocetak.compareTo(datum) <= 0 && kraj.compareTo(datum) >= 0){
                    if(pocetak.compareTo(datum) == 0){
                        potrosnja += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(datum) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    if(novi.compareTo(datum) == 0){
                        potrosnja += t.getAmount();
                    }
                }
            }
        }

        return potrosnja;
    }

    @Override
    public float DnevnaZarada(int dan) {
        float zarada = 0.0f;

        LocalDate datum = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dan);

        for(Transaction t : get()){

            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().compareTo(datum) == 0){
                    zarada += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();

                if(pocetak.compareTo(datum) <= 0 && kraj.compareTo(datum) >= 0){
                    if(pocetak.compareTo(datum) == 0){
                        zarada += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(datum) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    if(novi.compareTo(datum) == 0){
                        zarada += t.getAmount();
                    }
                }
            }
        }

        return zarada;

    }

    @Override
    public float DnevnoUkupno(int dan) {
        float ukupno = 0.0f;
        int pocSedmice = pocetakSedmice();
        int trenutniMjesec = LocalDate.now().getMonthValue();
        int krajSedmice = pocSedmice + 6;
        if(pocSedmice > 28){
            krajSedmice = brojDanaUMjesecu.get(trenutniMjesec - 1);
        }
        for(int i = pocSedmice; i <= krajSedmice; i++){
            if(i <= dan){
                ukupno += (DnevnaZarada(i) - DnevnaPotrosnja(i));
            }

        }

        return ukupno;
    }

    @Override
    public int pocetakSedmice(){
        int trenutniDan = LocalDate.now().getDayOfMonth();
        int trenutniMjesec = LocalDate.now().getMonthValue();

        int pocPetlje = 0;
        int krajPetlje = 0;

        int pocetni = 1;
        int krajnji = brojDanaUMjesecu.get(trenutniMjesec - 1);
        int krajSedmice = 7;
        if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
            pocPetlje = pocetni;
            krajPetlje = krajSedmice;
        }
        else{
            while(pocetni <= krajnji){

                if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
                    pocPetlje = pocetni;
                    krajPetlje = krajSedmice;
                    break;
                }

                pocetni = pocetni + 7;
                krajSedmice = krajSedmice + 7;
                if(pocetni > 28){
                    krajSedmice = krajnji;
                    pocPetlje = pocetni;
                    krajPetlje = krajnji;
                }
            }
        }
        return pocetni;
    }


    @Override
    public int getMjLimit() {
        return bModel.racun.getMonthLimit();
    }


    @Override
    public ArrayList<Transaction> get() {
        return model.transactions;
    }

    @Override
    public boolean CheckTotalLimit(Transaction transaction){
        double potrosnja = 0;
        for(Transaction t : get()){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                potrosnja += t.getAmount();
            }
            if(t.getType() == transactionType.REGULARPAYMENT){
                long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                long number = numOfDays/t.getTransactionInterval();
                potrosnja += t.getAmount()*number;
            }
        }

        if(transaction.getType() == transactionType.REGULARPAYMENT){
            long numOfDays = DAYS.between(transaction.getDate(), transaction.getEndDate());
            long number = numOfDays/transaction.getTransactionInterval();
            potrosnja += transaction.getAmount()*number;
        }

        else{
            potrosnja += transaction.getAmount();
        }

        return (potrosnja <= getTotLimit());
    }

    @Override
    public boolean CheckMonthLimit(Transaction transaction){
        double potrosnja = 0;
        for(Transaction t : get()){
            if(t.getDate().getMonthValue() == transaction.getDate().getMonthValue()){
                if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                    potrosnja += t.getAmount();
                }
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.getMonthValue() == t.getDate().getMonthValue() && novi.compareTo(t.getEndDate()) <= 0){
                        potrosnja += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                }
            }
        }

        if(transaction.getType() == transactionType.REGULARPAYMENT){
            long numOfDays = DAYS.between(transaction.getDate(), transaction.getEndDate());
            long number = numOfDays/transaction.getTransactionInterval();
            LocalDate novi = transaction.getDate().plusDays(transaction.getTransactionInterval());
            while(novi.getMonthValue() == transaction.getDate().getMonthValue() && novi.compareTo(transaction.getEndDate()) <= 0){
                potrosnja += transaction.getAmount();
                novi = novi.plusDays(transaction.getTransactionInterval());
            }
        }
        else{
            potrosnja += transaction.getAmount();
        }

        return (potrosnja <= getMjLimit());
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
                if(!(trDatum.getYear() < t.getDate().getYear() || (trDatum.getYear() == t.getDate().getYear()
                        && trDatum.getMonthValue() < t.getDate().getMonthValue()))
                    &&  !(trDatum.getYear() > t.getEndDate().getYear() || (trDatum.getYear() == t.getEndDate().getYear()
                        && trDatum.getMonthValue() > t.getEndDate().getMonthValue())) ){

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
            if(type.equals("All types") || type.equals("Filter by")){
                odgovarajuce.add(t);
            }
        }

        if(sortType.equals("Sort by")){
            return odgovarajuce;
        }

        if(sortType.equals("Price - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingDouble(Transaction::getAmount)).collect(Collectors.toList());
        }

        if(sortType.equals("Price - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()).collect(Collectors.toList());
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

package ba.unsa.etf.rma.rma20djokicmilica36;


import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionListFragment extends Fragment implements ITransactionListView {


    private TextView glAmount;
    private TextView lim;
    private Spinner filter;
    private TextView month;
    private Button leftArrow;
    private Button rightArrow;
    private Spinner sort;
    private ListView listView;
    private Button dodaj;



    private TransactionListAdapter transactionListAdapter;

    private FilterAdapter filterAdapter;

    private ArrayAdapter<String> sortAdapter;


    private TransactionsModel model = new TransactionsModel();

    private ITransactionListPresenter transactionListPresenter;


    public ITransactionListPresenter getPresenter() {
        if (transactionListPresenter == null) {
            transactionListPresenter = new TransactionListPresenter(this, getActivity());
        }
        return transactionListPresenter;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onItemClicked(Transaction transaction);
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Ovdje se dodjeljuje layout fragmentu,
        // tj. ˇsta ´ce se nalaziti unutar fragmenta
        // Ovu liniju ´cemo poslije promijeniti

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        onItemClick= (OnItemClick) getActivity();



        transactionListAdapter = new TransactionListAdapter(getActivity(), R.layout.list_element,new ArrayList<Transaction>());

        filterAdapter = new FilterAdapter(getActivity(), R.layout.filter_element, getPresenter().getFiltriranje());

        sortAdapter = new SortAdapter(getActivity(), R.layout.sort_element, getPresenter().getSortiranje());



        LocalDate trenutniDatum = LocalDate.now();

        int mjesec = trenutniDatum.getMonthValue();
        int godina = trenutniDatum.getYear();

        month = fragmentView.findViewById(R.id.month);
        month.setText(Months.values()[mjesec - 1] + "," + Integer.toString(godina));

        leftArrow = fragmentView.findViewById(R.id.leftArrow);
        rightArrow = fragmentView.findViewById(R.id.rightArrow);

        listView= fragmentView.findViewById(R.id.listView);
        listView.setAdapter(transactionListAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        getPresenter().refreshTransactionsByDate();

        filter = fragmentView.findViewById(R.id.filter);
        filter.setAdapter(filterAdapter);

        sort = fragmentView.findViewById(R.id.sort);
        sort.setAdapter(sortAdapter);

        dodaj = fragmentView.findViewById(R.id.dodaj);

        glAmount = fragmentView.findViewById(R.id.glAmount);
        lim = fragmentView.findViewById(R.id.lim);
        double global = 0.0;




        for(Transaction t : getPresenter().getInteractor().get()){
            if (t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.REGULARINCOME){
                if(t.getType() == transactionType.REGULARINCOME){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    global += t.getAmount()*number;
                }
                else{
                    global += t.getAmount();
                }
            }
            else{
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global -= t.getAmount()*number;
                }
                else{
                    global -= t.getAmount();
                }
            }
        }

        glAmount.setText("Global amount: " + round(global, 2));

        lim.setText("Limit: " + getPresenter().getInteractor().getTotLimit());

        leftArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getPresenter().decreaseTransactionsMonth();
                getPresenter().refreshTransactionsByDate();
                getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getPresenter().increaseTransactionsMonth();
                getPresenter().refreshTransactionsByDate();
                getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            }
        });


        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String selectedType = sort.getSelectedItem().toString();
                getPresenter().refreshTransactionsByTypeSorted(selectedItem, selectedType);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String selectedItem = parent.getItemAtPosition(position).toString();
                String selectedFilter = filter.getSelectedItem().toString();
                getPresenter().refreshTransactionsByTypeSorted(selectedFilter, selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent transactionDetailIntent = new Intent(getActivity(), TransactionDetailActivity.class);
                Transaction transaction = new Transaction(LocalDate.now(), 0D, "", transactionType.INDIVIDUALPAYMENT, "",
                                                          0, LocalDate.now());

                onItemClick.onItemClicked(transaction);
                /*transactionDetailIntent.putExtra("date", LocalDate.now());
                transactionDetailIntent.putExtra("amount", 0D);
                transactionDetailIntent.putExtra("title", "");
                transactionDetailIntent.putExtra("type", transactionType.INDIVIDUALPAYMENT);
                transactionDetailIntent.putExtra("desc", "");
                transactionDetailIntent.putExtra("interval", 0);
                transactionDetailIntent.putExtra("endDate", LocalDate.now());
                //MainActivity.this.startActivity(transactionDetailIntent);
                getActivity().startActivity(transactionDetailIntent);*/
            }
        });



        return fragmentView;
    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Intent transactionDetailIntent = new Intent(getActivity(), TransactionDetailActivity.class);
            Transaction transaction = transactionListAdapter.getTransaction(position);
            onItemClick.onItemClicked(transaction);
            /*transactionDetailIntent.putExtra("date", transaction.getDate());
            transactionDetailIntent.putExtra("amount", transaction.getAmount());
            transactionDetailIntent.putExtra("title", transaction.getTitle());
            transactionDetailIntent.putExtra("type", transaction.getType());
            transactionDetailIntent.putExtra("desc", transaction.getItemDescription());
            transactionDetailIntent.putExtra("interval", transaction.getTransactionInterval());
            transactionDetailIntent.putExtra("endDate", transaction.getEndDate());
            getActivity().startActivity(transactionDetailIntent);*/
        }
    };

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        transactionListAdapter.setTransactions(transactions);
    }

    @Override
    public void setTransactionsByType(ArrayList<Transaction> transactions){
        transactionListAdapter.setTransactionsByType(transactions);
    }

    @Override
    public void setTransactionsSorted(ArrayList<Transaction> transactions) {
        transactionListAdapter.setTransactionsSorted(transactions);
    }

    @Override
    public void notifyTransactionListDataSetChanged() {
        transactionListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDate(String dat){
        month.setText(dat);

    }

    @Override
    public void onResume(){
        super.onResume();
        transactionListAdapter.setTransactions(getPresenter().getInteractor().get());
        getPresenter().refreshTransactionsByDate();
        getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
        double global = 0;
        for(Transaction t : getPresenter().getInteractor().get()){
            if (t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.REGULARINCOME){
                if(t.getType() == transactionType.REGULARINCOME){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global += t.getAmount()*number;
                }
                else{
                    global += t.getAmount();
                }
            }
            else{
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global -= t.getAmount()*number;
                }
                else{
                    global -= t.getAmount();
                }
            }
        }

        glAmount.setText("Global amount: " + round(global, 2));
    }
}

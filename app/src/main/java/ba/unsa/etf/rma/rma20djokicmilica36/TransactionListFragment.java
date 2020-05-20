package ba.unsa.etf.rma.rma20djokicmilica36;


import android.graphics.Color;
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
    private Button account;
    private Button buttonMGraphs;
    //private Button graphs;

    int prePos = -1;
    int prePos2 = -1;



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
    private OnItemClick onRightClick;
    private OnItemClick onLeftClick;

    public interface OnItemClick {
        void onItemClicked(Transaction transaction);
        void onRightClicked(Account account);
        void onLeftClicked();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        onItemClick= (OnItemClick) getActivity();
        onRightClick = (OnItemClick) getActivity();
        onLeftClick = (OnItemClick) getActivity();


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
        getPresenter().getTransactions("GET");
        //getPresenter().refreshTransactionsByDate();
        String mj = "";
        if(LocalDate.now().getMonthValue() <= 9){
            mj = "0" + Integer.toString(LocalDate.now().getMonthValue());
        }
        else{
            mj = Integer.toString(LocalDate.now().getMonthValue());
        }
        getPresenter().refreshByDateTypeSorted("", "", mj,
                Integer.toString(LocalDate.now().getYear()));

        filter = fragmentView.findViewById(R.id.filter);
        filter.setAdapter(filterAdapter);

        sort = fragmentView.findViewById(R.id.sort);
        sort.setAdapter(sortAdapter);

        dodaj = fragmentView.findViewById(R.id.dodaj);
        account = fragmentView.findViewById(R.id.account);
        buttonMGraphs = fragmentView.findViewById(R.id.buttonMGraphs);

        glAmount = fragmentView.findViewById(R.id.glAmount);
        lim = fragmentView.findViewById(R.id.lim);
        double global = 0.0;

        for(Transaction t : getPresenter().getInteractor().getTransact()){
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
                String typeId = "";
                if(filter.getSelectedItem().toString().equals("Regular payment")){
                    typeId = "1";
                }
                if(filter.getSelectedItem().toString().equals("Regular income")){
                    typeId = "2";
                }
                if(filter.getSelectedItem().toString().equals("Purchase")){
                    typeId = "3";
                }
                if(filter.getSelectedItem().toString().equals("Individual income")){
                    typeId = "4";
                }
                if(filter.getSelectedItem().toString().equals("Individual payment")){
                    typeId = "5";
                }
                if(filter.getSelectedItem().toString().equals("All types") || filter.getSelectedItem().toString().equals("Filter by")){
                    typeId = "";
                }

                String sortTip = "";

                if(sort.getSelectedItem().toString().equals("Price - Ascending")){
                    sortTip = "amount.asc";
                }
                if(sort.getSelectedItem().toString().equals("Price - Descending")){
                    sortTip = "amount.desc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Ascending")){
                    sortTip = "title.asc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Descending")){
                    sortTip = "title.desc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Ascending")){
                    sortTip = "date.asc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Descending")){
                    sortTip = "date.desc";
                }
                if(sort.getSelectedItem().toString().equals("Sort by")){
                    sortTip = "";
                }

                String d = month.getText().toString();
                int pozicija = d.indexOf(',');
                String mjesec = d.substring(0, pozicija);
                String godina = d.substring(pozicija+1, d.length());

                if(mjesec.equals("January")) mjesec = "01";
                if(mjesec.equals("February")) mjesec = "02";
                if(mjesec.equals("March")) mjesec = "03";
                if(mjesec.equals("April")) mjesec = "04";
                if(mjesec.equals("May")) mjesec = "05";
                if(mjesec.equals("June")) mjesec = "06";
                if(mjesec.equals("July")) mjesec = "07";
                if(mjesec.equals("August")) mjesec = "08";
                if(mjesec.equals("September")) mjesec = "09";
                if(mjesec.equals("October")) mjesec = "10";
                if(mjesec.equals("November")) mjesec = "11";
                if(mjesec.equals("December")) mjesec = "12";

                getPresenter().refreshByDateTypeSorted(typeId, sortTip, mjesec, godina);
                //getPresenter().refreshTransactionsByDate();
                //getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getPresenter().increaseTransactionsMonth();

                String typeId = "";
                if(filter.getSelectedItem().toString().equals("Regular payment")){
                    typeId = "1";
                }
                if(filter.getSelectedItem().toString().equals("Regular income")){
                    typeId = "2";
                }
                if(filter.getSelectedItem().toString().equals("Purchase")){
                    typeId = "3";
                }
                if(filter.getSelectedItem().toString().equals("Individual income")){
                    typeId = "4";
                }
                if(filter.getSelectedItem().toString().equals("Individual payment")){
                    typeId = "5";
                }
                if(filter.getSelectedItem().toString().equals("All types") || filter.getSelectedItem().toString().equals("Filter by")){
                    typeId = "";
                }

                String sortTip = "";

                if(sort.getSelectedItem().toString().equals("Price - Ascending")){
                    sortTip = "amount.asc";
                }
                if(sort.getSelectedItem().toString().equals("Price - Descending")){
                    sortTip = "amount.desc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Ascending")){
                    sortTip = "title.asc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Descending")){
                    sortTip = "title.desc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Ascending")){
                    sortTip = "date.asc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Descending")){
                    sortTip = "date.desc";
                }
                if(sort.getSelectedItem().toString().equals("Sort by")){
                    sortTip = "";
                }

                String d = month.getText().toString();
                int pozicija = d.indexOf(',');
                String mjesec = d.substring(0, pozicija);
                String godina = d.substring(pozicija+1, d.length());

                if(mjesec.equals("January")) mjesec = "01";
                if(mjesec.equals("February")) mjesec = "02";
                if(mjesec.equals("March")) mjesec = "03";
                if(mjesec.equals("April")) mjesec = "04";
                if(mjesec.equals("May")) mjesec = "05";
                if(mjesec.equals("June")) mjesec = "06";
                if(mjesec.equals("July")) mjesec = "07";
                if(mjesec.equals("August")) mjesec = "08";
                if(mjesec.equals("September")) mjesec = "09";
                if(mjesec.equals("October")) mjesec = "10";
                if(mjesec.equals("November")) mjesec = "11";
                if(mjesec.equals("December")) mjesec = "12";

                getPresenter().refreshByDateTypeSorted(typeId, sortTip, mjesec, godina);
                //getPresenter().refreshTransactionsByDate();
                //getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            }
        });


        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String typeId = "";
                if(selectedItem.equals("Regular payment")){
                    typeId = "1";
                }
                if(selectedItem.equals("Regular income")){
                    typeId = "2";
                }
                if(selectedItem.equals("Purchase")){
                    typeId = "3";
                }
                if(selectedItem.equals("Individual income")){
                    typeId = "4";
                }
                if(selectedItem.equals("Individual payment")){
                    typeId = "5";
                }
                if(selectedItem.equals("All types") || selectedItem.equals("Filter by")){
                    typeId = "";
                }

                String sortTip = "";

                if(sort.getSelectedItem().toString().equals("Price - Ascending")){
                    sortTip = "amount.asc";
                }
                if(sort.getSelectedItem().toString().equals("Price - Descending")){
                    sortTip = "amount.desc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Ascending")){
                    sortTip = "title.asc";
                }
                if(sort.getSelectedItem().toString().equals("Title - Descending")){
                    sortTip = "title.desc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Ascending")){
                    sortTip = "date.asc";
                }
                if(sort.getSelectedItem().toString().equals("Date - Descending")){
                    sortTip = "date.desc";
                }
                if(sort.getSelectedItem().toString().equals("Sort by")){
                    sortTip = "";
                }

                String d = month.getText().toString();
                int pozicija = d.indexOf(',');
                String mjesec = d.substring(0, pozicija);
                String godina = d.substring(pozicija+1, d.length());

                if(mjesec.equals("January")) mjesec = "01";
                if(mjesec.equals("February")) mjesec = "02";
                if(mjesec.equals("March")) mjesec = "03";
                if(mjesec.equals("April")) mjesec = "04";
                if(mjesec.equals("May")) mjesec = "05";
                if(mjesec.equals("June")) mjesec = "06";
                if(mjesec.equals("July")) mjesec = "07";
                if(mjesec.equals("August")) mjesec = "08";
                if(mjesec.equals("September")) mjesec = "09";
                if(mjesec.equals("October")) mjesec = "10";
                if(mjesec.equals("November")) mjesec = "11";
                if(mjesec.equals("December")) mjesec = "12";
                //String selectedType = sort.getSelectedItem().toString();
                //getPresenter().refreshTransactionsByTypeSorted(selectedItem, selectedType);
                getPresenter().refreshByDateTypeSorted(typeId, sortTip, mjesec, godina);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String selectedItem = parent.getItemAtPosition(position).toString();

                String typeId = "";
                if(filter.getSelectedItem().toString().equals("Regular payment")){
                    typeId = "1";
                }
                if(filter.getSelectedItem().toString().equals("Regular income")){
                    typeId = "2";
                }
                if(filter.getSelectedItem().toString().equals("Purchase")){
                    typeId = "3";
                }
                if(filter.getSelectedItem().toString().equals("Individual income")){
                    typeId = "4";
                }
                if(filter.getSelectedItem().toString().equals("Individual payment")){
                    typeId = "5";
                }
                if(filter.getSelectedItem().toString().equals("All types") || filter.getSelectedItem().toString().equals("Filter by")){
                    typeId = "";
                }

                String sortTip = "";

                if(selectedItem.equals("Price - Ascending")){
                    sortTip = "amount.asc";
                }
                if(selectedItem.equals("Price - Descending")){
                    sortTip = "amount.desc";
                }
                if(selectedItem.equals("Title - Ascending")){
                    sortTip = "title.asc";
                }
                if(selectedItem.equals("Title - Descending")){
                    sortTip = "title.desc";
                }
                if(selectedItem.equals("Date - Ascending")){
                    sortTip = "date.asc";
                }
                if(selectedItem.equals("Date - Descending")){
                    sortTip = "date.desc";
                }
                if(selectedItem.equals("Sort by")){
                    sortTip = "";
                }

                String d = month.getText().toString();
                int pozicija = d.indexOf(',');
                String mjesec = d.substring(0, pozicija);
                String godina = d.substring(pozicija+1, d.length());

                if(mjesec.equals("January")) mjesec = "01";
                if(mjesec.equals("February")) mjesec = "02";
                if(mjesec.equals("March")) mjesec = "03";
                if(mjesec.equals("April")) mjesec = "04";
                if(mjesec.equals("May")) mjesec = "05";
                if(mjesec.equals("June")) mjesec = "06";
                if(mjesec.equals("July")) mjesec = "07";
                if(mjesec.equals("August")) mjesec = "08";
                if(mjesec.equals("September")) mjesec = "09";
                if(mjesec.equals("October")) mjesec = "10";
                if(mjesec.equals("November")) mjesec = "11";
                if(mjesec.equals("December")) mjesec = "12";
                //String selectedType = sort.getSelectedItem().toString();
                //getPresenter().refreshTransactionsByTypeSorted(selectedItem, selectedType);
                getPresenter().refreshByDateTypeSorted(typeId, sortTip, mjesec, godina);
                //String selectedFilter = filter.getSelectedItem().toString();
                //getPresenter().refreshTransactionsByTypeSorted(selectedFilter, selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction(LocalDate.now(), 0D, "", transactionType.INDIVIDUALPAYMENT, "",
                        0, LocalDate.now());

                onItemClick.onItemClicked(transaction);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Account account = getPresenter().getInteractor().getBModel().racun;
                onRightClick.onRightClicked(account);
            }
        });

        buttonMGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClick.onLeftClicked();
            }
        });


        return fragmentView;
    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Transaction transaction = transactionListAdapter.getTransaction(position);
            Transaction tr = new Transaction(LocalDate.now(), 0D, "", transactionType.INDIVIDUALPAYMENT, "",
                    0, LocalDate.now());

            for (int i = 0; i < parent.getChildCount(); i++) {

                if (i == position) {
                    if (position != prePos) {
                        parent.getChildAt(i).setBackgroundColor(Color.GRAY);
                        onItemClick.onItemClicked(transaction);
                        prePos = position;
                    } else {
                        parent.getChildAt(i).setBackgroundColor(0x76AAE1);
                        prePos = -1;
                        onItemClick.onItemClicked(tr);
                    }

                }
            }
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
        //transactionListAdapter.setTransactions(getPresenter().getInteractor().getTransact());
        //getPresenter().getTransactions("GET");
        //getPresenter().refreshTransactionsByDate();
        //getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
        String typeId = "";
        if(filter.getSelectedItem().toString().equals("Regular payment")){
            typeId = "1";
        }
        if(filter.getSelectedItem().toString().equals("Regular income")){
            typeId = "2";
        }
        if(filter.getSelectedItem().toString().equals("Purchase")){
            typeId = "3";
        }
        if(filter.getSelectedItem().toString().equals("Individual income")){
            typeId = "4";
        }
        if(filter.getSelectedItem().toString().equals("Individual payment")){
            typeId = "5";
        }
        if(filter.getSelectedItem().toString().equals("All types") || filter.getSelectedItem().toString().equals("Filter by")){
            typeId = "";
        }

        String sortTip = "";

        if(sort.getSelectedItem().equals("Price - Ascending")){
            sortTip = "amount.asc";
        }
        if(sort.getSelectedItem().equals("Price - Descending")){
            sortTip = "amount.desc";
        }
        if(sort.getSelectedItem().equals("Title - Ascending")){
            sortTip = "title.asc";
        }
        if(sort.getSelectedItem().equals("Title - Descending")){
            sortTip = "title.desc";
        }
        if(sort.getSelectedItem().equals("Date - Ascending")){
            sortTip = "date.asc";
        }
        if(sort.getSelectedItem().equals("Date - Descending")){
            sortTip = "date.desc";
        }
        if(sort.getSelectedItem().equals("Sort by")){
            sortTip = "";
        }

        String d = month.getText().toString();
        int pozicija = d.indexOf(',');
        String mjesec = d.substring(0, pozicija);
        String godina = d.substring(pozicija+1, d.length());

        if(mjesec.equals("January")) mjesec = "01";
        if(mjesec.equals("February")) mjesec = "02";
        if(mjesec.equals("March")) mjesec = "03";
        if(mjesec.equals("April")) mjesec = "04";
        if(mjesec.equals("May")) mjesec = "05";
        if(mjesec.equals("June")) mjesec = "06";
        if(mjesec.equals("July")) mjesec = "07";
        if(mjesec.equals("August")) mjesec = "08";
        if(mjesec.equals("September")) mjesec = "09";
        if(mjesec.equals("October")) mjesec = "10";
        if(mjesec.equals("November")) mjesec = "11";
        if(mjesec.equals("December")) mjesec = "12";
        getPresenter().refreshByDateTypeSorted(typeId, sortTip, mjesec, godina);
        getPresenter().getInteractor().getBModel().racun.setBudget(getPresenter().RefreshAmount());
        getPresenter().getInteractor().getBModel().racun.setTotalLimit(getPresenter().RefreshLimit());
        glAmount.setText("Global amount: " + round(getPresenter().RefreshAmount(), 2));
        lim.setText("Limit" + getPresenter().RefreshLimit());
    }
}

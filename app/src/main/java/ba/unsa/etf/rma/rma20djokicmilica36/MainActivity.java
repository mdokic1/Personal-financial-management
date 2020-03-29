package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ITransactionListView{

    private TextView glAmount;
    private TextView lim;
    private Spinner filter;
    private TextView month;
    private Button leftArrow;
    private Button rightArrow;
    private Spinner sort;
    private ListView listView;
    private Button add;



    private TransactionListAdapter transactionListAdapter;

    private FilterAdapter filterAdapter;

    private ArrayAdapter<String> sortAdapter;


    private TransactionsModel model = new TransactionsModel();

    private ITransactionListPresenter transactionListPresenter;


    public ITransactionListPresenter getPresenter() {
        if (transactionListPresenter == null) {
            transactionListPresenter = new TransactionListPresenter(this, this);
        }
        return transactionListPresenter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        transactionListAdapter = new TransactionListAdapter(this, R.layout.list_element,new ArrayList<Transaction>());

        filterAdapter = new FilterAdapter(this, R.layout.filter_element, getPresenter().getFiltriranje());

        sortAdapter = new SortAdapter(this, R.layout.sort_element, getPresenter().getSortiranje());



        LocalDate trenutniDatum = LocalDate.now();

        int mjesec = trenutniDatum.getMonthValue();
        int godina = trenutniDatum.getYear();

        month = (TextView) findViewById(R.id.month);
        month.setText(Months.values()[mjesec - 1] + "," + Integer.toString(godina));

        leftArrow = (Button) findViewById(R.id.leftArrow);
        rightArrow = (Button) findViewById(R.id.rightArrow);

        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(transactionListAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        getPresenter().refreshTransactionsByDate();

        filter = (Spinner) findViewById(R.id.filter);
        filter.setAdapter(filterAdapter);

        sort = (Spinner) findViewById(R.id.sort);
        sort.setAdapter(sortAdapter);

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

    }

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

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent transactionDetailIntent = new Intent(MainActivity.this, TransactionDetailActivity.class);
            Transaction transaction = transactionListAdapter.getTransaction(position);
            transactionDetailIntent.putExtra("date", transaction.getDate());
            transactionDetailIntent.putExtra("amount", transaction.getAmount());
            transactionDetailIntent.putExtra("title", transaction.getTitle());
            transactionDetailIntent.putExtra("type", transaction.getType());
            transactionDetailIntent.putExtra("desc", transaction.getItemDescription());
            transactionDetailIntent.putExtra("interval", transaction.getTransactionInterval());
            transactionDetailIntent.putExtra("endDate", transaction.getEndDate());
            MainActivity.this.startActivity(transactionDetailIntent);
        }
    };
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(transactionListAdapter);
        getPresenter().refreshTransactions();


        LocalDate trenutniDatum = LocalDate.now();
        int mjesec = trenutniDatum.getMonthValue();
        int godina = trenutniDatum.getYear();

        month = (TextView) findViewById(R.id.month);
        month.setText(Integer.toString(mjesec) + "," + Integer.toString(godina));

    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        transactionListAdapter.setTransactions(transactions);
    }

    @Override
    public void notifyTransactionListDataSetChanged() {
        transactionListAdapter.notifyDataSetChanged();
    }
}

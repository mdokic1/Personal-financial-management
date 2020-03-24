package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    private ArrayList<Transaction> transakcije;

    private TransactionsModel model = new TransactionsModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        transakcije = new ArrayList<>();
        model.load();
        transakcije.addAll(model.getData());

        transactionListAdapter = new TransactionListAdapter(this, R.layout.list_element,transakcije);
        transactionListAdapter.notifyDataSetChanged();
        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(transactionListAdapter);

    }
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    private LocalDate trDatum;

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
        trDatum = trenutniDatum;
        int mjesec = trenutniDatum.getMonthValue();
        int godina = trenutniDatum.getYear();

        month = (TextView) findViewById(R.id.month);
        month.setText(Months.values()[mjesec - 1] + "," + Integer.toString(godina));

        leftArrow = (Button) findViewById(R.id.leftArrow);
        rightArrow = (Button) findViewById(R.id.rightArrow);



        leftArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                month.setText(Months.values()[mj - 1] + "," + Integer.toString(god));
                transactionListAdapter.notifyDataSetChanged();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                month.setText(Months.values()[mj - 1] + "," + Integer.toString(god));
                transactionListAdapter.notifyDataSetChanged();
            }
        });

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

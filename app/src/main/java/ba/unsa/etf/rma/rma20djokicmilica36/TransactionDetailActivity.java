package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class TransactionDetailActivity extends AppCompatActivity {
    private ImageView icon;
    private Spinner type;
    private EditText amount;
    private EditText title;
    private EditText date;
    private EditText endDate;
    private EditText interval;
    private EditText desc;
    private Button save;
    private Button delete;

    private ITransactionDetailPresenter presenter;

    private DetailSpinnerAdapter detailSpinnerAdapter;

    public ITransactionDetailPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TransactionDetailPresenter(this);
        }
        return presenter;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_activity);

        getPresenter().create((LocalDate)getIntent().getSerializableExtra("date"),
                              (int)getIntent().getSerializableExtra("amount"),
                               getIntent().getStringExtra("title"),
                              (transactionType)getIntent().getSerializableExtra("type"),
                               getIntent().getStringExtra("desc"),
                              (Integer)getIntent().getSerializableExtra("interval"),
                              (LocalDate)getIntent().getSerializableExtra("endDate"));

        icon = findViewById(R.id.icon);
        type = findViewById(R.id.type);
        amount = (EditText)findViewById(R.id.amount);
        title = (EditText)findViewById(R.id.title);
        date = (EditText)findViewById(R.id.date);
        endDate = (EditText)findViewById(R.id.endDate);
        interval = (EditText)findViewById(R.id.interval);
        desc = (EditText)findViewById(R.id.desc);

        Transaction transaction = getPresenter().getTransaction();
        amount.setText(String.valueOf(transaction.getAmount()));
        title.setText(transaction.getTitle());
        date.setText(transaction.getDate().toString());
        endDate.setText(transaction.getEndDate().toString());
        interval.setText(String.valueOf(transaction.getTransactionInterval()));
        desc.setText(transaction.getItemDescription());

        icon.setImageResource(R.drawable.all_types);

        if(type.equals(transactionType.INDIVIDUALPAYMENT)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa1());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(type.equals(transactionType.REGULARPAYMENT)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa2());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(type.equals(transactionType.PURCHASE)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa3());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(type.equals(transactionType.INDIVIDUALINCOME)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa4());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(type.equals(transactionType.REGULARINCOME)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa5());
            type.setAdapter(detailSpinnerAdapter);
        }

    }

}

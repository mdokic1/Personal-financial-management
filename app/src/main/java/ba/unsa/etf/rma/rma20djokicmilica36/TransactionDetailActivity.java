package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    int amount_before;
    String title_before;
    LocalDate date_before;
    LocalDate endDate_before;
    Integer interval_before;
    String desc_before;
    transactionType type_before;

    Transaction stara;

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
        save = (Button)findViewById(R.id.save);
        delete = (Button)findViewById(R.id.delete);

        Transaction transaction = getPresenter().getTransaction();
        amount.setText(String.valueOf(transaction.getAmount()));
        title.setText(transaction.getTitle());
        date.setText(transaction.getDate().toString());
        if(transaction.getEndDate() == null){
            endDate.setText("");
        }
        else{
            endDate.setText(transaction.getEndDate().toString());
        }

        if(transaction.getTransactionInterval() == null){
            interval.setText("");
        }
        else{
            interval.setText(String.valueOf(transaction.getTransactionInterval()));
        }

        if(transaction.getItemDescription() == null){
            desc.setText("");
        }
        else{
            desc.setText(transaction.getItemDescription());
        }

        transactionType tip = transaction.getType();

        amount_before = Integer.parseInt(amount.getText().toString());
        title_before = title.getText().toString();
        date_before = LocalDate.parse(date.getText().toString());
        if(endDate.getText().toString().equals("")){
            endDate_before = null;
        }
        else{
            endDate_before = LocalDate.parse(date.getText().toString());
        }

        if(interval.getText().toString().equals("")){
            interval_before = null;
        }
        else{
            interval_before = Integer.parseInt(interval.getText().toString());
        }

        if(desc.getText().toString().equals("")){
            desc_before = null;
        }
        else{
            desc_before = desc.getText().toString();
        }


        icon.setImageResource(R.drawable.all_types);

        if(tip.equals(transactionType.INDIVIDUALPAYMENT)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa1());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(tip.equals(transactionType.REGULARPAYMENT)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa2());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(tip.equals(transactionType.PURCHASE)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa3());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(tip.equals(transactionType.INDIVIDUALINCOME)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa4());
            type.setAdapter(detailSpinnerAdapter);
        }

        if(tip.equals(transactionType.REGULARINCOME)){
            detailSpinnerAdapter = new DetailSpinnerAdapter(this, R.layout.detail_spinner_element, getPresenter().getIzborTipa5());
            type.setAdapter(detailSpinnerAdapter);
        }

        //type_before = transactionType.valueOf(type.getSelectedItem().toString().toUpperCase());
        type_before = tip;

        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                amount.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        title.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                title.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if(title.getText().length() <= 3 || title.getText().length() >= 15){
                    title.setBackgroundColor(Color.RED);
                }
            }
        });

        date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                date.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        endDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                endDate.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if((type.getSelectedItem().toString().equals("Individual income") || type.getSelectedItem().toString().equals("Individual payment") ||
                        type.getSelectedItem().toString().equals("Purchase")) && !endDate.getText().toString().equals("")){
                    endDate.setBackgroundColor(Color.RED);
                }

                if((type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().toString().equals("Regular payment"))  &&
                        endDate.getText().toString().equals("")){
                    endDate.setBackgroundColor(Color.RED);
                }
            }
        });

        interval.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                interval.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if((type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().toString().equals("Regular payment"))
                        && interval.getText().toString().equals("")){
                   interval.setBackgroundColor(Color.RED);
                }

                if((type.getSelectedItem().toString().equals("Individual income") || type.getSelectedItem().toString().equals("Individual payment") ||
                        type.getSelectedItem().toString().equals("Purchase")) && !interval.getText().toString().equals("")){
                    interval.setBackgroundColor(Color.RED);
                }
            }
        });

        desc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                desc.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if ((type.getSelectedItem().toString().equals("REGULARINCOME") || type.getSelectedItem().equals("INDIVIDUALINCOME")) &&
                        !desc.getText().toString().equals("")) {
                    desc.setBackgroundColor(Color.RED);
                }
            }
        });

       /* if ((type.getSelectedItem().equals("Regular income") || type.getSelectedItem().equals("Individual income")) && desc.getText().toString() != "") {
            type.setBackgroundColor(Color.RED);
        }
        if((type.getSelectedItem().equals("Regular income") || type.getSelectedItem().equals("Regular payment")) && interval.getText().toString() == ""){
            type.setBackgroundColor(Color.RED);
        }

        if((type.getSelectedItem().equals("Individual income") || type.getSelectedItem().equals("Individual payment") ||
                type.getSelectedItem().equals("Purchase")) && interval.getText().toString() != ""){
            type.setBackgroundColor(Color.RED);
        }

        if((type.getSelectedItem().equals("Regular income") || type.getSelectedItem().equals("Reegular payment")) && endDate.getText().toString() == ""){
           type.setBackgroundColor(Color.RED);
        }

        if((type.getSelectedItem().equals("Individual income") || type.getSelectedItem().equals("Individual payment") ||
                type.getSelectedItem().equals("Purchase")) && endDate.getText().toString() != ""){
             type.setBackgroundColor(Color.RED);
        }*/

        stara = new Transaction(date_before, amount_before, title_before, type_before, desc_before, interval_before, endDate_before);


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((ColorDrawable)date.getBackground()).getColor() == Color.GREEN && ((ColorDrawable)amount.getBackground()).getColor() == Color.GREEN &&
                    ((ColorDrawable)title.getBackground()).getColor() == Color.GREEN && ((ColorDrawable)type.getBackground()).getColor() == Color.GREEN &&
                    ((ColorDrawable)desc.getBackground()).getColor() == Color.GREEN && ((ColorDrawable)interval.getBackground()).getColor() == Color.GREEN &&
                    ((ColorDrawable)endDate.getBackground()).getColor() == Color.GREEN){
                    Transaction nova = new Transaction(LocalDate.parse(date.getText().toString()), Integer.parseInt(amount.getText().toString()),
                            title.getText().toString(), transactionType.valueOf(type.getSelectedItem().toString().toUpperCase()), desc.getText().toString(),
                            Integer.parseInt(interval.getText().toString()), LocalDate.parse(endDate.getText().toString()));
                    stara = nova;

                    getPresenter().getView().setTransactions(getPresenter().getInteractor().get());

                }
            }
        });
    }

}
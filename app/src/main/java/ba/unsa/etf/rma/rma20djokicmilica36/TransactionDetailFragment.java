package ba.unsa.etf.rma.rma20djokicmilica36;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionDetailFragment extends Fragment implements ITransactionDetailView {

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

    private Account racun;

    double amount_before;
    String title_before;
    LocalDate date_before;
    LocalDate endDate_before;
    Integer interval_before;
    String desc_before;
    transactionType type_before;

    int indeks = -1;

    private ITransactionDetailPresenter presenter;

    private DetailSpinnerAdapter detailSpinnerAdapter;
    private TransactionListAdapter transactionListAdapter;

    private ArrayAdapter<Transaction> arrayAdapter;

    public ITransactionDetailPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TransactionDetailPresenter(this, getActivity());
        }
        return presenter;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private boolean checkIfDateIsValid(String date, SimpleDateFormat format) {

        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private OnButtonClick onButtonClick;

    public interface OnButtonClick {
       void Refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        onButtonClick = (OnButtonClick) getActivity();


        arrayAdapter = new ArrayAdapter<Transaction>(getActivity(), R.layout.transaction_detail_activity, getPresenter().getModel());

        if (getArguments() != null && getArguments().containsKey("transaction")) {
            getPresenter().setTransaction(getArguments().getParcelable("transaction"));

            icon = view.findViewById(R.id.icon);
            type = view.findViewById(R.id.type);
            amount = (EditText) view.findViewById(R.id.amount);
            title = (EditText) view.findViewById(R.id.title);
            date = (EditText) view.findViewById(R.id.date);
            endDate = (EditText) view.findViewById(R.id.endDate);
            interval = (EditText) view.findViewById(R.id.interval);
            desc = (EditText) view.findViewById(R.id.desc);
            save = (Button) view.findViewById(R.id.save);
            delete = (Button) view.findViewById(R.id.delete);

            Transaction transaction = getPresenter().getTransaction();
            Integer IDtransakcije = transaction.getTransaction_id();
            amount.setText(String.valueOf(transaction.getAmount()));
            title.setText(transaction.getTitle());
            date.setText(transaction.getDate().toString());
            if (transaction.getEndDate() == null) {
                endDate.setText("");
            } else {
                endDate.setText(transaction.getEndDate().toString());
            }

            if (transaction.getTransactionInterval() == null) {
                interval.setText("");
            } else {
                interval.setText(String.valueOf(transaction.getTransactionInterval()));
            }

            if (transaction.getItemDescription() == null) {
                desc.setText("");
            } else {
                desc.setText(transaction.getItemDescription());
            }

            transactionType tip = transaction.getType();

            amount_before = Double.parseDouble(amount.getText().toString());
            title_before = title.getText().toString();
            date_before = LocalDate.parse(date.getText().toString());
            if (endDate.getText().toString().equals("")) {
                endDate_before = null;
            } else {
                endDate_before = LocalDate.parse(endDate.getText().toString());
            }

            if (interval.getText().toString().equals("")) {
                interval_before = null;
            } else {
                interval_before = Integer.parseInt(interval.getText().toString());
            }

            if (desc.getText().toString().equals("")) {
                desc_before = null;
            } else {
                desc_before = desc.getText().toString();
            }


            icon.setImageResource(R.drawable.all_types);

            if (tip.equals(transactionType.INDIVIDUALPAYMENT)) {
                detailSpinnerAdapter = new DetailSpinnerAdapter(getActivity(), R.layout.detail_spinner_element, getPresenter().getIzborTipa1());
                type.setAdapter(detailSpinnerAdapter);
            }

            if (tip.equals(transactionType.REGULARPAYMENT)) {
                detailSpinnerAdapter = new DetailSpinnerAdapter(getActivity(), R.layout.detail_spinner_element, getPresenter().getIzborTipa2());
                type.setAdapter(detailSpinnerAdapter);
            }

            if (tip.equals(transactionType.PURCHASE)) {
                detailSpinnerAdapter = new DetailSpinnerAdapter(getActivity(), R.layout.detail_spinner_element, getPresenter().getIzborTipa3());
                type.setAdapter(detailSpinnerAdapter);
            }

            if (tip.equals(transactionType.INDIVIDUALINCOME)) {
                detailSpinnerAdapter = new DetailSpinnerAdapter(getActivity(), R.layout.detail_spinner_element, getPresenter().getIzborTipa4());
                type.setAdapter(detailSpinnerAdapter);
            }

            if (tip.equals(transactionType.REGULARINCOME)) {
                detailSpinnerAdapter = new DetailSpinnerAdapter(getActivity(), R.layout.detail_spinner_element, getPresenter().getIzborTipa5());
                type.setAdapter(detailSpinnerAdapter);
            }

            getPresenter().azurirajAccount();

            type_before = tip;

            ArrayList<Transaction> sve = getPresenter().getModel();
            getPresenter().create(date_before, amount_before, title_before, type_before, desc_before, interval_before, endDate_before);

            if (amount_before == 0) {
                delete.setEnabled(false);
            }

            for (Transaction t : sve) {
                if (t.equals(getPresenter().getTransaction())) {
                    indeks = sve.indexOf(t);
                }
            }

            type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    /*TextView tipSpinnera = view.findViewById(R.id.tipSpinnera);

                    tipSpinnera.setBackgroundColor(Color.GREEN);*/

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            amount.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    amount.setBackgroundColor(Color.GREEN);
                    //amount.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if (amount.getText().toString().equals("") || amount.getText().toString().equals("0") || amount.getText().toString().equals("0.0")) {
                        amount.setBackgroundColor(Color.RED);
                        amount.setTag("red");
                    } else {
                        amount.setTag("green");
                    }
                }
            });

            title.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    title.setBackgroundColor(Color.GREEN);
                    //title.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if (title.getText().length() <= 3 || title.getText().length() >= 15) {
                        title.setBackgroundColor(Color.RED);
                        title.setTag("red");
                    } else {
                        title.setTag("green");
                    }
                }
            });

            date.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    date.setBackgroundColor(Color.GREEN);
                    //date.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if (checkIfDateIsValid(date.getText().toString(), format)) {
                        if (!endDate.getText().toString().equals("")) {
                            try {
                                if (format.parse(endDate.getText().toString()).compareTo(format.parse(date.getText().toString())) < 0) {
                                    date.setBackgroundColor(Color.RED);
                                    date.setTag("red");
                                } else {
                                    date.setBackgroundColor(Color.GREEN);
                                    date.setTag("green");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (!checkIfDateIsValid(date.getText().toString(), format)) {
                        date.setBackgroundColor(Color.RED);
                        date.setTag("red");
                    } else {
                        date.setTag("green");
                    }

                }
            });

            endDate.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    endDate.setBackgroundColor(Color.GREEN);
                    //endDate.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }


                @Override
                public void afterTextChanged(Editable arg0) {
                    if ((type.getSelectedItem().toString().equals("Individual income") || type.getSelectedItem().toString().equals("Individual payment") ||
                            type.getSelectedItem().toString().equals("Purchase")) && !endDate.getText().toString().equals("")) {
                        endDate.setBackgroundColor(Color.RED);
                        endDate.setTag("red");
                    } else if ((type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().toString().equals("Regular payment")) &&
                            endDate.getText().toString().equals("")) {
                        endDate.setBackgroundColor(Color.RED);
                        endDate.setTag("red");
                    } else if (checkIfDateIsValid(endDate.getText().toString(), format)) {
                        if (!date.getText().toString().equals("")) {
                            try {
                                if (format.parse(endDate.getText().toString()).compareTo(format.parse(date.getText().toString())) < 0) {
                                    endDate.setBackgroundColor(Color.RED);
                                    endDate.setTag("red");
                                } else {
                                    endDate.setBackgroundColor(Color.GREEN);
                                    endDate.setTag("green");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (!checkIfDateIsValid(endDate.getText().toString(), format)) {
                        if (endDate.getText().toString().equals("")) {
                            if (type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().toString().equals("Regular payment")) {
                                endDate.setBackgroundColor(Color.RED);
                                endDate.setTag("red");
                            } else {
                                endDate.setTag("green");
                            }
                        } else {
                            endDate.setBackgroundColor(Color.RED);
                            endDate.setTag("red");
                        }

                    } else {
                        endDate.setTag("green");
                    }

                }
            });

            interval.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    interval.setBackgroundColor(Color.GREEN);
                    //interval.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if ((type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().toString().equals("Regular payment"))
                            && (interval.getText().toString().equals("") || interval.getText().toString().equals("0"))) {
                        interval.setBackgroundColor(Color.RED);
                        interval.setTag("red");
                    } else if ((type.getSelectedItem().toString().equals("Individual income") || type.getSelectedItem().toString().equals("Individual payment") ||
                            type.getSelectedItem().toString().equals("Purchase")) && !interval.getText().toString().equals("")) {
                        interval.setBackgroundColor(Color.RED);
                        interval.setTag("red");
                    } else if (interval.getText().toString().equals("0")) {
                        interval.setTag("red");
                    } else {
                        interval.setTag("green");
                    }
                }
            });

            desc.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    desc.setBackgroundColor(Color.GREEN);
                    //desc.setTag("changed");

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if ((type.getSelectedItem().toString().equals("Regular income") || type.getSelectedItem().equals("Individual income")) &&
                            !desc.getText().toString().equals("")) {
                        desc.setBackgroundColor(Color.RED);
                        desc.setTag("red");
                    } else {
                        desc.setTag("green");
                    }
                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                double budzet = 0.0;
                double stari_budget = 0.0;
                transactionType noviTip;
                Integer noviInterval;
                String noviInterval2 = null;
                String noviDesc = null;
                LocalDate noviEndDate;
                String noviEndDate2 = null;
                String izmjenaDate = null, izmjenaAmount = null, izmjenaTitle = null, izmjenaDesc = null, izmjenaInterval = null, izmjenaEndDate = null;

                @Override
                public void onClick(View v) {

                    if ((date.getTag() == null || date.getTag().equals("green")) && (amount.getTag() == null || amount.getTag().equals("green"))
                            && (title.getTag() == null || title.getTag().equals("green")) && (desc.getTag() == null || desc.getTag().equals("green"))
                            && (type.getTag() == null || type.getTag().equals("green"))
                            && (interval.getTag() == null || interval.getTag().equals("green")) &&
                            (endDate.getTag() == null || endDate.getTag().equals("green"))) {

                        date.setBackgroundColor(0xFF76AAE1);
                        amount.setBackgroundColor(0xFF76AAE1);
                        title.setBackgroundColor(0xFF76AAE1);
                        desc.setBackgroundColor(0xFF76AAE1);
                        date.setBackgroundColor(0xFF76AAE1);

                        TextView tipSpinnera = view.findViewById(R.id.tipSpinnera);
                        tipSpinnera.setBackgroundColor(0xFF76AAE1);

                        interval.setBackgroundColor(0xFF76AAE1);
                        endDate.setBackgroundColor(0xFF76AAE1);


                        if (type.getSelectedItem().toString().equals("Individual payment")) {
                            noviTip = transactionType.INDIVIDUALPAYMENT;
                        }

                        if (type.getSelectedItem().toString().equals("Regular payment")) {
                            noviTip = transactionType.REGULARPAYMENT;
                        }

                        if (type.getSelectedItem().toString().equals("Individual income")) {
                            noviTip = transactionType.INDIVIDUALINCOME;
                        }

                        if (type.getSelectedItem().toString().equals("Regular income")) {
                            noviTip = transactionType.REGULARINCOME;
                        }

                        if (type.getSelectedItem().toString().equals("Purchase")) {
                            noviTip = transactionType.PURCHASE;
                        }

                        if (interval.getText().toString().equals("")) {
                            noviInterval = null;
                            noviInterval2 = null;
                        } else {
                            noviInterval = Integer.parseInt(interval.getText().toString());
                            noviInterval2 = interval.getText().toString();
                        }

                        if (desc.getText().toString().equals("")) {
                            noviDesc = null;
                        } else {
                            noviDesc = desc.getText().toString();
                        }

                        if (endDate.getText().toString().equals("")) {
                            noviEndDate = null;
                            noviEndDate2 = null;
                        } else {
                            noviEndDate = LocalDate.parse(endDate.getText().toString());
                            noviEndDate2 = endDate.getText().toString();
                        }


                        Transaction nova = new Transaction(LocalDate.parse(date.getText().toString()), Double.parseDouble(amount.getText().toString()),
                                title.getText().toString(), noviTip, noviDesc, noviInterval, noviEndDate);

                        if(getArguments() != null && getArguments().containsKey("akcija")) {


                            if (getArguments().getString("akcija").equals("dodaj")) {

                                if (nova.getType() == transactionType.REGULARPAYMENT || nova.getType() == transactionType.INDIVIDUALPAYMENT ||
                                        nova.getType() == transactionType.PURCHASE) {
                                    if (!getPresenter().getInteractor().CheckTotalLimit(nova) || !getPresenter().getInteractor().CheckMonthLimit(nova)) {
                                        AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                                                .setTitle("Confirm")
                                                .setMessage("Are you sure you want to add this transaction?")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        getPresenter().refreshTransactionsAdd(date.getText().toString(), amount.getText().toString(), title.getText().toString(),
                                                                type.getSelectedItem().toString(), noviDesc, noviInterval2, noviEndDate2);

                                                        if(nova.getType() == transactionType.REGULARPAYMENT){
                                                            long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                                            long number = numOfDays/noviInterval;
                                                            budzet -= nova.getAmount()*number;
                                                        }
                                                        else{
                                                            budzet -= nova.getAmount();
                                                        }

                                                        onButtonClick.Refresh();
                                                        getFragmentManager().popBackStack();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        getFragmentManager().popBackStack();
                                                    }
                                                })
                                                .show();
                                    } else {
                                        getPresenter().refreshTransactionsAdd(date.getText().toString(), amount.getText().toString(), title.getText().toString(),
                                                type.getSelectedItem().toString(), noviDesc, noviInterval2, noviEndDate2);

                                        if(nova.getType() == transactionType.REGULARPAYMENT){
                                            long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                            long number = numOfDays/noviInterval;
                                            budzet -= nova.getAmount()*number;
                                        }
                                        else{
                                            budzet -= nova.getAmount();
                                        }

                                        onButtonClick.Refresh();
                                        getFragmentManager().popBackStack();
                                    }
                                } else {
                                    getPresenter().refreshTransactionsAdd(date.getText().toString(), amount.getText().toString(), title.getText().toString(),
                                            type.getSelectedItem().toString(), noviDesc, noviInterval2, noviEndDate2);

                                    if(nova.getType() == transactionType.REGULARINCOME){
                                        long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                        long number = numOfDays/noviInterval;
                                        budzet += nova.getAmount()*number;
                                    }
                                    else{
                                        budzet += nova.getAmount();
                                    }

                                    onButtonClick.Refresh();
                                    getFragmentManager().popBackStack();
                                }

                                budzet = racun.getBudget() + budzet;
                                //racun.setBudget(budzet);
                                getPresenter().azurirajBudzet(String.valueOf(budzet), null, null);
                                onButtonClick.Refresh();
                            } else {

                                budzet = 0.0;
                                stari_budget = 0.0;

                                if(type_before == transactionType.REGULARPAYMENT){
                                    long numOfDays = DAYS.between(date_before, endDate_before);
                                    long number = numOfDays/interval_before;
                                    stari_budget -= amount_before*number;
                                }
                                else if (type_before == transactionType.INDIVIDUALPAYMENT || type_before == transactionType.PURCHASE){
                                    stari_budget -= amount_before;
                                }
                                else if(type_before == transactionType.REGULARINCOME){
                                    long numOfDays = DAYS.between(date_before, endDate_before);
                                    long number = numOfDays/interval_before;
                                    stari_budget += amount_before*number;
                                }
                                else if(type_before == transactionType.INDIVIDUALINCOME){
                                    stari_budget += amount_before;
                                }

                                if(LocalDate.parse(date.getText().toString()) != date_before){
                                    izmjenaDate = date.getText().toString();
                                }
                                else{
                                    izmjenaDate = null;
                                }

                                if(Double.parseDouble(amount.getText().toString()) != amount_before){
                                    izmjenaAmount = amount.getText().toString();
                                }
                                else{
                                    izmjenaAmount = null;
                                }

                                if(title.getText().toString() != title_before){
                                    izmjenaTitle = title.getText().toString();
                                }
                                else{
                                    izmjenaTitle = null;
                                }

                                if(desc.getText().toString() != "" && desc.getText().toString() != "null" && desc.getText().toString() != null){
                                    izmjenaDesc = desc.getText().toString();
                                }
                                else{
                                    izmjenaDesc = null;
                                }

                                try{
                                    if(interval.getText().toString() != ""  && interval.getText().toString() != "null" && interval.getText().toString() != "0"
                                            && interval.getText().toString() != null){
                                        //if(Integer.parseInt(interval.getText().toString()) != interval_before){
                                            izmjenaInterval = interval.getText().toString();
                                        //}
                                    }
                                } catch (Exception e) {
                                    izmjenaInterval = null;
                                }

                                try{
                                    if(endDate.getText().toString() != "" && endDate.getText().toString() != "null" && endDate.getText().toString() != null) {
                                        //LocalDate end = LocalDate.parse(endDate.getText().toString());
                                        //if(end != endDate_before) {
                                            izmjenaEndDate = endDate.getText().toString();
                                        //}
                                    }
                                } catch (Exception e) {
                                    izmjenaEndDate = null;
                                }

                                if(izmjenaDesc.equals("")){
                                    izmjenaDesc = null;
                                }

                                if(izmjenaEndDate.equals("")){
                                    izmjenaEndDate = null;
                                }

                                if(izmjenaInterval.equals("")){
                                    izmjenaInterval = null;
                                }

                                if (nova.getType() == transactionType.REGULARPAYMENT || nova.getType() == transactionType.INDIVIDUALPAYMENT ||
                                        nova.getType() == transactionType.PURCHASE) {
                                    if (!getPresenter().getInteractor().CheckTotalLimit(nova) || !getPresenter().getInteractor().CheckMonthLimit(nova)) {
                                        AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                                                .setTitle("Confirm")
                                                .setMessage("Are you sure you want to change this transaction?")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        getPresenter().refreshTransactionsChange(String.valueOf(IDtransakcije), date.getText().toString(), amount.getText().toString(),
                                                                title.getText().toString(), type.getSelectedItem().toString(), izmjenaDesc, izmjenaInterval, izmjenaEndDate);


                                                         if(nova.getType() == transactionType.REGULARPAYMENT){
                                                            long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                                            long number = numOfDays/noviInterval;
                                                            budzet -= nova.getAmount()*number;
                                                        }
                                                        else{
                                                            budzet -= nova.getAmount();
                                                        }

                                                        onButtonClick.Refresh();
                                                        getFragmentManager().popBackStack();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        stari_budget = 0.0;
                                                    }
                                                })
                                                .show();
                                    } else {
                                        getPresenter().refreshTransactionsChange(String.valueOf(IDtransakcije), date.getText().toString(), amount.getText().toString(),
                                                title.getText().toString(), type.getSelectedItem().toString(), izmjenaDesc, izmjenaInterval, izmjenaEndDate);

                                        if(nova.getType() == transactionType.REGULARPAYMENT){
                                            long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                            long number = numOfDays/noviInterval;
                                            budzet -= nova.getAmount()*number;
                                        }
                                        else{
                                            budzet -= nova.getAmount();
                                        }

                                        onButtonClick.Refresh();
                                        getFragmentManager().popBackStack();
                                    }
                                } else {
                                    getPresenter().refreshTransactionsChange(String.valueOf(IDtransakcije), date.getText().toString(), amount.getText().toString(),
                                            title.getText().toString(), type.getSelectedItem().toString(), izmjenaDesc, izmjenaInterval, izmjenaEndDate);

                                    if(nova.getType() == transactionType.REGULARINCOME){
                                        long numOfDays = DAYS.between(LocalDate.parse(date.getText().toString()), noviEndDate);
                                        long number = numOfDays/noviInterval;
                                        budzet += nova.getAmount()*number;
                                    }
                                    else{
                                        budzet += nova.getAmount();
                                    }

                                    onButtonClick.Refresh();
                                    getFragmentManager().popBackStack();
                                }

                                budzet = racun.getBudget() - stari_budget + budzet;
                               // racun.setBudget(budzet);
                                getPresenter().azurirajBudzet(String.valueOf(budzet), null, null);
                                onButtonClick.Refresh();
                            }
                        }
                    }
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {

                double stari_budget = 0.0;
                double budzet = 0.0;

                @Override
                public void onClick(View v) {

                    if(type_before == transactionType.REGULARPAYMENT){
                        long numOfDays = DAYS.between(date_before, endDate_before);
                        long number = numOfDays/interval_before;
                        stari_budget -= amount_before*number;
                    }
                    else if (type_before == transactionType.INDIVIDUALPAYMENT || type_before == transactionType.PURCHASE){
                        stari_budget -= amount_before;
                    }
                    else if(type_before == transactionType.REGULARINCOME){
                        long numOfDays = DAYS.between(date_before, endDate_before);
                        long number = numOfDays/interval_before;
                        stari_budget += amount_before*number;
                    }
                    else if(type_before == transactionType.INDIVIDUALINCOME){
                        stari_budget += amount_before;
                    }

                    AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm Deletion")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getPresenter().refreshTransactionsRemove(String.valueOf(IDtransakcije));

                                    //budzet = racun.getBudget() - stari_budget;

                                    onButtonClick.Refresh();
                                    getFragmentManager().popBackStack();
                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stari_budget = 0.0;
                                    getFragmentManager().popBackStack();
                                }
                            })
                            .show();

                    stari_budget = racun.getBudget() - stari_budget;
                    //racun.setBudget(stari_budget);
                    getPresenter().azurirajBudzet(String.valueOf(stari_budget), null, null);
                    onButtonClick.Refresh();
                }
            });


        }
            return view;
        }

    @Override
    public void notifyTransactionListDataSetChanged() {
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeTransaction(ArrayList<Transaction> transactions, int indeks, Transaction t) {
        transactions.set(indeks, t);
    }

    @Override
    public void removeTransaction(ArrayList<Transaction> transactions, int indeks){
        transactions.remove(indeks);
    }

    @Override
    public void addTransaction(ArrayList<Transaction> transactions, Transaction t) {
        transactions.add(t);
    }

    @Override
    public void setTransactions(ArrayList<Transaction> results) {

    }

    @Override
    public void setAccount(Account racun) {
        this.racun = racun;
    }

}










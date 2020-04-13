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

    private OnItemClick2 onItemClick2;

    public interface OnItemClick2 {
        void onItemClicked(Transaction transaction);
        //void onAddClicked();
        void Refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Ovdje se dodjeljuje layout fragmentu,
        // tj. ˇsta ´ce se nalaziti unutar fragmenta
        //Ovu liniju ´cemo poslije promijeniti
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        onItemClick2 = (OnItemClick2) getActivity();


        arrayAdapter = new ArrayAdapter<Transaction>(getActivity(), R.layout.transaction_detail_activity, getPresenter().getModel());

       /* getPresenter().create((LocalDate)getActivity().getIntent().getSerializableExtra("date"),
                (double)getActivity().getIntent().getSerializableExtra("amount"),
                getActivity().getIntent().getStringExtra("title"),
                (transactionType)getActivity().getIntent().getSerializableExtra("type"),
                getActivity().getIntent().getStringExtra("desc"),
                (Integer)getActivity().getIntent().getSerializableExtra("interval"),
                (LocalDate)getActivity().getIntent().getSerializableExtra("endDate"));*/

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
                    TextView tipSpinnera = view.findViewById(R.id.textView);

                    tipSpinnera.setBackgroundColor(Color.GREEN);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

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
                transactionType noviTip;
                Integer noviInterval;
                String noviDesc;
                LocalDate noviEndDate;

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

                        TextView tipSpinnera = view.findViewById(R.id.textView);
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
                        } else {
                            noviInterval = Integer.parseInt(interval.getText().toString());
                        }

                        if (desc.getText().toString().equals("")) {
                            noviDesc = null;
                        } else {
                            noviDesc = desc.getText().toString();
                        }

                        if (endDate.getText().toString().equals("")) {
                            noviEndDate = null;
                        } else {
                            noviEndDate = LocalDate.parse(endDate.getText().toString());
                        }


                        Transaction nova = new Transaction(LocalDate.parse(date.getText().toString()), Double.parseDouble(amount.getText().toString()),
                                title.getText().toString(), noviTip, noviDesc, noviInterval, noviEndDate);


                        if (indeks == -1) {

                            if (nova.getType() == transactionType.REGULARPAYMENT || nova.getType() == transactionType.INDIVIDUALPAYMENT ||
                                    nova.getType() == transactionType.PURCHASE) {
                                if (!getPresenter().getInteractor().CheckTotalLimit(nova) || !getPresenter().getInteractor().CheckMonthLimit(nova)) {
                                    AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                                            .setTitle("Confirm")
                                            .setMessage("Are you sure you want to add this transaction?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    getPresenter().refreshTransactionsAdd(nova);
                                                    onItemClick2.Refresh();
                                                    //getActivity().finish();
                                                    getFragmentManager().popBackStack();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //finish();
                                                }
                                            })
                                            .show();
                                } else {
                                    getPresenter().refreshTransactionsAdd(nova);
                                    onItemClick2.Refresh();
                                    //getActivity().finish();
                                    getFragmentManager().popBackStack();
                                }
                            } else {
                                getPresenter().refreshTransactionsAdd(nova);
                                onItemClick2.Refresh();
                                //getActivity().finish();
                                getFragmentManager().popBackStack();
                            }
                        } else {
                            if (nova.getType() == transactionType.REGULARPAYMENT || nova.getType() == transactionType.INDIVIDUALPAYMENT ||
                                    nova.getType() == transactionType.PURCHASE) {
                                if (!getPresenter().getInteractor().CheckTotalLimit(nova) || !getPresenter().getInteractor().CheckMonthLimit(nova)) {
                                    //AlertDialog alertDialog2 = new AlertDialog.Builder(TransactionDetailActivity.this)
                                    AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                                            .setTitle("Confirm")
                                            .setMessage("Are you sure you want to change this transaction?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    getPresenter().refreshTransactionsChange(indeks, nova);
                                                    onItemClick2.Refresh();
                                                    //getActivity().finish();
                                                    getFragmentManager().popBackStack();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                } else {
                                    getPresenter().refreshTransactionsChange(indeks, nova);
                                    onItemClick2.Refresh();
                                    //getActivity().finish();
                                    getFragmentManager().popBackStack();
                                }
                            } else {
                                getPresenter().refreshTransactionsChange(indeks, nova);
                                onItemClick2.Refresh();
                                //getActivity().finish();
                                getFragmentManager().popBackStack();
                            }
                        }
                    }
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm Deletion")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getPresenter().refreshTransactionsRemove(indeks);
                                    onItemClick2.Refresh();
                                    //getActivity().finish();
                                    getFragmentManager().popBackStack();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //getActivity().finish();
                                    getFragmentManager().popBackStack();
                                }
                            })
                            .show();
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

}










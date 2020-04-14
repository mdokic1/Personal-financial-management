package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BudgetFragment extends Fragment implements IBudgetView {
    private TextView budzet;
    private TextView mLimit;
    private TextView tLimit;
    private Button sacuvaj;

    private IBudgetPresenter budgetPresenter;

    public IBudgetPresenter getPresenter() {
        if (budgetPresenter == null) {
            budgetPresenter = new BudgetPresenter(this, getActivity());
        }
        return budgetPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        if (getArguments() != null && getArguments().containsKey("account")) {
            getPresenter().setAccount(getArguments().getParcelable("account"));


            budzet = view.findViewById(R.id.budzet);
            mLimit = view.findViewById(R.id.mLimit);
            tLimit = view.findViewById(R.id.tLimit);
            sacuvaj = view.findViewById(R.id.sacuvaj);

            budzet.setText(String.valueOf(getPresenter().getAccount().getBudget()));
            mLimit.setText(String.valueOf(getPresenter().getAccount().getMonthLimit()));
            tLimit.setText(String.valueOf(getPresenter().getAccount().getTotalLimit()));

            getPresenter().create(Double.parseDouble(budzet.getText().toString()), Integer.parseInt(tLimit.getText().toString()),
                    Integer.parseInt(mLimit.getText().toString()));


            sacuvaj.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Account novi = new Account(Double.parseDouble(budzet.getText().toString()), Integer.parseInt(tLimit.getText().toString()),
                            Integer.parseInt(mLimit.getText().toString()));

                    Account stari = getPresenter().getAccount();
                    getPresenter().ChangeAccount(stari, novi);
                }
            });


        }
        return view;
    }

    @Override
    public void Change(Account stari, Account novi) {

        stari.setBudget(novi.getBudget());
        stari.setMonthLimit(novi.getMonthLimit());
        stari.setTotalLimit(novi.getTotalLimit());


        /*mLimit.setText(String.valueOf(novi.getMonthLimit()));
        tLimit.setText(String.valueOf(novi.getTotalLimit()));*/
    }
}

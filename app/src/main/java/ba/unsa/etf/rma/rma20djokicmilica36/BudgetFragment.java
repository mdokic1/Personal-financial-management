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
    private Button graphs;
    private Button buttonMain;

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
            graphs = view.findViewById(R.id.graphs);
            buttonMain = view.findViewById(R.id.buttonMain);

            budzet.setText(String.valueOf(getPresenter().getAccount().getBudget()));
            mLimit.setText(String.valueOf(getPresenter().getAccount().getMonthLimit()));
            tLimit.setText(String.valueOf(getPresenter().getAccount().getTotalLimit()));
            //getPresenter().refreshAccount();

            /*getPresenter().create(Double.parseDouble(budzet.getText().toString()), Integer.parseInt(tLimit.getText().toString()),
                    Integer.parseInt(mLimit.getText().toString()));*/

            Double stariBudget = Double.parseDouble(budzet.getText().toString());
            Integer stariTotalLimit = Integer.parseInt(tLimit.getText().toString());
            Integer stariMonthLimit = Integer.parseInt(mLimit.getText().toString());


            sacuvaj.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String total = null, month = null;
                    Account novi = new Account(Double.parseDouble(budzet.getText().toString()), Integer.parseInt(tLimit.getText().toString()),
                            Integer.parseInt(mLimit.getText().toString()));

                    //Account stari = getPresenter().getAccount();
                     if(tLimit.getText().toString().equals(String.valueOf(stariTotalLimit))){
                         total = null;
                     }
                     else{
                         total = tLimit.getText().toString();
                     }

                     if(mLimit.getText().toString().equals(String.valueOf(stariMonthLimit))){
                         month = null;
                     }
                     else{
                         month = mLimit.getText().toString();
                     }

                     getPresenter().changeAccount(budzet.getText().toString(), total, month);
                     getFragmentManager().popBackStack();
                }
            });

            graphs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  GraphsFragment graphsFragment = new GraphsFragment();
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, graphsFragment).addToBackStack(null).commit();

                }
            });

            buttonMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransactionListFragment listFragment = new TransactionListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, listFragment).addToBackStack(null).commit();
                }
            });


        }
        return view;
    }


    @Override
    public void setAccount(Account racun) {
        budzet.setText(String.valueOf(racun.getBudget()));
        tLimit.setText(String.valueOf(racun.getTotalLimit()));
        mLimit.setText(String.valueOf(racun.getMonthLimit()));
    }
}

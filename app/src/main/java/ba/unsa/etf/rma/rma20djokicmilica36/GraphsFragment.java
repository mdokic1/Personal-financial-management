package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class GraphsFragment extends Fragment implements IGraphsView {
    private Spinner tipGrafa;
    private Button buttonGMain;
    private Button buttonGBudget;

    private GraphsAdapter graphsAdapter;
    private Account racun;

    private IGraphsPresenter graphsPresenter;

    public IGraphsPresenter getPresenter() {
        if (graphsPresenter == null) {
            graphsPresenter = new GraphsPresenter(this, getActivity());
        }
        return graphsPresenter;
    }

    BarChart potrosnja;
    BarChart zarada;
    BarChart ukupno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);

        tipGrafa = view.findViewById(R.id.tipGrafa);
        potrosnja = view.findViewById(R.id.potrosnja);
        zarada = view.findViewById(R.id.zarada);
        ukupno = view.findViewById(R.id.ukupno);

        buttonGMain = view.findViewById(R.id.buttonGMain);
        buttonGBudget = view.findViewById(R.id.buttonGBudget);

        potrosnja.setDrawBarShadow(false);
        zarada.setDrawBarShadow(false);
        ukupno.setDrawBarShadow(false);

        potrosnja.setDrawValueAboveBar(true);
        zarada.setDrawValueAboveBar(true);
        ukupno.setDrawValueAboveBar(true);

        potrosnja.setMaxVisibleValueCount(50);
        zarada.setMaxVisibleValueCount(50);
        ukupno.setMaxVisibleValueCount(50);

        potrosnja.setPinchZoom(false);
        zarada.setPinchZoom(false);
        ukupno.setPinchZoom(false);

        potrosnja.setDrawGridBackground(true);
        zarada.setDrawGridBackground(true);
        ukupno.setDrawGridBackground(true);

        graphsAdapter = new GraphsAdapter(getActivity(), R.layout.graph_element, getPresenter().getIntervali());

        tipGrafa.setAdapter(graphsAdapter);

        getPresenter().makeGraphs("Time interval");

        potrosnja.setData(getPresenter().getBarDataPotrosnja());
        zarada.setData(getPresenter().getBarDataZarada());
        ukupno.setData(getPresenter().getBarDataUkupno());

        tipGrafa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                getPresenter().makeGraphs(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonGMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionListFragment listFragment = new TransactionListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, listFragment).addToBackStack(null).commit();
            }
        });

        buttonGBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                /*Account account = new Account(getPresenter().getInteractor().getBModel().racun.getBudget(),
                        getPresenter().getInteractor().getBModel().racun.getTotalLimit(),
                        getPresenter().getInteractor().getBModel().racun.getMonthLimit());*/
                arguments.putParcelable("account", racun);
                BudgetFragment budgetFragment = new BudgetFragment();
                budgetFragment.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, budgetFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void setData(BarData barDataPotrosnja, BarData barDataZarada, BarData barDataUkupno) {
        potrosnja.setData(getPresenter().getBarDataPotrosnja());
        zarada.setData(getPresenter().getBarDataZarada());
        ukupno.setData(getPresenter().getBarDataUkupno());
    }

    @Override
    public void setRacun(Account racun) {
        this.racun = racun;
    }
}

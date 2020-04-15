package ba.unsa.etf.rma.rma20djokicmilica36;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

public class GraphsFragment extends Fragment implements IGraphsView {
    private Spinner tipGrafa;

    private GraphsAdapter graphsAdapter;

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

        tipGrafa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                getPresenter().makeGraphs(selectedItem);

                potrosnja.setData(getPresenter().getBarDataPotrosnja());
                zarada.setData(getPresenter().getBarDataZarada());
                ukupno.setData(getPresenter().getBarDataUkupno());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
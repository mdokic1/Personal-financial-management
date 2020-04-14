package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);

        tipGrafa = view.findViewById(R.id.tipGrafa);
        graphsAdapter = new GraphsAdapter(getActivity(), R.layout.graph_element, getPresenter().getIntervali());

        tipGrafa.setAdapter(graphsAdapter);

        return view;
    }
}

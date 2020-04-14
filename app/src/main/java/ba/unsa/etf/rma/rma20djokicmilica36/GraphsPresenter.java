package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;

import java.util.ArrayList;

public class GraphsPresenter implements IGraphsPresenter {
    private IGraphsView view;
    private static ITransactionListInteractor interactor;
    private Context context;

    public static ArrayList<String> intervali = new ArrayList<String>(){
        {
            add("Time interval");
            add("Day");
            add("Week");
            add("Month");
        }
    };

    public ITransactionListInteractor getInteractor() {
        return interactor;
    }

    public ArrayList<String> getIntervali() {
        return intervali;
    }

    public GraphsPresenter(IGraphsView view, Context context) {
        this.view       = view;
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }
}

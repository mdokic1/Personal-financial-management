package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class GraphsPresenter implements IGraphsPresenter {
    private IGraphsView view;
    private static ITransactionListInteractor interactor;
    private Context context;

    ArrayList<BarEntry> barEntriesPotrosnja = new ArrayList<>();
    ArrayList<BarEntry> barEntriesZarada = new ArrayList<>();
    ArrayList<BarEntry> barEntriesUkupno = new ArrayList<>();

    BarDataSet barDataSetPotrosnja;
    BarDataSet barDataSetZarada;
    BarDataSet barDataSetUkupno;

    BarData barDataPotrosnja;
    BarData barDataZarada;
    BarData barDataUkupno;



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

    public ArrayList<BarEntry> getBarEntriesPotrosnja() { return barEntriesPotrosnja; }
    public BarData getBarDataPotrosnja(){ return barDataPotrosnja; }
    public BarData getBarDataZarada(){ return barDataZarada; }
    public BarData getBarDataUkupno(){ return barDataUkupno; }

    @Override
    public void makeGraphs(String selectedItem) {
        if(selectedItem.equals("Time interval") || selectedItem.equals("Month")){
            barEntriesPotrosnja.add(new BarEntry(1, getInteractor().MjesecnaPotrosnja(1)));
            barEntriesPotrosnja.add(new BarEntry(2, getInteractor().MjesecnaPotrosnja(2)));
            barEntriesPotrosnja.add(new BarEntry(3, getInteractor().MjesecnaPotrosnja(3)));
            barEntriesPotrosnja.add(new BarEntry(4, getInteractor().MjesecnaPotrosnja(4)));
            barEntriesPotrosnja.add(new BarEntry(5, getInteractor().MjesecnaPotrosnja(5)));
            barEntriesPotrosnja.add(new BarEntry(6, getInteractor().MjesecnaPotrosnja(6)));
            barEntriesPotrosnja.add(new BarEntry(7, getInteractor().MjesecnaPotrosnja(7)));
            barEntriesPotrosnja.add(new BarEntry(8, getInteractor().MjesecnaPotrosnja(8)));
            barEntriesPotrosnja.add(new BarEntry(9, getInteractor().MjesecnaPotrosnja(9)));
            barEntriesPotrosnja.add(new BarEntry(10, getInteractor().MjesecnaPotrosnja(10)));
            barEntriesPotrosnja.add(new BarEntry(11, getInteractor().MjesecnaPotrosnja(11)));
            barEntriesPotrosnja.add(new BarEntry(12, getInteractor().MjesecnaPotrosnja(12)));

            barDataSetPotrosnja = new BarDataSet(barEntriesPotrosnja, "Potrošnja");
            barDataSetPotrosnja.setColor(Color.RED);

            barDataPotrosnja = new BarData(barDataSetPotrosnja);
            barDataPotrosnja.setBarWidth(0.9f);

            barEntriesZarada.add(new BarEntry(1, getInteractor().MjesecnaZarada(1)));
            barEntriesZarada.add(new BarEntry(2, getInteractor().MjesecnaZarada(2)));
            barEntriesZarada.add(new BarEntry(3, getInteractor().MjesecnaZarada(3)));
            barEntriesZarada.add(new BarEntry(4, getInteractor().MjesecnaZarada(4)));
            barEntriesZarada.add(new BarEntry(5, getInteractor().MjesecnaZarada(5)));
            barEntriesZarada.add(new BarEntry(6, getInteractor().MjesecnaZarada(6)));
            barEntriesZarada.add(new BarEntry(7, getInteractor().MjesecnaZarada(7)));
            barEntriesZarada.add(new BarEntry(8, getInteractor().MjesecnaZarada(8)));
            barEntriesZarada.add(new BarEntry(9, getInteractor().MjesecnaZarada(9)));
            barEntriesZarada.add(new BarEntry(10, getInteractor().MjesecnaZarada(10)));
            barEntriesZarada.add(new BarEntry(11, getInteractor().MjesecnaZarada(11)));
            barEntriesZarada.add(new BarEntry(12, getInteractor().MjesecnaZarada(12)));

            barDataSetZarada = new BarDataSet(barEntriesZarada, "Zarada");
            barDataSetZarada.setColor(Color.GREEN);

            barDataZarada = new BarData(barDataSetZarada);
            barDataZarada.setBarWidth(0.9f);

            barEntriesUkupno.add(new BarEntry(1, getInteractor().Ukupno(1)));
            barEntriesUkupno.add(new BarEntry(2, getInteractor().Ukupno(2)));
            barEntriesUkupno.add(new BarEntry(3, getInteractor().Ukupno(3)));
            barEntriesUkupno.add(new BarEntry(4, getInteractor().Ukupno(4)));
            barEntriesUkupno.add(new BarEntry(5, getInteractor().Ukupno(5)));
            barEntriesUkupno.add(new BarEntry(6, getInteractor().Ukupno(6)));
            barEntriesUkupno.add(new BarEntry(7, getInteractor().Ukupno(7)));
            barEntriesUkupno.add(new BarEntry(8, getInteractor().Ukupno(8)));
            barEntriesUkupno.add(new BarEntry(9, getInteractor().Ukupno(9)));
            barEntriesUkupno.add(new BarEntry(10, getInteractor().Ukupno(10)));
            barEntriesUkupno.add(new BarEntry(11, getInteractor().Ukupno(11)));
            barEntriesUkupno.add(new BarEntry(12, getInteractor().Ukupno(12)));

            barDataSetUkupno = new BarDataSet(barEntriesUkupno, "Ukupno stanje");
            barDataSetUkupno.setColor(Color.BLUE);

            barDataUkupno = new BarData(barDataSetUkupno);
            barDataUkupno.setBarWidth(0.9f);




        }

    }
}

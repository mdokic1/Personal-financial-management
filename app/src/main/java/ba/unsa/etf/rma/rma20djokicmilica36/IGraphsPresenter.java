package ba.unsa.etf.rma.rma20djokicmilica36;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public interface IGraphsPresenter {
    ArrayList<String> getIntervali();

    void makeGraphs(String selectedItem);
    ArrayList<BarEntry> getBarEntriesPotrosnja();

    BarData getBarDataPotrosnja();
    BarData getBarDataZarada();
    BarData getBarDataUkupno();
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import com.github.mikephil.charting.data.BarData;

public interface IGraphsView {
    void setData(BarData barDataPotrosnja, BarData barDataZarada, BarData barDataUkupno);

    void setRacun(Account racun);
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
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

            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

            for(int i = 1; i <= 12; i++){
                barEntriesPotrosnja.add(new BarEntry(i, getInteractor().MjesecnaPotrosnja(i)));
                barEntriesZarada.add(new BarEntry(i, getInteractor().MjesecnaZarada(i)));
                barEntriesUkupno.add(new BarEntry(i, getInteractor().MjesecnoUkupno(i)));
            }
        }

        if(selectedItem.equals("Week")){

            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

            for(int i = 1; i <= 4; i++){
                barEntriesPotrosnja.add(new BarEntry(i, getInteractor().SedmicnaPotrosnja(i)));
                barEntriesZarada.add(new BarEntry(i, getInteractor().SedmicnaZarada(i)));
                barEntriesUkupno.add(new BarEntry(i, getInteractor().SedmicnoUkupno(i)));
            }
        }

        if(selectedItem.equals("Day")){

            ArrayList<Integer> brojDanaUMjesecu = new ArrayList<Integer>(){
                {
                    add(31);
                    add(29);
                    add(31);
                    add(30);
                    add(31);
                    add(30);
                    add(31);
                    add(31);
                    add(30);
                    add(31);
                    add(30);
                    add(31);
                }
            };

            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

            int trenutniDan = LocalDate.now().getDayOfMonth();
            int trenutniMjesec = LocalDate.now().getMonthValue();

            int pocPetlje = 0;
            int krajPetlje = 0;

            int pocetni = 1;
            int krajnji = brojDanaUMjesecu.get(trenutniMjesec - 1);
            int krajSedmice = 7;
            if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
                pocPetlje = pocetni;
                krajPetlje = krajSedmice;
            }
            else{
                while(pocetni <= krajnji){

                    if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
                        pocPetlje = pocetni;
                        krajPetlje = krajSedmice;
                        break;
                    }

                    pocetni = pocetni + 7;
                    krajSedmice = krajSedmice + 7;
                    if(pocetni > 28){
                        krajSedmice = krajnji;
                        pocPetlje = pocetni;
                        krajPetlje = krajnji;
                    }
                }
            }



            for(int i = pocPetlje; i <= krajPetlje; i++){
                barEntriesPotrosnja.add(new BarEntry(i, getInteractor().DnevnaPotrosnja(i)));
                barEntriesZarada.add(new BarEntry(i, getInteractor().DnevnaZarada(i)));
                barEntriesUkupno.add(new BarEntry(i, getInteractor().DnevnoUkupno(i)));
            }

        }

        barDataSetPotrosnja = new BarDataSet(barEntriesPotrosnja, "Potrošnja");
        barDataSetPotrosnja.setColor(Color.RED);

        barDataPotrosnja = new BarData(barDataSetPotrosnja);
        barDataPotrosnja.setBarWidth(0.9f);

        barDataSetZarada = new BarDataSet(barEntriesZarada, "Zarada");
        barDataSetZarada.setColor(Color.GREEN);

        barDataZarada = new BarData(barDataSetZarada);
        barDataZarada.setBarWidth(0.9f);

        barDataSetUkupno = new BarDataSet(barEntriesUkupno, "Ukupno stanje");
        barDataSetUkupno.setColor(Color.BLUE);

        barDataUkupno = new BarData(barDataSetUkupno);
        barDataUkupno.setBarWidth(0.9f);



    }
}

package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
import java.util.ArrayList;

public class GraphsPresenter implements IGraphsPresenter, TransactionListInteractor.OnTransactionsGetDone {
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

    ArrayList<Float> mjesecnaPotrosnjaIznosi = new ArrayList<Float>();
    ArrayList<Float> mjesecnaZaradaIznosi = new ArrayList<Float>();
    ArrayList<Float> mjesecnoUkupnoIznosi = new ArrayList<Float>();

    ArrayList<Float> sedmicnaPotrosnjaIznosi = new ArrayList<Float>();
    ArrayList<Float> sedmicnaZaradaIznosi = new ArrayList<Float>();
    ArrayList<Float> sedmicnoUkupnoIznosi = new ArrayList<Float>();

    ArrayList<Float> dnevnaPotrosnjaIznosi = new ArrayList<Float>();
    ArrayList<Float> dnevnaZaradaIznosi = new ArrayList<Float>();
    ArrayList<Float> dnevnoUkupnoIznosi = new ArrayList<Float>();

    String vrstaGrafa = "";


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
        //this.interactor = new TransactionListInteractor();
        this.context    = context;
    }

    public ArrayList<BarEntry> getBarEntriesPotrosnja() { return barEntriesPotrosnja; }
    public BarData getBarDataPotrosnja(){ return barDataPotrosnja; }
    public BarData getBarDataZarada(){ return barDataZarada; }
    public BarData getBarDataUkupno(){ return barDataUkupno; }

    @Override
    public void makeGraphs(String selectedItem) {
        vrstaGrafa = selectedItem;
        new TransactionListInteractor((TransactionListInteractor.OnTransactionsGetDone)this,
                "make graphs").execute(selectedItem);

    }

    @Override
    public void onDone(ArrayList<Transaction> results) {

    }

    @Override
    public void returnGraphs(ArrayList<Float> mjesecniGrafPotrosnja, ArrayList<Float> mjesecniGrafZarada, ArrayList<Float> mjesecniGrafUkupno,
                             ArrayList<Float> sedmicniGrafPotrosnja, ArrayList<Float> sedmicniGrafZarada, ArrayList<Float> sedmicniGrafUkupno,
                             ArrayList<Float> dnevniGrafPotrosnja, ArrayList<Float> dnevniGrafZarada, ArrayList<Float> dnevniGrafUkupno) {
        mjesecnaPotrosnjaIznosi = mjesecniGrafPotrosnja;
        mjesecnaZaradaIznosi = mjesecniGrafZarada;
        mjesecnoUkupnoIznosi = mjesecniGrafUkupno;
        sedmicnaPotrosnjaIznosi = sedmicniGrafPotrosnja;
        sedmicnaZaradaIznosi = sedmicniGrafZarada;
        sedmicnoUkupnoIznosi = sedmicniGrafUkupno;
        dnevnaPotrosnjaIznosi = dnevniGrafPotrosnja;
        dnevnaZaradaIznosi = dnevniGrafZarada;
        dnevnoUkupnoIznosi = dnevniGrafUkupno;

        if(vrstaGrafa.equals("Time interval") || vrstaGrafa.equals("Month")){
            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

            for(int i = 1; i <= 12; i++){
                barEntriesPotrosnja.add(new BarEntry(i, mjesecnaPotrosnjaIznosi.get(i - 1)));
                barEntriesZarada.add(new BarEntry(i, mjesecnaZaradaIznosi.get(i - 1)));
                barEntriesUkupno.add(new BarEntry(i, mjesecnoUkupnoIznosi.get(i - 1)));
            }
        }

        if(vrstaGrafa.equals("Week")){

            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

            for(int i = 1; i <= 4; i++){
                barEntriesPotrosnja.add(new BarEntry(i, sedmicnaPotrosnjaIznosi.get(i - 1)));
                barEntriesZarada.add(new BarEntry(i, sedmicnaZaradaIznosi.get(i - 1)));
                barEntriesUkupno.add(new BarEntry(i, sedmicnoUkupnoIznosi.get(i - 1)));
            }
        }

        if(vrstaGrafa.equals("Day")){

            barEntriesPotrosnja.clear();
            barEntriesZarada.clear();
            barEntriesUkupno.clear();

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

            if(pocPetlje > 28){
                krajPetlje = brojDanaUMjesecu.get(trenutniMjesec - 1);
            }


            for(int i = pocPetlje; i <= krajPetlje; i++){
                barEntriesPotrosnja.add(new BarEntry(i, dnevnaPotrosnjaIznosi.get(i - pocPetlje)));
                barEntriesZarada.add(new BarEntry(i, dnevnaZaradaIznosi.get(i - pocPetlje)));
                barEntriesUkupno.add(new BarEntry(i, dnevnoUkupnoIznosi.get(i - pocPetlje)));
            }

        }

        barDataSetPotrosnja = new BarDataSet(barEntriesPotrosnja, "Payments");
        barDataSetPotrosnja.setColor(Color.RED);

        barDataPotrosnja = new BarData(barDataSetPotrosnja);
        barDataPotrosnja.setBarWidth(0.9f);

        barDataSetZarada = new BarDataSet(barEntriesZarada, "Incomes");
        barDataSetZarada.setColor(Color.GREEN);

        barDataZarada = new BarData(barDataSetZarada);
        barDataZarada.setBarWidth(0.9f);

        barDataSetUkupno = new BarDataSet(barEntriesUkupno, "Total");
        barDataSetUkupno.setColor(Color.BLUE);

        barDataUkupno = new BarData(barDataSetUkupno);
        barDataUkupno.setBarWidth(0.9f);

        view.setData(barDataPotrosnja, barDataZarada, barDataUkupno);

    }

}

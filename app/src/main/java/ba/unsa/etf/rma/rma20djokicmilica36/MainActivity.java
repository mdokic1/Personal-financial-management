package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick, TransactionDetailFragment.OnButtonClick{



    private boolean twoPaneMode=false;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details = findViewById(R.id.transaction_detail);
        //slucaj layouta za ˇsiroke ekrane
        if (details != null) {
            twoPaneMode = true;
            TransactionDetailFragment detailFragment = (TransactionDetailFragment) fragmentManager.findFragmentById(R.id.transaction_detail);
            //provjerimo da li je fragment detalji ve´c kreiran
            if (detailFragment==null) {
                //kreiramo novi fragment FragmentDetalji ukoliko ve´c nije kreiran
                detailFragment = new TransactionDetailFragment();
                fragmentManager.beginTransaction(). replace(R.id.transaction_detail,detailFragment) .commit();

            }
        } else { twoPaneMode = false; }
        //Dodjeljivanje fragmenta MovieListFragment
        Fragment listFragment = fragmentManager.findFragmentByTag("list");
       //provjerimo da li je ve´c kreiran navedeni fragment
        if (listFragment==null){
            //ukoliko nije, kreiramo
            listFragment = new TransactionListFragment();
            fragmentManager.beginTransaction() .replace(R.id.transactions_list,listFragment,"list") .commit();
        }else{
            //sluˇcaj kada mijenjamo orijentaciju uredaja
            // iz portrait (uspravna) u landscape (vodoravna)
            // a u aktivnosti je bio otvoren fragment MovieDetailFragment
            // tada je potrebno skinuti MovieDetailFragment sa steka
            // kako ne bi bio dodan na mjesto fragmenta MovieListFragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }




        /*transactionListAdapter = new TransactionListAdapter(this, R.layout.list_element,new ArrayList<Transaction>());

        filterAdapter = new FilterAdapter(this, R.layout.filter_element, getPresenter().getFiltriranje());

        sortAdapter = new SortAdapter(this, R.layout.sort_element, getPresenter().getSortiranje());



        LocalDate trenutniDatum = LocalDate.now();

        int mjesec = trenutniDatum.getMonthValue();
        int godina = trenutniDatum.getYear();

        month = (TextView) findViewById(R.id.month);
        month.setText(Months.values()[mjesec - 1] + "," + Integer.toString(godina));

        leftArrow = (Button) findViewById(R.id.leftArrow);
        rightArrow = (Button) findViewById(R.id.rightArrow);

        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(transactionListAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        getPresenter().refreshTransactionsByDate();

        filter = (Spinner) findViewById(R.id.filter);
        filter.setAdapter(filterAdapter);

        sort = (Spinner) findViewById(R.id.sort);
        sort.setAdapter(sortAdapter);

        dodaj = (Button) findViewById(R.id.dodaj);

        glAmount = (TextView) findViewById(R.id.glAmount);
        lim = (TextView) findViewById(R.id.lim);
        double global = 0.0;


        for(Transaction t : getPresenter().getInteractor().get()){
            if (t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.REGULARINCOME){
                if(t.getType() == transactionType.REGULARINCOME){
                    long numOfDays = ChronoUnit.DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    global += t.getAmount()*number;
                }
                else{
                    global += t.getAmount();
                }
            }
            else{
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    global -= t.getAmount()*number;
                }
                else{
                    global -= t.getAmount();
                }
>>>>>>> origin/master
            }
        } else { twoPaneMode = false; }
        //Dodjeljivanje fragmenta MovieListFragment
        Fragment listFragment = fragmentManager.findFragmentByTag("list");
        //provjerimo da li je ve´c kreiran navedeni fragment
        if (listFragment==null){
            //ukoliko nije, kreiramo
            listFragment = new TransactionListFragment();
            fragmentManager.beginTransaction() .replace(R.id.transactions_list,listFragment,"list") .commit();
        } else{
            //sluˇcaj kada mijenjamo orijentaciju uredaja
            // iz portrait (uspravna) u landscape (vodoravna)
            // a u aktivnosti je bio otvoren fragment MovieDetailFragment
            // tada je potrebno skinuti MovieDetailFragment sa steka
            // kako ne bi bio dodan na mjesto fragmenta MovieListFragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }





        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String selectedType = sort.getSelectedItem().toString();
                getPresenter().refreshTransactionsByTypeSorted(selectedItem, selectedType);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String selectedItem = parent.getItemAtPosition(position).toString();
                String selectedFilter = filter.getSelectedItem().toString();
                getPresenter().refreshTransactionsByTypeSorted(selectedFilter, selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionDetailIntent = new Intent(MainActivity.this, TransactionDetailActivity.class);

                transactionDetailIntent.putExtra("date", LocalDate.now());
                transactionDetailIntent.putExtra("amount", 0D);
                transactionDetailIntent.putExtra("title", "");
                transactionDetailIntent.putExtra("type", transactionType.INDIVIDUALPAYMENT);
                transactionDetailIntent.putExtra("desc", "");
                transactionDetailIntent.putExtra("interval", 0);
                transactionDetailIntent.putExtra("endDate", LocalDate.now());
                MainActivity.this.startActivity(transactionDetailIntent);
            }
        });*/


    }

    @Override
    public void onResume(){
        super.onResume();
    }





    @Override
    public void onItemClicked(Transaction transaction) {
        //Priprema novog fragmenta FragmentDetalji
        Bundle arguments = new Bundle();
        arguments.putParcelable("transaction", transaction);
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        detailFragment.setArguments(arguments);
        if (twoPaneMode){
            //Sluˇcaj za ekrane sa ˇsirom dijagonalom
            getSupportFragmentManager().beginTransaction() .replace(R.id.transaction_detail, detailFragment) .commit();
        } else{
            //Sluˇcaj za ekrane sa poˇcetno zadanom ˇsirinom
            getSupportFragmentManager().beginTransaction() .replace(R.id.transactions_list,detailFragment) .addToBackStack(null).commit();
            //Primijetite liniju .addToBackStack(null)
        }
    }

    @Override
    public void onRightClicked(Account account) {

            Bundle arguments = new Bundle();
            arguments.putParcelable("account", account);
            BudgetFragment budgetFragment = new BudgetFragment();
            budgetFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, budgetFragment).addToBackStack(null).commit();


    }

    @Override
    public void Refresh(){
        if(twoPaneMode){
            TransactionListFragment listFragment = (TransactionListFragment)getSupportFragmentManager().findFragmentById(R.id.transactions_list);
            //TransactionDetailFragment detailFragment = (TransactionDetailFragment)getSupportFragmentManager().findFragmentById(R.id.transaction_detail);
            listFragment.getPresenter().refreshTransactions();
            listFragment.getPresenter().refreshTransactionsByDate();
            Spinner filter = findViewById(R.id.filter);
            Spinner sort = findViewById(R.id.sort);
            TextView glAmount = findViewById(R.id.glAmount);
            TextView lim = findViewById(R.id.lim);
            listFragment.getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            glAmount.setText("Global amount: " + round(listFragment.getPresenter().RefreshAmount(), 2));
            lim.setText("Limit" + listFragment.getPresenter().RefreshLimit());
        }

   }



}


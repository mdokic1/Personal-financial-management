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

public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick{



    private boolean twoPaneMode=false;


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
        } else{
            //sluˇcaj kada mijenjamo orijentaciju uredaja
            // iz portrait (uspravna) u landscape (vodoravna)
            // a u aktivnosti je bio otvoren fragment MovieDetailFragment
            // tada je potrebno skinuti MovieDetailFragment sa steka
            // kako ne bi bio dodan na mjesto fragmenta MovieListFragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }




        /**/

    }



   /* @Override
    public void onResume(){
        super.onResume();
        transactionListAdapter.setTransactions(getPresenter().getInteractor().get());
        getPresenter().refreshTransactionsByDate();
        getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
        double global = 0;
        for(Transaction t : getPresenter().getInteractor().get()){
            if (t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.REGULARINCOME){
                if(t.getType() == transactionType.REGULARINCOME){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
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
            }
        }

        glAmount.setText("Global amount: " + round(global, 2));
    }*/

    @Override
    public void onItemClicked(Transaction transaction) {
        //Priprema novog fragmenta FragmentDetalji
        Bundle arguments = new Bundle();
        arguments.putParcelable("movie", transaction);
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


}


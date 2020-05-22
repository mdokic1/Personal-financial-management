package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick, TransactionDetailFragment.OnButtonClick{


    private boolean twoPaneMode=false;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isTwoPaneMode() {
        return twoPaneMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details = findViewById(R.id.transaction_detail);

        if (details != null) {
            twoPaneMode = true;
            TransactionDetailFragment detailFragment = (TransactionDetailFragment) fragmentManager.findFragmentById(R.id.transaction_detail);
            if (detailFragment==null) {
                detailFragment = new TransactionDetailFragment();
                fragmentManager.beginTransaction(). replace(R.id.transaction_detail,detailFragment) .commit();
            }
        } else { twoPaneMode = false; }
        Fragment listFragment = fragmentManager.findFragmentByTag("list");
        if (listFragment==null){
            listFragment = new TransactionListFragment();
            fragmentManager.beginTransaction() .replace(R.id.transactions_list,listFragment,"list") .commit();
        }else{
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onItemClicked(Transaction transaction, String akcija) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("transaction", transaction);
        arguments.putString("akcija", akcija);
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        detailFragment.setArguments(arguments);
        if (twoPaneMode){
            getSupportFragmentManager().beginTransaction() .replace(R.id.transaction_detail, detailFragment) .commit();
        } else{
            getSupportFragmentManager().beginTransaction() .replace(R.id.transactions_list,detailFragment) .addToBackStack(null).commit();
        }
    }

    @Override
    public void onRightClicked(Account account) {

        if(!twoPaneMode){
            Bundle arguments = new Bundle();
            arguments.putParcelable("account", account);
            BudgetFragment budgetFragment = new BudgetFragment();
            budgetFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, budgetFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onLeftClicked(){
        if(!twoPaneMode){
            GraphsFragment graphsFragment = new GraphsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.transactions_list, graphsFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void Refresh(){
        if(twoPaneMode){
            TransactionListFragment listFragment = (TransactionListFragment)getSupportFragmentManager().findFragmentById(R.id.transactions_list);
            listFragment.getPresenter().refreshTransactions();
            listFragment.getPresenter().refreshTransactionsByDate();
            Spinner filter = findViewById(R.id.filter);
            Spinner sort = findViewById(R.id.sort);
            TextView glAmount = findViewById(R.id.glAmount);
            TextView lim = findViewById(R.id.lim);
            listFragment.getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            glAmount.setText("Global amount: " + round(listFragment.getPresenter().RefreshAmount(), 2));
            lim.setText("Limit: " + listFragment.getPresenter().RefreshLimit());
        }

   }

}


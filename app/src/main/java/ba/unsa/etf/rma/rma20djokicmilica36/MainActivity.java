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
    public boolean returnMode() {
        return twoPaneMode;
    }

    @Override
    public void Refresh(){
        if(twoPaneMode){
            TransactionListFragment listFragment = (TransactionListFragment)getSupportFragmentManager().findFragmentById(R.id.transactions_list);
            //listFragment.getPresenter().refreshTransactions();
            //listFragment.getPresenter().refreshTransactionsByDate();
            Spinner filter = findViewById(R.id.filter);
            Spinner sort = findViewById(R.id.sort);
            TextView month = findViewById(R.id.month);
            TextView glAmount = findViewById(R.id.glAmount);
            TextView lim = findViewById(R.id.lim);
            String typeId = "";
            if(filter.getSelectedItem().toString().equals("Regular payment")){
                typeId = "1";
            }
            if(filter.getSelectedItem().toString().equals("Regular income")){
                typeId = "2";
            }
            if(filter.getSelectedItem().toString().equals("Purchase")){
                typeId = "3";
            }
            if(filter.getSelectedItem().toString().equals("Individual income")){
                typeId = "4";
            }
            if(filter.getSelectedItem().toString().equals("Individual payment")){
                typeId = "5";
            }
            if(filter.getSelectedItem().toString().equals("All types") || filter.getSelectedItem().toString().equals("Filter by")){
                typeId = "";
            }

            String sortTip = "";

            if(sort.getSelectedItem().equals("Price - Ascending")){
                sortTip = "amount.asc";
            }
            if(sort.getSelectedItem().equals("Price - Descending")){
                sortTip = "amount.desc";
            }
            if(sort.getSelectedItem().equals("Title - Ascending")){
                sortTip = "title.asc";
            }
            if(sort.getSelectedItem().equals("Title - Descending")){
                sortTip = "title.desc";
            }
            if(sort.getSelectedItem().equals("Date - Ascending")){
                sortTip = "date.asc";
            }
            if(sort.getSelectedItem().equals("Date - Descending")){
                sortTip = "date.desc";
            }
            if(sort.getSelectedItem().equals("Sort by")){
                sortTip = "";
            }

            String d = month.getText().toString();
            int pozicija = d.indexOf(',');
            String mj = d.substring(0, pozicija);
            String god = d.substring(pozicija+1, d.length());

            if(mj.equals("January")) mj = "01";
            if(mj.equals("February")) mj = "02";
            if(mj.equals("March")) mj = "03";
            if(mj.equals("April")) mj = "04";
            if(mj.equals("May")) mj = "05";
            if(mj.equals("June")) mj = "06";
            if(mj.equals("July")) mj = "07";
            if(mj.equals("August")) mj = "08";
            if(mj.equals("September")) mj = "09";
            if(mj.equals("October")) mj = "10";
            if(mj.equals("November")) mj = "11";
            if(mj.equals("December")) mj = "12";
            listFragment.getPresenter().refreshByDateTypeSorted(typeId, sortTip, mj, god);
            listFragment.getPresenter().azurirajRacun();
            //listFragment.getPresenter().refreshTransactionsByTypeSorted(filter.getSelectedItem().toString(), sort.getSelectedItem().toString());
            glAmount.setText("Global amount: " + round(listFragment.getRacun().getBudget(), 2));
            lim.setText("Limit: " + listFragment.getRacun().getTotalLimit());
        }

   }

}


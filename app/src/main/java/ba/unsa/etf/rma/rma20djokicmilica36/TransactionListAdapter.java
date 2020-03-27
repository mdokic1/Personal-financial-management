package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;

import static ba.unsa.etf.rma.rma20djokicmilica36.TransactionsModel.transactions;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private int resource;
    public ImageView ikonica;
    public TextView title;
    public TextView iznos;

    public TransactionListAdapter(@NonNull Context context, int _resource, ArrayList<Transaction> items) {
        super(context, _resource,items);
        resource = _resource;
    }

   @NonNull
   @Override
   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LinearLayout newView;
       if (convertView == null) {
           newView = new LinearLayout(getContext());
           String inflater = Context.LAYOUT_INFLATER_SERVICE;
           LayoutInflater li;
           li = (LayoutInflater) getContext().
                   getSystemService(inflater);
           li.inflate(resource, newView, true);
       } else {
           newView = (LinearLayout) convertView;
       }

       Transaction transaction = getItem(position);

       ikonica = (ImageView) newView.findViewById(R.id.ikonica);
       title = (TextView) newView.findViewById(R.id.title);
       iznos = (TextView) newView.findViewById(R.id.iznos);

       title.setText(transaction.getTitle());
       iznos.setText(Integer.toString(transaction.getAmount()));

       transactionType tip = transaction.getType();

       if (tip.equals(transactionType.INDIVIDUALPAYMENT)) {
           ikonica.setImageResource(R.drawable.individual);
       }

       if (tip.equals(transactionType.REGULARPAYMENT)) {
           ikonica.setImageResource(R.drawable.regular);
       }

       if (tip.equals(transactionType.PURCHASE)) {
           ikonica.setImageResource(R.drawable.purchase);
       }

       if (tip.equals(transactionType.INDIVIDUALINCOME)) {
           ikonica.setImageResource(R.drawable.income);
       }

       if (tip.equals(transactionType.REGULARINCOME)) {
           ikonica.setImageResource(R.drawable.reginc);
       }

       return newView;

   }


    public void setTransactions(ArrayList<Transaction> transactions) {
        this.clear();
        this.addAll(transactions);
    }

    public void setTransactionsByType(ArrayList<Transaction> transactions) {
        this.clear();
        this.addAll(transactions);
    }

    public void setTransactionsSorted(ArrayList<Transaction> transactions){
        this.clear();
        this.addAll(transactions);
    }
}

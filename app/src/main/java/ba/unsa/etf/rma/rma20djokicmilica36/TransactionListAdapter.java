package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public void setTransactions(ArrayList<Transaction> movies) {
        this.addAll(transactions);
    }

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
       iznos.setText(transaction.getAmount());

       transactionType tip = transaction.getType();

       if (tip.equals("INDIVIDUALPAYMENT")) {
           ikonica.setImageResource(R.drawable.individual);
       }

       if (tip.equals("REGULARPAYMENT")) {
           ikonica.setImageResource(R.drawable.regular);
       }

       if (tip.equals("PURCHASE")) {
           ikonica.setImageResource(R.drawable.purchase);
       }

       if (tip.equals("INDIVIDUALINCOME")) {
           ikonica.setImageResource(R.drawable.iincome);
       }

       if (tip.equals("REGULARINCOME")) {
           ikonica.setImageResource(R.drawable.regincome);
       }

       return newView;

   }



}

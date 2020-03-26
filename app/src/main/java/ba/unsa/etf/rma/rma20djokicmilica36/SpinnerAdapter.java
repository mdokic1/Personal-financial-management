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

public class SpinnerAdapter extends ArrayAdapter<transactionType> {
    private int resource;
    public ImageView simbol;
    public TextView tipTransakcije;

    public SpinnerAdapter(@NonNull Context context, int _resource, ArrayList<transactionType> items) {
        super(context, _resource, items);
        resource = _resource;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

        transactionType tip = getItem(position);

        simbol = (ImageView) newView.findViewById(R.id.simbol);
        tipTransakcije = (TextView) newView.findViewById(R.id.tipTransakcije);

        tipTransakcije.setText(tip.toString());

        if(tip.equals(transactionType.INDIVIDUALPAYMENT)){
            simbol.setImageResource(R.drawable.individual);
        }

        if(tip.equals(transactionType.REGULARPAYMENT)){
            simbol.setImageResource(R.drawable.regular);
        }

        if(tip.equals(transactionType.PURCHASE)){
            simbol.setImageResource(R.drawable.purchase);
        }

        if(tip.equals(transactionType.REGULARINCOME)){
            simbol.setImageResource(R.drawable.regIncome);
        }

        if(tip.equals(transactionType.INDIVIDUALINCOME)){
            simbol.setImageResource(R.drawable.iIncome);
        }

        if(tip.equals("Filter by: ")){
            simbol.setImageResource(R.drawable.alltypes);
        }

        return newView;
    }
}

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

public class FilterAdapter extends ArrayAdapter<String> {
    private int resource;
    public ImageView simbol;
    public TextView tipTransakcije;


    public FilterAdapter(@NonNull Context context, int _resource, ArrayList<String> items) {
        super(context, _resource, items);
        resource = _resource;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
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

        String tip = getItem(position);

        simbol = (ImageView) newView.findViewById(R.id.simbol);
        tipTransakcije = (TextView) newView.findViewById(R.id.tipTransakcije);

        tipTransakcije.setText(tip);

        if(tip.equals("Individual payment")){
            simbol.setImageResource(R.drawable.individual);
        }

        if(tip.equals("Regular payment")){
            simbol.setImageResource(R.drawable.regular);
        }

        if(tip.equals("Purchase")){
            simbol.setImageResource(R.drawable.purchase);
        }

        if(tip.equals("Regular income")){
            simbol.setImageResource(R.drawable.reginc);
        }

        if(tip.equals("Individual income")){
            simbol.setImageResource(R.drawable.income);
        }

        if(tip.equals("All types")){
            simbol.setImageResource(R.drawable.alltypes);
        }

        if(tip.equals("Filter by")){
            simbol.setImageResource(0);
        }


        return newView;
    }
}


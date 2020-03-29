package ba.unsa.etf.rma.rma20djokicmilica36;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DetailSpinnerAdapter extends ArrayAdapter<String> {
    private int resource;
    public TextView textView;

    public DetailSpinnerAdapter(@NonNull Context context, int _resource, ArrayList<String> items) {
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

    private View createItemView(int position, View convertView, ViewGroup parent) {
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
        textView = (TextView) newView.findViewById(R.id.textView);
        textView.setText(tip);

        return newView;
    }
}
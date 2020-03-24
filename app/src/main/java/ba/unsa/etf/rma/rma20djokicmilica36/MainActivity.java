package ba.unsa.etf.rma.rma20djokicmilica36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ITransactionListView{

    private TextView glAmount;
    private TextView lim;
    private Spinner filter;
    private TextView month;
    private Button leftArrow;
    private Button rightArrow;
    private Spinner sort;
    private ListView listView;
    private Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}

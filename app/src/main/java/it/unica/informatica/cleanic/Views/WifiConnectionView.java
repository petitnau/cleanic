package it.unica.informatica.cleanic.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import it.unica.informatica.cleanic.R;
import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;

public class WifiConnectionView extends LinearLayout {

    ImageView iconView;
    TextView nameView;
    CardView cardView;
    Button next;

    String name;
    ArrayList<WifiConnectionView> wifis;

    boolean connected = false;

    public WifiConnectionView(Context context, String name) {
        super(context);
        this.name = name;

        initializeViews(context);
    }

    public void switchConnected(boolean connected) {
        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        if (connected) {
            iconView.setImageResource(R.drawable.ic_baseline_done_24);
            nameView.setTextColor(value.data);
            iconView.setColorFilter(value.data);
            wifis.forEach(w -> w.switchConnected(false));
            next.setEnabled(true);
        } else {
            iconView.setImageResource(R.drawable.ic_baseline_wifi_24);
            nameView.setTextColor(Color.parseColor("#808080"));
            iconView.setColorFilter(null);
            next.setEnabled(false);
        }
        this.connected = connected;
    }

    public void setRest(ArrayList<WifiConnectionView> wifis) {
        this.wifis = (ArrayList<WifiConnectionView>) wifis.clone();
        this.wifis.remove(this);
    }

    public void setButton(Button next) {
        this.next = next;
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wifi_connection_view, this);

        nameView = findViewById(R.id.wifi_name);
        iconView = findViewById(R.id.wifi_icon);
        cardView = findViewById(R.id.wifi_card);

        nameView.setText(name);
        cardView.setOnClickListener(v -> switchConnected(!connected));
    }
}

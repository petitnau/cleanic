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

public class NotificationView extends LinearLayout {

    String text;
    int icon;
    int color;

    TextView textView;
    ImageView iconView;

    public NotificationView(Context context, String text, int icon, int color) {
        super(context);
        this.text = text;
        this.icon = icon;
        this.color = color;

        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.notification_view, this);

        textView = findViewById(R.id.notification_text);
        iconView = findViewById(R.id.notification_icon);

        textView.setText(text);
        iconView.setImageResource(icon);
        iconView.setColorFilter(color);
    }
}

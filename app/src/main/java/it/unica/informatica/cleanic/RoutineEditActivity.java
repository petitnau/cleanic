package it.unica.informatica.cleanic;

import static it.unica.informatica.cleanic.utils.Utils.toggleVisibility;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;
import it.unica.informatica.cleanic.utils.Utils.Rooms;

public class RoutineEditActivity extends AppCompatActivity {

    CardView timeCard;
    TextView timeText;
    ImageView map_colored;
    List<ImageView> map = new ArrayList<>();
    MaterialButton[] weekdays = new MaterialButton[7];
    MaterialToolbar topBar;

    EditText routineName;
    Routine routine;

    private int hour;
    private int minutes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_edit_activity);

        topBar = findViewById(R.id.topAppBar);

        topBar.setNavigationOnClickListener(l -> {
            //handle X
        });

        topBar.setOnMenuItemClickListener(l -> {
            switch (l.getItemId()) {
                case R.id.save:
                    System.out.println("CIAOOO");
                    saveRoutine();
                    return true;
            }

            return false;
        });

        routineName = findViewById(R.id.routineName);
        timeCard = findViewById(R.id.time_card);
        map_colored = findViewById(R.id.map_colored);

        for (Rooms room : Rooms.values()) {
            ImageView roomImage = findViewById(room.getResourceId());
            map.add(roomImage);
        }

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        timeText = findViewById(R.id.timeText);
        timeText.setText(Utils.TIME_FORMATTER.format(calendar.getTime()));

        timeCard.setOnClickListener(v -> {

            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(hour)
                    .setMinute(minutes)
                    .build();
            picker.show(getSupportFragmentManager(), "routine_time_picker");

            picker.addOnPositiveButtonClickListener(dialog -> {
                int newHour = picker.getHour();
                int newMinute = picker.getMinute();
                setTime(newHour, newMinute);
            });
        });

        map_colored.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            ImageView imageView = (ImageView) v;
            int x = (int) event.getX();
            int y = (int) event.getY();

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmapImg = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);

            int pixel = bitmapImg.getPixel(x, y);
            String pixel_hex = String.format("#%08x", (0xFFFFFFFF & pixel));
            bitmapImg.recycle();

            switch (pixel_hex) {
                case "#ff6f009d":
                    toggleVisibility(map.get(Rooms.BEDROOM1.ordinal()));
                    break;
                case "#ff9b9b9b":
                    toggleVisibility(map.get(Rooms.LIVING_ROOM.ordinal()));
                    break;
                case "#ffffbb00":
                    toggleVisibility(map.get(Rooms.KITCHEN.ordinal()));
                    break;
                case "#ff0c00ff":
                    toggleVisibility(map.get(Rooms.BEDROOM2.ordinal()));
                    break;
                case "#ff00af00":
                    toggleVisibility(map.get(Rooms.VERANDA.ordinal()));
                    break;
                case "#ff01dcff":
                    toggleVisibility(map.get(Rooms.BATHROOM.ordinal()));
                    break;
                case "#ffff0004":
                    toggleVisibility(map.get(Rooms.STORAGE_ROOM.ordinal()));
                    break;
                case "#ff653d00":
                    toggleVisibility(map.get(Rooms.ANDITO.ordinal()));
                    break;
            }
            return true;
        });

        weekdays[0] = findViewById(R.id.MondayButton);
        weekdays[1] = findViewById(R.id.TuesdayButton);
        weekdays[2] = findViewById(R.id.WednesdayButton);
        weekdays[3] = findViewById(R.id.ThursdayButton);
        weekdays[4] = findViewById(R.id.FridayButton);
        weekdays[5] = findViewById(R.id.SaturdayButton);
        weekdays[6] = findViewById(R.id.SundayButton);
    }

    private void saveRoutine() {
        System.out.println(this.getApplicationContext());
        Routine test = new Routine(UUID.randomUUID(), routineName.toString(), timeText.getText().toString(), map, weekdays, getApplicationContext());
        test.saveRoutine();
        System.out.println(UUID.randomUUID());
    }


    private void setTime(int newHour, int newMinute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.setLenient(false);

        String format = Utils.TIME_FORMATTER.format(cal.getTime());
        timeText.setText(format);
        hour = newHour;
        minutes = newMinute;
    }
}

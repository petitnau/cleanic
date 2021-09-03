package it.unica.informatica.cleanic;

import static it.unica.informatica.cleanic.utils.Utils.toggleVisibility;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.IntStream;

import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;
import it.unica.informatica.cleanic.utils.Utils.Room;

public class RoutineEditActivity extends AppCompatActivity {

    CardView timeCard;
    TextView timeText;
    ImageView map_colored;
    ImageView[] map;
    MaterialButton[] weekdays = new MaterialButton[7];
    MaterialToolbar topBar;

    EditText routineName;
    Routine routine;

    private boolean isTimePickerOpen;
    private boolean isEditing;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_edit_activity);

        isEditing = getIntent().getExtras() != null;
        if (isEditing) {
            isTimePickerOpen = getIntent().getExtras().getBoolean("isTimePickerOpen");
            routine = Routine.getRoutineFromJson(getIntent().getExtras().getString("Routine"), this);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            routine = new Routine(hour, minutes, this);
        }

        topBar = findViewById(R.id.topAppBar);
        routineName = findViewById(R.id.routineName);
        timeCard = findViewById(R.id.time_card);
        map_colored = findViewById(R.id.map_colored);
        timeText = findViewById(R.id.timeText);

        topBar.setNavigationOnClickListener(l -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        topBar.setOnMenuItemClickListener(l -> {
            switch (l.getItemId()) {
                case R.id.save:
                    saveRoutine();
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    return true;
            }
            return false;
        });
        timeCard.setOnClickListener(v -> {
            openTimePicker();
        });
        map_colored.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            ImageView imageView = (ImageView) v;
            int x = (int) event.getX();
            int y = (int) event.getY();

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmapImg = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);

            int pixel = bitmapImg.getPixel(x, y);
            Room room = Room.colorToRoom(pixel);
            if (room != null) {
                toggleVisibility(map[room.ordinal()]);
            }

            bitmapImg.recycle();
            return true;
        });

        setDays(routine.getWeekDays());
        setMap(routine.getMap());

        timeText.setText(Utils.TIME_FORMATTER.format(routine.getCalendar().getTime()));
        routineName.setText(routine.getName());

        if (isTimePickerOpen) {
            openTimePicker();
        }
    }

    private void saveRoutine() {
        routine.setName(routineName.getText().toString());
        routine.setMap(Arrays.stream(map).map(x -> x.getVisibility() == View.VISIBLE).toArray(Boolean[]::new));
        routine.setWeekDays(Arrays.stream(weekdays).map(MaterialButton::isChecked).toArray(Boolean[]::new));
        routine.update();
    }

    private void openTimePicker() {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(routine.getHour())
                .setMinute(routine.getMinutes())
                .build();
        picker.show(getSupportFragmentManager(), "routine_time_picker");

        picker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = picker.getHour();
            int newMinute = picker.getMinute();
            setTime(newHour, newMinute);
        });
    }

    private void setTime(int newHour, int newMinute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.setLenient(false);

        String format = Utils.TIME_FORMATTER.format(cal.getTime());
        timeText.setText(format);
        routine.setHour(newHour);
        routine.setMinutes(newMinutes);
    }

    public void setDays(Boolean[] days) {
        System.out.println("ORCO");
        System.out.println(days);
        System.out.println(weekdays);
        weekdays[0] = findViewById(R.id.MondayButton);
        weekdays[1] = findViewById(R.id.TuesdayButton);
        weekdays[2] = findViewById(R.id.WednesdayButton);
        weekdays[3] = findViewById(R.id.ThursdayButton);
        weekdays[4] = findViewById(R.id.FridayButton);
        weekdays[5] = findViewById(R.id.SaturdayButton);
        weekdays[6] = findViewById(R.id.SundayButton);

        for (int i = 0; i < weekdays.length; i++){
            weekdays[i].setChecked(days[i]);
        }
    }
    private void setMap(Boolean[] booleanMapArray) {
        this.map = IntStream.range(0, Room.values().length).mapToObj(i -> {
            ImageView roomImage = findViewById(Room.values()[i].getResourceId());
            roomImage.setVisibility(booleanMapArray[i] ? View.VISIBLE : View.INVISIBLE);
            return roomImage;
        }).toArray(ImageView[]::new);
    }
}

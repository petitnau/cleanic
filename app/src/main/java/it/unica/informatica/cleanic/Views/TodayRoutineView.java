package it.unica.informatica.cleanic.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import it.unica.informatica.cleanic.R;
import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;

public class TodayRoutineView extends LinearLayout {

    private final Routine routine;

    TextView routineName, routineTime, ampm;

    public TodayRoutineView(Context context, Routine routine) {
        super(context);
        this.routine = routine;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.today_routine_view, this);

        routineName = findViewById(R.id.routineNameToday);

        routineName.setText(routine.getName());
        routineTime = findViewById(R.id.routineTimeToday);
        ampm = findViewById(R.id.ampm);


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, routine.getHour());
        cal.set(Calendar.MINUTE, routine.getMinutes());
        cal.setLenient(false);

        String[] time_string = Utils.TIME_FORMATTER.format(routine.getCalendar().getTime()).split(" ");
        routineTime.setText(time_string[0]);
        ampm.setText(time_string[1]);
    }
}

package it.unica.informatica.cleanic.Views;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import it.unica.informatica.cleanic.R;
import it.unica.informatica.cleanic.RoutineEditActivity;
import it.unica.informatica.cleanic.RoutinesFragment;
import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;

public class RoutineView extends LinearLayout {
    CardView all_card;
    CardView time_card;
    ImageButton more;
    Activity activity;
    TextView time, ampm, routineName, weekDays;
    SwitchMaterial switchMaterial;
    private final Routine routine;
    RoutinesFragment fragment;

    public RoutineView(Context context, Activity activity, Routine routine, RoutinesFragment fragment) {
        super(context);
        this.activity = activity;
        this.routine = routine;
        this.fragment = fragment;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.routine_view, this);

        all_card = findViewById(R.id.all_card);
        time_card = findViewById(R.id.time_card);
        time = findViewById(R.id.time);
        ampm = findViewById(R.id.ampm);
        weekDays = findViewById(R.id.weekDays);
        routineName = findViewById(R.id.routineName);
        more = findViewById(R.id.routine_more);
        switchMaterial = findViewById(R.id.active_switch);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, routine.getHour());
        cal.set(Calendar.MINUTE, routine.getMinutes());
        cal.setLenient(false);

        String[] time_string = Utils.TIME_FORMATTER.format(routine.getCalendar().getTime()).split(" ");
        time.setText(time_string[0]);
        ampm.setText(time_string[1]);

        weekDays.setText(routine.getWeekDaysText());

        String name = routine.getName();
        routineName.setText(name.isEmpty() ? "Untitled" : name);
        if(routine.isFavorite()) {
            routineName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24, 0, 0, 0);
        }
        switchMaterial.setChecked(routine.isActive());

        switchMaterial.setOnClickListener(l -> {
            routine.setActive(switchMaterial.isChecked());
            routine.update();
        });

        all_card.setOnClickListener(v -> {
            Intent intent = new Intent(new Intent(getContext(), RoutineEditActivity.class));
            intent.putExtra("isTimePickerOpen", false);
            intent.putExtra("Routine", routine.getJson());
            getContext().startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay);
        });

        time_card.setOnClickListener(v -> {
            Intent intent = new Intent(new Intent(getContext(), RoutineEditActivity.class));
            intent.putExtra("isTimePickerOpen", true);
            intent.putExtra("Routine", routine.getJson());
            getContext().startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay);
        });

        more.setOnClickListener(v -> {
            showMenu(v, R.menu.routine_info_menu);
        });
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        Menu menuOpts = popup.getMenu();

        //menuOpts.getItem(0).set

        if (routine.isFavorite()) {
            menuOpts.getItem(0).setVisible(false);
            menuOpts.getItem(1).setVisible(true);
        }
        else {
            menuOpts.getItem(1).setVisible(false);
            menuOpts.getItem(0).setVisible(true);
        }

        popup.setOnMenuItemClickListener(i -> {
            switch (i.getItemId()) {
                case R.id.favorite:
                    routine.setFavorite(true);
                    routine.update();
                    fragment.updateList();
                    break;
                case R.id.unfavorite:
                    routine.setFavorite(false);
                    routine.update();
                    fragment.updateList();
                    break;
                case R.id.delete:
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Delete Routine")
                            .setMessage("This routine will be eliminated")
                            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    routine.remove();
                                    fragment.updateList();
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .show();

                    return true;
            }

            return false;
        });
//        if (popup.getMenu() instanceof MenuBuilder) {
//            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
//            menuBuilder.setOptionalIconsVisible(true);
//            for (MenuItem item : menuBuilder.getVisibleItems()) {
//                int iconMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, getResources().getDisplayMetrics());
//                if (item.getIcon() != null) {
//                    item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
//                }
//            }
//        }
        popup.show();
    }
}

package it.unica.informatica.cleanic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.cardview.widget.CardView;

import it.unica.informatica.cleanic.utils.Routine;

public class RoutineView extends LinearLayout {
    CardView all_card;
    CardView time_card;
    ImageButton more;
    Activity activity;

    public RoutineView(Context context, Activity activity) {
        super(context);
        this.activity = activity;
        initializeViews(context);
    }

    public void setRoutine(Routine routine) {
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.routine_view, this);

        all_card = findViewById(R.id.all_card);
        time_card = findViewById(R.id.time_card);
        more = findViewById(R.id.routine_more);

        all_card.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), RoutineEditActivity.class));
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay);
        });

        time_card.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), RoutineEditActivity.class));
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay);
        });

        more.setOnClickListener(v -> {
            showMenu(v, R.menu.routine_info_menu);
        });
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(i -> true);
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

package it.unica.informatica.cleanic;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.unica.informatica.cleanic.Views.NotificationView;
import it.unica.informatica.cleanic.Views.RoutineView;
import it.unica.informatica.cleanic.Views.TodayRoutineView;
import it.unica.informatica.cleanic.utils.Routine;

public class HomeFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    FavoritesCollectionAdapter favoriteCollectionAdapter;
    ViewPager2 viewPager;
    LinearLayout todayRoutineLayout, notificationLayout;
    MaterialCardView card;
    ImageView map;
    String package_name;
    Timer timer;

    public void updateMap(boolean increase) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int curr = (preferences.getInt("cleaning_map", 0));
        getActivity().runOnUiThread(() -> {
            map.setImageResource(getResources().getIdentifier("clean"+(1+curr), "drawable", package_name));
        });
        if (increase)
            preferences.edit().putInt("cleaning_map", (curr+1)%25).apply();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateMap(true);
            }
        }, 0, 1500);
    }

    @Override
    public void onPause() {
        super.onPause();

        timer.cancel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        favoriteCollectionAdapter = new FavoritesCollectionAdapter(this);

        package_name = getActivity().getPackageName();
        map = view.findViewById(R.id.map);

        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(favoriteCollectionAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                }
        ).attach();


        card = view.findViewById(R.id.noTodayRoutineCard);
        todayRoutineLayout = getView().findViewById(R.id.todayRoutineLayout);
        notificationLayout = getView().findViewById(R.id.notification_list);

        timer = new Timer();

        updateMap(false);
        updateList();
        updateNotifications();
    }

    public void updateNotifications() {
        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic is stuck!", R.drawable.ic_baseline_warning_24, Color.parseColor("#dc3545")));
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic battery is critical!", R.drawable.ic_outline_battery_alert_24, Color.parseColor("#dc3545")));
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic battery is low.", R.drawable.ic_outline_battery_alert_24, Color.parseColor("#ffc107")));
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic is currently cleaning the house.", R.drawable.ic_baseline_album_24, value.data));
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic is charging.", R.drawable.ic_baseline_battery_charging_full_24, Color.parseColor("#007bff")));
        notificationLayout.addView(new NotificationView(getContext(),"Your cleanic is fully charged.", R.drawable.ic_baseline_battery_full_24, Color.parseColor("#28a745")));
    }

    public void updateList() {
        List<Routine> routines = Routine.getTodayRoutines(getContext());
        if (routines.isEmpty()){
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
            Collections.sort(routines);
            for (Routine routine : routines) {
                TodayRoutineView routineView = new TodayRoutineView(getContext(), routine);
                todayRoutineLayout.addView(routineView);
            }
        }
    }
}
package it.unica.informatica.cleanic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Collections;
import java.util.List;

import it.unica.informatica.cleanic.Views.RoutineView;
import it.unica.informatica.cleanic.Views.TodayRoutineView;
import it.unica.informatica.cleanic.utils.Routine;

public class HomeFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    FavoritesCollectionAdapter favoriteCollectionAdapter;
    ViewPager2 viewPager;
    LinearLayout todayRoutineLayout;
    MaterialCardView card;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        favoriteCollectionAdapter = new FavoritesCollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(favoriteCollectionAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                }
        ).attach();


        card = view.findViewById(R.id.noTodayRoutineCard);
        todayRoutineLayout = getView().findViewById(R.id.todayRoutineLayout);

        updateList();
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
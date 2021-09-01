package it.unica.informatica.cleanic;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import it.unica.informatica.cleanic.utils.Routine;
import it.unica.informatica.cleanic.utils.Utils;

public class FavoritesCollectionAdapter extends FragmentStateAdapter {
    Fragment fragment;

    public FavoritesCollectionAdapter(Fragment fragment) {
        super(fragment);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<Routine> routines = Routine.getFavorites(fragment.getContext());

        List<Routine> routines_selected;
        if (routines.size() > position*2 + 2)
            routines_selected = routines.subList(position, position+2);
        else
            routines_selected = routines.subList(position, position+1);

        Fragment fragment = new TabObjectFragment(routines_selected);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return Utils.halfCeil(Routine.getFavorites(fragment.getContext()).size());
    }
}
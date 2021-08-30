package it.unica.informatica.cleanic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import it.unica.informatica.cleanic.utils.Routine;

public class RoutinesFragment extends Fragment {
    Button newRoutine;
    LinearLayout routineList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //String json = savedRoutines.getString("Routine", "");
        Gson gson = new Gson();
        //Routine routine1 = gson.fromJson(json, Routine.class);

        SharedPreferences savedRoutines = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> newSet = new HashSet<String>(savedRoutines.getStringSet("Routine", new HashSet<String>()));
        newSet.forEach(x -> System.out.println("Set:" + x));

        newRoutine = getView().findViewById(R.id.new_routine_button);

        newRoutine.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RoutineEditActivity.class));
            getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_stay);
        });

        routineList = getView().findViewById(R.id.routine_list);

        RoutineView routine = new RoutineView(getContext(), getActivity());
        routineList.addView(routine);
    }
}
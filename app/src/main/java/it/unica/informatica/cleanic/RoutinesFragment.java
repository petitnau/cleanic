package it.unica.informatica.cleanic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import it.unica.informatica.cleanic.Views.RoutineView;
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
        newRoutine = getView().findViewById(R.id.new_routine_button);

        newRoutine.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RoutineEditActivity.class));
            getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_stay);
        });

        routineList = getView().findViewById(R.id.routine_list);

        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        routineList.removeAllViews();
        List<Routine> savedRoutines = Routine.getRoutines(this.getContext());
        Collections.sort(savedRoutines);
        for (Routine routine : savedRoutines) {
            RoutineView routineView = new RoutineView(getContext(), getActivity(), routine, this);
            routineList.addView(routineView);
        }
    }
}
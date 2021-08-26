package it.unica.informatica.cleanic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RoutinesFragment extends Fragment {
    Button newRoutine;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newRoutine = getView().findViewById(R.id.new_routine_button);

        newRoutine.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RoutineEditActivity.class));
            getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_stay );
        });
    }
}
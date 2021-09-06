package it.unica.informatica.cleanic.tutorial;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.unica.informatica.cleanic.R;

public class Tutorial1Fragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button nextButton = getView().findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            switchToFragment(new Tutorial1AFragment(), getActivity().getSupportFragmentManager());
        });
    }
}
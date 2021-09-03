package it.unica.informatica.cleanic.tutorial;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

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

import java.util.ArrayList;

import it.unica.informatica.cleanic.HomeActivity;
import it.unica.informatica.cleanic.HomeFragment;
import it.unica.informatica.cleanic.MainActivity;
import it.unica.informatica.cleanic.R;
import it.unica.informatica.cleanic.Views.WifiConnectionView;

public class Tutorial3Fragment extends Fragment {
    LinearLayout wifiList;
    Button nextButton, backButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial3, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButton = getView().findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            preferences.edit().putBoolean("tutorial", true).apply();

            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        backButton = getView().findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            switchToFragment(new Tutorial2Fragment(), getActivity().getSupportFragmentManager());
        });

        wifiList = getView().findViewById(R.id.wifiList);

        ArrayList<WifiConnectionView> connections = new ArrayList<>();
        connections.add(new WifiConnectionView(getContext(), "FRITZ!Box 7530 XA"));
        connections.add(new WifiConnectionView(getContext(), "Vodafone C123894"));
        connections.add(new WifiConnectionView(getContext(), "Telecom T-9584LZPK"));
        for (WifiConnectionView connection : connections)
        {
            connection.setRest(connections);
            connection.setButton(nextButton);
            wifiList.addView(connection);
        }

    }
}
package it.unica.informatica.cleanic.tutorial;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Timer;
import java.util.TimerTask;

import it.unica.informatica.cleanic.HomeActivity;
import it.unica.informatica.cleanic.R;

public class Tutorial2AFragment extends Fragment {
    Button nextButton, backButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial2a, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CircularProgressIndicator progress = getActivity().findViewById(R.id.progressbar);
        Button nextButton = getActivity().findViewById(R.id.next_button);
        Button backButton = getActivity().findViewById(R.id.back_button);
        ImageView progressbar_done = getActivity().findViewById(R.id.progressbar_done);
        TextView title_text = getActivity().findViewById(R.id.title_text);
        TextView body_text = getActivity().findViewById(R.id.body_text);

        Timer timer0 = new Timer();
        timer0.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progress.getProgress() == progress.getMax()) {
                    getActivity().runOnUiThread(() -> {
                        nextButton.setEnabled(true);
                        progressbar_done.setVisibility(View.VISIBLE);
                        title_text.setText("Your map has been mapped succesfully!");
                        body_text.setText("You can now go use your app");
                    });
                    timer0.cancel();
                } else {
                    getActivity().runOnUiThread(() -> {
                        progress.setProgress(progress.getProgress() + 1, true);
                    });
                }
            }
        }, 0, 100);

        backButton.setOnClickListener(v -> {
            switchToFragment(new TutorialWiFiFragment(), getActivity().getSupportFragmentManager());
        });

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
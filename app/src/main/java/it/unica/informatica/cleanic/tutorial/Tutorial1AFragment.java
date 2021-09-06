package it.unica.informatica.cleanic.tutorial;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Timer;
import java.util.TimerTask;

import it.unica.informatica.cleanic.R;

public class Tutorial1AFragment extends Fragment {
    Button backButton, nextButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial1a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CircularProgressIndicator progress = getActivity().findViewById(R.id.progressbar);
        ImageView progressbar_done = getActivity().findViewById(R.id.progressbar_done);
        TextView title_text = getActivity().findViewById(R.id.title_text);
        TextView body_text = getActivity().findViewById(R.id.body_text);

        nextButton = getActivity().findViewById(R.id.next_button);
        backButton = getActivity().findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> switchToFragment(new Tutorial1Fragment(), getActivity().getSupportFragmentManager()));
        nextButton.setOnClickListener(v -> switchToFragment(new TutorialWiFiFragment(), getActivity().getSupportFragmentManager()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    progress.setIndeterminate(false);
                    progress.setProgress(progress.getMax());
                    progressbar_done.setVisibility(View.VISIBLE);
                    title_text.setText("Connected");
                    title_text.setGravity(Gravity.CENTER_HORIZONTAL);
                    body_text.setText("");
                    nextButton.setEnabled(true);
                });
            }
        }, 3000);
    }
}
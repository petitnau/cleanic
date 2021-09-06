package it.unica.informatica.cleanic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import it.unica.informatica.cleanic.tutorial.TutorialActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean tutorialDone = preferences.getBoolean("tutorial", false);
//        tutorialDone = false;
        Intent intent;
        if (tutorialDone) {
            intent = new Intent(this, HomeActivity.class);
        } else {
            intent = new Intent(this, TutorialActivity.class);

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
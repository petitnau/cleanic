package it.unica.informatica.cleanic;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        switchToFragment(new HomeFragment(), getSupportFragmentManager());

        navbar = findViewById(R.id.bottom_navigation);

        navbar.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.home_tab:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.routines_tab:
                    selectedFragment = new RoutinesFragment();
                    break;
                case R.id.remote_tab:
                    selectedFragment = new RemoteFragment();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            switchToFragment(selectedFragment, getSupportFragmentManager());
            return true;
        });
    }
}
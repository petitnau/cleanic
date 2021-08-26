package it.unica.informatica.cleanic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToFragment(new HomeFragment());

        navbar = findViewById(R.id.bottom_navigation);

        navbar.setOnItemSelectedListener(item -> {
            System.out.println("ciao " + item.getItemId());
            Fragment selectedFragment;
            switch(item.getItemId()) {
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
            switchToFragment(selectedFragment);
            return true;
        });
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
}
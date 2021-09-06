package it.unica.informatica.cleanic;

import static it.unica.informatica.cleanic.utils.Utils.switchToFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navbar;

    public void setRotation(float rotation) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navbar.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i);
            iconView.setRotation(rotation);
        }
    }

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
                    setRotation(0f);
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.routines_tab:
                    setRotation(0f);
                    selectedFragment = new RoutinesFragment();
                    break;
                case R.id.remote_tab:
                    setRotation(90f);
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
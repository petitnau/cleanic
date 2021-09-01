package it.unica.informatica.cleanic;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import it.unica.informatica.cleanic.utils.Routine;

// Instances of this class are fragments representing a single
// object in our collection.
public class TabObjectFragment extends Fragment
{
    public static final String ARG_OBJECT = "object";

    List<Routine> routines;
    List<MaterialCardView> cards;

    public TabObjectFragment(List<Routine> routines) {
        this.routines = routines;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_remote_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cards = new ArrayList<MaterialCardView>();
        cards.add(view.findViewById(R.id.favorite1).findViewById(R.id.card));
        cards.add(view.findViewById(R.id.favorite2).findViewById(R.id.card));

        for(int i = 0; i < cards.size(); i++) {
            MaterialCardView card = cards.get(i);
            if (i >= routines.size()) {
                card.setVisibility(View.INVISIBLE);
            }
            else {
                ((TextView) card.findViewById(R.id.favoriteText)).setText(routines.get(i).getName());
            }
        }
    }
}

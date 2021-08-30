package it.unica.informatica.cleanic.utils;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Locale;

import it.unica.informatica.cleanic.R;

public class Utils {

    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public static void toggleVisibility(View v) {
        if (v.getVisibility() == View.VISIBLE)
            v.setVisibility(View.INVISIBLE);
        else
            v.setVisibility(View.VISIBLE);
    }

    public enum Rooms {
        BEDROOM1("Cameretta", R.id.map_active_bedroom1),
        ANDITO("Andito", R.id.map_active_hallway),
        LIVING_ROOM("Soggiorno", R.id.map_active_living),
        BEDROOM2("Camera matrimoniale", R.id.map_active_bedroom2),
        KITCHEN("Cucina", R.id.map_active_kitchen),
        BATHROOM("Bagno", R.id.map_active_bathroom),
        VERANDA("Veranda", R.id.map_active_veranda),
        STORAGE_ROOM("Sgabuzzino", R.id.map_active_storage);

        private final String name;
        private final int resourceId;

        Rooms(String name, int resourceId) {
            this.name = name;
            this.resourceId = resourceId;
        }

        public int getResourceId() {
            return resourceId;
        }
    }


}

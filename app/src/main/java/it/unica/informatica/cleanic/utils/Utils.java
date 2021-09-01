package it.unica.informatica.cleanic.utils;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public static int halfCeil(int a) {
        if (a % 2 == 1)
            return a/2 + 1;
        else
            return a/2;
    }

    public static
    <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }
    public enum Room {
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

        Room(String name, int resourceId) {
            this.name = name;
            this.resourceId = resourceId;
        }
        public static Room colorToRoom(int color) {
            String pixel_hex = String.format("#%08x", color);

            switch (pixel_hex) {
                case "#ff6f009d":
                    return Room.BEDROOM1;
                case "#ff9b9b9b":
                    return Room.LIVING_ROOM;
                case "#ffffbb00":
                    return Room.KITCHEN;
                case "#ff0c00ff":
                    return Room.BEDROOM2;
                case "#ff00af00":
                    return Room.VERANDA;
                case "#ff01dcff":
                    return Room.BATHROOM;
                case "#ffff0004":
                    return Room.STORAGE_ROOM;
                case "#ff653d00":
                    return Room.ANDITO;
                default:
                    return null;
            }

        }

        public int getResourceId() {
            return resourceId;
        }
    }
}

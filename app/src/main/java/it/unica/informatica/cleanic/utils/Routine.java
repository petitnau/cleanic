package it.unica.informatica.cleanic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Routine {

    UUID uuid;
    //List<ImageView> map = new ArrayList<>();
    //MaterialButton[] weekdays = new MaterialButton[7];
    String routineName;
    String time;

    //Excluded from json
    private transient Context context;

    public Routine(UUID randomUUID, String routineName, String timeString, List<ImageView> map, MaterialButton[] weekdays, Context context) {
        this.uuid = randomUUID;
        this.routineName = routineName;
        this.time = timeString;
        //this.map = map;
        //this.weekdays = weekdays;
        this.context = context;
    }

    public void save() {
        SharedPreferences savedRoutines = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = savedRoutines.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        prefEditor.putString("Routine", json);
        System.out.println("Salvato!!!");
        prefEditor.apply();
    }


    public void saveRoutine() {
        SharedPreferences savedRoutines = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Set<String> newSet = new HashSet<String>(savedRoutines.getStringSet("Routine", new HashSet<String>()));
        newSet.add(json);
        savedRoutines.edit().putStringSet("Routine", newSet).apply();
    }

    public void readRoutines() {
        SharedPreferences savedRoutines = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> newSet = new HashSet<String>(savedRoutines.getStringSet("Routine", new HashSet<String>()));
        System.out.println("Set:" + newSet);
    }
    /*public static loadRoutine() {

    } */

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } */
}

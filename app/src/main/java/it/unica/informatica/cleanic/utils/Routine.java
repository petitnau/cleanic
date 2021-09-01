package it.unica.informatica.cleanic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Routine implements Comparable {

    UUID uuid;
    Boolean[] map;
    Boolean[] weekDays;
    public String name;
    public int hour;
    public int minutes;
    public boolean active;
    private boolean isFavorite;

    //Excluded from json
    private transient Context context;

    public Routine(int hour, int minutes, Context context) {
        this.uuid = UUID.randomUUID();
        this.name = "";
        this.hour = hour;
        this.minutes = minutes;
        this.map = Stream.generate(() -> Boolean.FALSE)
                .limit(Utils.Room.values().length)
                .toArray(Boolean[]::new);
        this.weekDays = Stream.generate(() -> Boolean.FALSE)
                .limit(7)
                .toArray(Boolean[]::new);
        this.context = context;
        this.active = false;
    }

    public static List<Routine> getRoutines(Context context) {
        SharedPreferences savedRoutines = PreferenceManager.getDefaultSharedPreferences(context);
        return Utils.asSortedList(savedRoutines.getStringSet("Routine", new HashSet<String>())).stream().map(s -> Routine.getRoutineFromJson(s, context)).collect(Collectors.toList());
    }

    public static List<Routine> getTodayRoutines(Context context) {
        return getRoutines(context).stream().filter(Routine::isActive).filter(Routine::isToday).collect(Collectors.toList());
    }


    public static List<Routine> getFavorites(Context context) {
        return getRoutines(context).stream().filter(Routine::isFavorite).collect(Collectors.toList());
    }

    public void update() {
        Set<Routine> savedRoutines = new HashSet<Routine>(getRoutines(context));
        savedRoutines.removeIf((x -> this.getUuid().equals(x.getUuid())));
        savedRoutines.add(this);
        SharedPreferences savedRoutinesShared = PreferenceManager.getDefaultSharedPreferences(context);
        savedRoutinesShared.edit().putStringSet("Routine", savedRoutines.stream().map(Routine::getJson).collect(Collectors.toSet())).apply();
    }

    public void remove() {
        Set<Routine> savedRoutines = new HashSet<Routine>(getRoutines(context));
        savedRoutines.removeIf((x -> this.getUuid().equals(x.getUuid())));
        SharedPreferences savedRoutinesShared = PreferenceManager.getDefaultSharedPreferences(context);
        savedRoutinesShared.edit().putStringSet("Routine", savedRoutines.stream().map(Routine::getJson).collect(Collectors.toSet())).apply();

    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Routine getRoutineFromJson(String json, Context context) {
        Gson gson = new Gson();
        Routine routine = gson.fromJson(json, Routine.class);
        routine.context = context;
        return routine;
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.setLenient(false);
        return calendar;
    }

    public boolean isToday() {
        Calendar calendar = Calendar.getInstance();
        System.out.print(calendar);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDays[(day-2) % 7];

    }

    public UUID getUuid() {
        return uuid;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean[] getMap() {
        return map;
    }

    public void setMap(Boolean[] map) {
        this.map = map;
    }

    public void setWeekDays(Boolean[] weekDays) {
        this.weekDays = weekDays;
    }

    public Boolean[] getWeekDays() {
        return weekDays;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isActive() {
        return active;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int compareTo(Object o) {
        Routine r = (Routine) o;
        if (this.getHour() < r.getHour())
            return -1;
        if (this.getHour() > r.getHour())
            return +1;
        if (this.getMinutes() < r.getMinutes())
            return -1;
        if (this.getMinutes() > r.getMinutes())
            return +1;
        return 0;
    }

    public String getWeekDaysText(){
        String weekText = "";
        int i = 0;
        if(weekDays[0]) {
            weekText += "Mon";
            i++;
        }
        if(weekDays[1]) {
            if(i > 0)
                weekText += " ";
            weekText += "Tue";
            i++;
        }
        if(weekDays[2]) {
            if(i > 0)
                weekText += " ";
            weekText += "Wed";
            i++;
        }
        if(weekDays[3]) {
            if(i > 0)
                weekText += " ";
            weekText += "Thu";
        }
        if(weekDays[4]) {
            if(i > 0)
                weekText += " ";
            weekText += "Fri";
            i++;
        }
        if(weekDays[5]) {
            if(i > 0)
                weekText += " ";
            weekText += "Sat";
            i++;
        }
        if(weekDays[6]) {
            if(i > 0)
                weekText += " ";
            weekText += "Sun";
            i++;
        }

        if(i == 6) {
            weekText = "Every Day";
        }
        return weekText;
    }
}

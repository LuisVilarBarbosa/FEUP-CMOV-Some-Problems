package pt.up.fe.up201405729.lunchlist;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class LunchApp extends Application {
    public List<Restaurant> restaurants = new ArrayList<>();
    public MainActivity.RestaurantAdapter adapter;
    public Restaurant current;
}

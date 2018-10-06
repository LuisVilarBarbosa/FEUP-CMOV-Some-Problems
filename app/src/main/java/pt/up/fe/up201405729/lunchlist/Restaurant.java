package pt.up.fe.up201405729.lunchlist;

import android.support.annotation.NonNull;

class Restaurant {
    private String name = "";
    private String address = "";
    private String type = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public @NonNull String toString() {
        return name;
    }
}

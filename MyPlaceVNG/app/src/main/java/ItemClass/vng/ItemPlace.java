package ItemClass.vng;

/**
 * Created by TranManhHung on 27-Jan-16.
 */
public class ItemPlace {
    String icon;
    //JsonElement id;
    String name;
    String place_id;
    String rate;
    String vicinity;
    String lat;
    String lng;
    String Khoangcach;


    public String getKhoangcach() {
        return Khoangcach;
    }

    public void setKhoangcach(String khoangcach) {
        Khoangcach = khoangcach;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

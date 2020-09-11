package cm.seeds.cuisineandroid.gaadleaderboard.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Learner {

    private String name;
    private int hours;
    private int score;
    private String country;
    private String badgeUrl;

    public Learner(JSONObject object){
        try {
            name = object.getString("name");
            country = object.getString("country");
            badgeUrl = object.getString("badgeUrl");
            if(object.has("score")){
                score = object.getInt("score");
            }

            if(object.has("hours")){
                hours = object.getInt("hours");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Learner(String name, int hours, int score, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public int getScore() {
        return score;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }
}

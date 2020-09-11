package cm.seeds.cuisineandroid.gaadleaderboard.Helper;

import java.util.List;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    public static final String ENDPOINT = "https://gadsapi.herokuapp.com";


    @GET("/api/skilliq")
    Call<List<Learner>> getIQLearners();

    @GET("/api/hours")
    Call<List<Learner>> getHoursLearners();

    @FormUrlEncoded
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    Call<Void> submitProject(@Field("entry.1824927963") String emailAddress, @Field("entry.1877115667") String firstName, @Field("entry.2006916086") String lastName, @Field("entry.284483984") String githubLink);

}

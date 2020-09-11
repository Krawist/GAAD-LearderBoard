package cm.seeds.cuisineandroid.gaadleaderboard.Helper;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;
import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.ProjectSubmission;
import cm.seeds.cuisineandroid.gaadleaderboard.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cm.seeds.cuisineandroid.gaadleaderboard.Helper.RetrofitInterface.ENDPOINT;

public class ConnectionToServer {

    private final Context context;
    private final OperationTerminated listener;
    private OkHttpClient okHttpClient;
    private RetrofitInterface client;
    private  String baseUrl;

    public ConnectionToServer(Context context, OperationTerminated listener) {
        this.context = context;
        this.listener = listener;
        baseUrl = ENDPOINT;
        //initialiseWaitingDialog();
        initCom();
    }

    public ConnectionToServer(Context context, OperationTerminated listener, String baseUrl) {
        this.context = context;
        this.listener = listener;
        this.baseUrl = baseUrl;
        //initialiseWaitingDialog();
        initCom();
    }

    private void initCom() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .build();
/*                        okhttp3.Response response = chain.proceed(newRequest);
                        Log.e(TAG," data square transaction"+response.body().string());*/
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        //Log.e(TAG,"token: "+token);
        client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitInterface.class);
    }

    public void loadData(int typeOfLearder){
        Call<List<Learner>> call = null;
        if(typeOfLearder==Const.LEARNER_LEARDER_BOARD){
            call = client.getHoursLearners();
        }else{
            call = client.getIQLearners();
        }
        call.enqueue(new Callback<List<Learner>>() {
            @Override
            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {
                List<Learner> learners = response.body();
                int code = response.code();
                if (learners != null) {
                    listener.onSuccess(code,learners);
                }else{
                    listener.onError(code,context.getString(R.string.erreur));
                }
            }

            @Override
            public void onFailure(Call<List<Learner>> call, Throwable t) {

            }
        });
    }

    public void submitProject(ProjectSubmission projectSubmission){
        Call<Void> call = client.submitProject(projectSubmission.getEmailAddress(),projectSubmission.getFirstName(),projectSubmission.getLastName(),projectSubmission.getGithubLink());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if(code==200){
                    listener.onSuccess(code,response.body());
                }else{
                    listener.onError(code,context.getString(R.string.erreur));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(500,context.getString(R.string.erreur));
            }
        });
    }

}

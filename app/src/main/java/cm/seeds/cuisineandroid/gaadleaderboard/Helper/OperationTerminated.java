package cm.seeds.cuisineandroid.gaadleaderboard.Helper;

import java.util.List;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;

public interface OperationTerminated {
    void onSuccess(int code, Object body);

    void onError(int code, String message);
}

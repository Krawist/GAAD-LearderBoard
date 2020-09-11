package cm.seeds.cuisineandroid.gaadleaderboard.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;
import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.ProjectSubmission;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.ConnectionToServer;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.OperationTerminated;
import cm.seeds.cuisineandroid.gaadleaderboard.R;

public class SubmissionActivity extends AppCompatActivity {

    private static final String SUBMISSION_BASE_URL = "https://docs.google.com/forms/d/e/";
    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextLastName;
    private TextInputEditText editTextEmailAddress;
    private TextInputEditText editTextGithubLink;
    private Button submitButton;
    private LinearLayout formRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        initialiseViews();

        addActionsOnviews();

    }

    private void addActionsOnviews() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllFieldIsCorrect()){
                    formRoot.setVisibility(View.GONE);
                    final Dialog dialog = new Dialog(SubmissionActivity.this);
                    dialog.setContentView(R.layout.confirm_submission_dialog_layout);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.findViewById(R.id.button_yees).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            submit();
                        }
                    });

                    dialog.findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            formRoot.setVisibility(View.VISIBLE);
                        }
                    });

                    dialog.show();
                }
            }
        });
    }

    private void submit() {
        ProjectSubmission projectSubmission = new ProjectSubmission(editTextFirstName.getText().toString(),
                editTextLastName.getText().toString(),
                editTextEmailAddress.getText().toString(),
                editTextGithubLink.getText().toString());
        final Dialog loading = new Dialog(this);
        loading.setContentView(new ProgressBar(this));
        loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loading.setCancelable(false);
        loading.show();
        new ConnectionToServer(SubmissionActivity.this, new OperationTerminated() {
            @Override
            public void onSuccess(int code, Object body) {
                loading.dismiss();
                if(code==200){
                    showResult(true);
                }
            }

            @Override
            public void onError(int code, String message) {
                loading.dismiss();
                showResult(false);
            }
        },SUBMISSION_BASE_URL).submitProject(projectSubmission);

        //showResult(false);
    }

    private boolean AllFieldIsCorrect() {
        boolean allIsCorrect = true;

        if(TextUtils.isEmpty(editTextEmailAddress.getText().toString())){
            allIsCorrect = false;
            editTextEmailAddress.setError("Veuillez fournir l'adresse email");
        }else{
            editTextEmailAddress.setError(null);
        }

        if(TextUtils.isEmpty(editTextFirstName.getText().toString())){
            allIsCorrect = false;
            editTextFirstName.setError("Veuillez fournir votre nom");
        }else{
            editTextFirstName.setError(null);
        }

        if(TextUtils.isEmpty(editTextLastName.getText().toString())){
            allIsCorrect = false;
            editTextLastName.setError("Veuillez fournir votre prénom");
        }else{
            editTextLastName.setError(null);
        }

        if(TextUtils.isEmpty(editTextGithubLink.getText().toString())){
            allIsCorrect = false;
            editTextGithubLink.setError("Veuillez fournir l'adresse github de votre projet");
        }else{
            editTextGithubLink.setError(null);
        }

        return allIsCorrect;
    }

    private void showResult(boolean allIsCorrect) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.submission_result_dialog_layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if(allIsCorrect){
            ((TextView)dialog.findViewById(R.id.textview_message)).setText(R.string.submission_successfull);
            ((ImageView)dialog.findViewById(R.id.image)).setColorFilter(getResources().getColor(R.color.good_result_color));
        }else{
            ((TextView)dialog.findViewById(R.id.textview_message)).setText(R.string.submission_not_successfull);
            ((ImageView)dialog.findViewById(R.id.image)).setColorFilter(getResources().getColor(R.color.bad_result_color));
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                formRoot.setVisibility(View.VISIBLE);
            }
        });

        dialog.show();
    }

    private void initialiseViews() {

        editTextFirstName = findViewById(R.id.editetext_first_name);
        editTextLastName = findViewById(R.id.editetext_last_name);
        editTextEmailAddress = findViewById(R.id.editetext_email_address);
        editTextGithubLink = findViewById(R.id.editetext_github_link);
        submitButton = findViewById(R.id.button_submit_button);
        formRoot = findViewById(R.id.form_root);

    }

    public void navigateBack(View view) {
        onBackPressed();
    }
}
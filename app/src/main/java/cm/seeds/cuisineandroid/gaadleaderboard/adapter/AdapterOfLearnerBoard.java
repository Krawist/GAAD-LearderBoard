package cm.seeds.cuisineandroid.gaadleaderboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;
import cm.seeds.cuisineandroid.gaadleaderboard.R;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.Const;

public class AdapterOfLearnerBoard extends RecyclerView.Adapter<AdapterOfLearnerBoard.SkillIQViewHolder> {

    private Context context;
    private List<Learner> learners;
    private int typeOflearners;

    public AdapterOfLearnerBoard(Context context, List<Learner> learners, int typeOflearners) {
        this.context = context;
        this.learners = learners;
        this.typeOflearners = typeOflearners;
    }

    public void setList(List<Learner> learners){
        this.learners = learners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SkillIQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillIQViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill_iq_learder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQViewHolder holder, int position) {
        holder.bindData(learners.get(position),position,typeOflearners);
    }

    @Override
    public int getItemCount() {
        if(learners!=null){
            return learners.size();
        }
        return 0;
    }

    static class SkillIQViewHolder extends RecyclerView.ViewHolder{

        private final TextView learnerName;
        private final TextView learderDetails;
        private final ImageView imageView;

        public SkillIQViewHolder(View view){
            super(view);
            learnerName = view.findViewById(R.id.textview_name_learder);
            learderDetails = view.findViewById(R.id.textview_learder_details);
            imageView = view.findViewById(R.id.image_type_learder);
        }

        public void bindData(Learner learner, int position, int typeOfLearners){
            learnerName.setText(learner.getName());

            switch (typeOfLearners){
                case Const.LEARNER_LEARDER_BOARD :
                    learderDetails.setText(learner.getHours() + " learning hours, " + learner.getCountry());
                    break;

                case Const.SKILL_IQ_LEARDER_BOARD:
                    learderDetails.setText(learner.getScore() + " skill IQ Score, " + learner.getCountry());
                    break;
            }

            Glide.with(imageView).load(learner.getBadgeUrl()).into(imageView);

        }

    }
}

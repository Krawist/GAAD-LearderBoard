package cm.seeds.cuisineandroid.gaadleaderboard.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import cm.seeds.cuisineandroid.gaadleaderboard.Helper.Const;
import cm.seeds.cuisineandroid.gaadleaderboard.fragment.LeadearBoardFragment;

public class ViewPagerLeaderAdapter extends FragmentStatePagerAdapter {

    public ViewPagerLeaderAdapter(FragmentManager fragmentManager){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return LeadearBoardFragment.newInstance(Const.LEARNER_LEARDER_BOARD);

            case 1:
                return LeadearBoardFragment.newInstance(Const.SKILL_IQ_LEARDER_BOARD);

        }

        return LeadearBoardFragment.newInstance(Const.LEARNER_LEARDER_BOARD);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return "Learning Leaders";

            case 1:
                return "Skill IQ Learders";

        }

        return "Learning Leaders";
    }
}

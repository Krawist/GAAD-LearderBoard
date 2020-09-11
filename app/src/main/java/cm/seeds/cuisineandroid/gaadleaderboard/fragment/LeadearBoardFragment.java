package cm.seeds.cuisineandroid.gaadleaderboard.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cm.seeds.cuisineandroid.gaadleaderboard.DataModel.Learner;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.Const;
import cm.seeds.cuisineandroid.gaadleaderboard.R;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.ConnectionToServer;
import cm.seeds.cuisineandroid.gaadleaderboard.Helper.OperationTerminated;
import cm.seeds.cuisineandroid.gaadleaderboard.adapter.AdapterOfLearnerBoard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeadearBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeadearBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE_OF_LEARDER = "type_of_learner";

    // TODO: Rename and change types of parameters
    private int typeOfLearder;
    private RecyclerView recyclerViewLeaderList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdapterOfLearnerBoard adapterOfLearnerBoard;
    private List<Learner> learners;

    public LeadearBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param typeOfLearder Parameter 1.
     * @return A new instance of fragment LeadearBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeadearBoardFragment newInstance(int typeOfLearder) {
        LeadearBoardFragment fragment = new LeadearBoardFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_OF_LEARDER, typeOfLearder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeOfLearder = getArguments().getInt(TYPE_OF_LEARDER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leadear_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseViews(view);

        recyclerViewLeaderList.setLayoutManager(new LinearLayoutManager(requireContext()));

        addActionsToViews();

        if(learners!=null && !learners.isEmpty()){
            configureAdapter();
        }else{
            refreshData();
        }
    }

    private void addActionsToViews() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {

        swipeRefreshLayout.setRefreshing(true);

        new ConnectionToServer(requireContext(), new OperationTerminated() {
            @Override
            public void onSuccess(int code, Object body) {
                if(body!=null){
                    LeadearBoardFragment.this.learners = (List<Learner>)body;

                    sortData();

                    configureAdapter();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(int code, String message) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }).loadData(typeOfLearder);
    }

    private void sortData() {
        switch (typeOfLearder){

            case Const.LEARNER_LEARDER_BOARD:
                Collections.sort(learners, new Comparator<Learner>() {
                    @Override
                    public int compare(Learner learner1, Learner learner2) {
                        return learner1.getHours()>=learner2.getHours()?1:0;
                    }
                });
                break;

            case Const.SKILL_IQ_LEARDER_BOARD:
            Collections.sort(learners, new Comparator<Learner>() {
                @Override
                public int compare(Learner learner1, Learner learner2) {
                    return learner1.getScore()>=learner2.getScore()?1:0;
                }
            });
            break;

        }
    }

    private void configureAdapter() {
        if(adapterOfLearnerBoard!=null){
            adapterOfLearnerBoard.setList(learners);
        }else{
            adapterOfLearnerBoard = new AdapterOfLearnerBoard(requireContext(),learners,typeOfLearder);
        }

        if(recyclerViewLeaderList.getAdapter()!=adapterOfLearnerBoard){
            recyclerViewLeaderList.setAdapter(adapterOfLearnerBoard);
        }
    }

    private void initialiseViews(View view) {
        recyclerViewLeaderList = view.findViewById(R.id.recyclerview_list_learder);
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
    }
}
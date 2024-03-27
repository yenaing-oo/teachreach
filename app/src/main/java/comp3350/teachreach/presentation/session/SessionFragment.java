package comp3350.teachreach.presentation.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.google.android.material.tabs.TabLayout;

import comp3350.teachreach.databinding.FragmentSessionsBinding;
import comp3350.teachreach.presentation.utils.TRViewModel;


public
class SessionFragment extends Fragment {
    private FragmentSessionsBinding binding;
    private SlidingPaneLayout       slidingPaneLayout;
    private TabLayout               tabLayout;
    private TRViewModel             viewModel;
    private boolean                 isTutor;

    public SessionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        isTutor   = Boolean.TRUE.equals(viewModel.getIsTutor().getValue());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding           = FragmentSessionsBinding.inflate(inflater, container, false);
        slidingPaneLayout = binding.sessionsFragment;
        tabLayout         = binding.sessionTabs;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpTabBar();
    }

    private void setUpTabBar() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0 -> Toast.makeText(requireContext(), "Pending", Toast.LENGTH_SHORT)
                                   .show();
                    case 1 -> Toast.makeText(requireContext(), "Upcoming", Toast.LENGTH_SHORT)
                                   .show();
                    case 2 -> Toast.makeText(requireContext(), "Previous", Toast.LENGTH_SHORT)
                                   .show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}

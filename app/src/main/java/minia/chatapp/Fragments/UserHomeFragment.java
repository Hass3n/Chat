package minia.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import minia.chatapp.R;
import minia.chatapp.adapters.ViewPagerAdapter;

public class UserHomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long facultyId;

    public UserHomeFragment(long facultyId) {
        // Required empty public constructor
        this.facultyId = facultyId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        tabLayout = view.findViewById(R.id.tabMenu);
        viewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new employeeFragment(facultyId), "Employee");
        adapter.addFragment(new StaffFragment(facultyId), "Staff");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

}

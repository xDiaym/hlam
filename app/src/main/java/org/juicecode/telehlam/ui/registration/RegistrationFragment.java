package org.juicecode.telehlam.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.NavigationViewLocker;

public class RegistrationFragment  extends Fragment {
    ImageButton goBackButton;
    ExtendedFloatingActionButton floatingActionButton;
    RegistrationFragment registrationFragment = this;



    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.registration_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.login_registration);
        NavigationViewLocker navigationViewLocker = (NavigationViewLocker)view.getContext();
        navigationViewLocker.lockDrawer();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)v.getContext();
                fragmentManagerSimplifier.remove("registration");
            }
        });
        goBackButton = view.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardManager.hideKeyboard(getActivity());
                getActivity().onBackPressed();

            }
        });
        return view;
    }
}

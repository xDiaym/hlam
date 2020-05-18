package org.juicecode.telehlam.ui.fingerprint;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FingerPrintChecker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;
import org.juicecode.telehlam.utils.SnackbarShower;


public class RequestFingerPrintFragment extends Fragment {
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private LinearLayout layout;
    private SharedPreferencesRepository repository;

    public RequestFingerPrintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_request_finger_print, container, false);

        layout = view.findViewById(R.id.layout);
        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);

        Button deleteFingerPrint = view.findViewById(R.id.deleteFingerPrint);
        deleteFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.DELETING_FINGERPRINT, getContext(), new SnackbarShower(layout));
                checker.checkAuth(repository);
            }
        });

        deleteFingerPrint.setVisibility(View.GONE);
        TextView warning = view.findViewById(R.id.warning);
        fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();
        repository = new SharedPreferencesRepository(getContext());

        if (repository.getFingerPrint()) {
            warning.setText(R.string.hasFingerprint);
            yesButton.setVisibility(View.GONE);
            noButton.setVisibility(View.GONE);
            deleteFingerPrint.setVisibility(View.VISIBLE);
        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.ADDING_FINGERPRINT, getContext(), new SnackbarShower(layout));
                checker.checkAuth(repository);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManagerSimplifier.addFragment(R.id.nav_home);
            }
        });

        return view;
    }

}

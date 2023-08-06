package mg.itu.projetm1.vues;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mg.itu.projetm1.R;
import mg.itu.projetm1.session.SessionManager;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.toggleToolbarVisibility(false); // Hide the toolbar
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.toggleToolbarVisibility(true); // Show the toolbar
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView appVersionTextView = view.findViewById(R.id.app_version_text);
        appVersionTextView.setText(getAppVersion());
    }

    private String getAppVersion() {
        String version = "";
        try {
            PackageInfo packageInfo = requireActivity().getPackageManager().getPackageInfo(requireActivity().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
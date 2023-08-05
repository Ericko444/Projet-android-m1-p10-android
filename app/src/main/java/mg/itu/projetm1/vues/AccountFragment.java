package mg.itu.projetm1.vues;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import mg.itu.projetm1.R;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

//        sessionManager = new SessionManager(getActivity());
//
//        HashMap<String, String> user = sessionManager.getUserDetail();
//        String name = user.get(SessionManager.NAME);
//        String email = user.get(SessionManager.EMAIL);
//
//        TextView nameTextView = view.findViewById(R.id.name);
//        TextView emailTextView = view.findViewById(R.id.email);
//
//        nameTextView.setText("Name: " + name);
//        emailTextView.setText("Email: " + email);

        return view;
    }
}
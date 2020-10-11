package com.example.rentals.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.rentals.Activity.ForgotPassword;
import com.example.rentals.Activity.MainActivity;
import com.example.rentals.Activity.Postadd;
import com.example.rentals.Activity.ProfileDetails;
import com.example.rentals.R;
import com.example.rentals.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link MyAccountFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MyAccountFragment extends PreferenceFragmentCompat {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CircleImageView ivProfile;
    TextView tvPersonName;
    LinearLayout llProfile;
    FirebaseUser fUser;
    User user = null;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirebaseFirestore;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
    // TODO: Rename and change types and number of parameters
//    public static MyAccountFragment newInstance(String param1, String param2) {
//        MyAccountFragment fragment = new MyAccountFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        auth = FirebaseAuth.getInstance();
//        mFirebaseFirestore = FirebaseFirestore.getInstance();
//
//        fUser = auth.getCurrentUser();
//
//
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Preference profile = findPreference("profile");
        Preference security = findPreference("security");
        Preference newpost = findPreference("newpost");
        Preference manage = findPreference("manage");
        Preference logout = findPreference("logout");


        profile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(getActivity(), ProfileDetails.class);
                startActivity(i);
                return true;
            }
        });
        security.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(getActivity(), ForgotPassword.class);
                startActivity(i);
                return true;
            }
        });
        newpost.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(getActivity(), Postadd.class);
                startActivity(i);
                return true;
            }
        });
        manage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(getActivity(), ProfileDetails.class);
                startActivity(i);
                return true;
            }
        });
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);

                startActivity(i);

                return true;
            }
        });
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_myaccount, container, false);
//
//        ivProfile = v.findViewById(R.id.profile_image);
//        tvPersonName = v.findViewById(R.id.tv_person_name);
//        llProfile = v.findViewById(R.id.ll_profile);
//
//
//        if (fUser != null) {
//            if (fUser.getEmail() != null) {
//                getUserData(fUser.getEmail());
//            } else {
//                Toast.makeText(getActivity().getApplicationContext(), "No user email exists!", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//        return v;
//    }
//
//
//    private void getUserData(final String email) {
//
//        // final List<User> userList = new ArrayList<User>();
//
//
//        mFirebaseFirestore.collection("User").document(email).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()){
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            if (documentSnapshot.exists()) {
//                                user = documentSnapshot.toObject(User.class);
//                                if (user != null) {
//                                    tvPersonName.setText(user.getName());
//                                    llProfile.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            Intent intent = new Intent(getActivity(), ProfileDetails.class);
//                                            startActivity(intent);
//                                        }
//                                    });
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), "No user found with email!!!", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "Error getting user data!!!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
}
package com.example.rentals.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rentals.Adapters.WishlistAdapter;
import com.example.rentals.Models.WishlistModel;
import com.example.rentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment {

    FirebaseFirestore db;
    /**
     * variable declaration authenication object
     */
    private FirebaseAuth auth;
    /**
     * variable declarationfor current user
     */
    private FirebaseUser curUser;
    /**
     * variable declaration for recyclerview
     */
    RecyclerView wishlistRecycler;
    /**
     * variable declaration for list adapter
     */
    WishlistAdapter wishAdapter;
    FirebaseStorage storage;
    StorageReference storageReference;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public WishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_wishlist, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // auth = FirebaseAuth.getInstance();
        wishlistRecycler =view.findViewById(R.id.wishlist_recycler);
        final ArrayList<WishlistModel> wishlist = new ArrayList<>();

      db=FirebaseFirestore.getInstance();
      // curUser=auth.getCurrentUser();
       // String userId=curUser.getUid();

        db.collection("Wishlist")
                .whereEqualTo("UserId", "jjUgo0MRvbgiokGHNAB0LTV8AxI2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());
                                System.out.println(document.getId() + " => " + document.getData());
                                String apartmentId= (String) document.getData().get("ApartmentId");

                               // Toast.makeText(getActivity().getApplicationContext(), ""+apartmentId, Toast.LENGTH_SHORT).show();
                                Log.d("", ""+apartmentId);
                                getApartmentDetails(apartmentId,wishlist);

                                // productsList.add(new CartModel(cid,  name, quantity, price,myUri));





                            }
                            setWishlistRecycler(wishlist);



                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getApartmentDetails(final String apartmentId, final ArrayList<WishlistModel> wishlist) {
        DocumentReference docRef = db.collection("Apartment").document(apartmentId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        String price,location,type,bedroom,bathroom;
                        price=(String)document.getData().get("Amount");
                        location=(String)document.getData().get("Address");
                        type=(String)document.getData().get("Unit");
                        bedroom=(String)document.getData().get("Bedroom");
                        bathroom=(String)document.getData().get("Bathroom");
                        getImage(apartmentId,price,type,location,bedroom,bathroom,wishlist);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }

    private void getImage(final String apartmentId, final String price, final String type, final String location, final String bedroom, final String bathroom, final ArrayList<WishlistModel> wishlist) {
        storageReference = storage.getInstance().getReference();
        storageReference.child("images/"+apartmentId+"/0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                wishlist.add(new WishlistModel(apartmentId,  price, bedroom, bathroom,location,type,uri));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    private void setWishlistRecycler(ArrayList<WishlistModel> wishlist) {

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        wishlistRecycler.setLayoutManager(layoutManager);
        wishAdapter = new WishlistAdapter(getActivity().getApplicationContext(), wishlist);
        wishlistRecycler.setAdapter(wishAdapter);

    }

}
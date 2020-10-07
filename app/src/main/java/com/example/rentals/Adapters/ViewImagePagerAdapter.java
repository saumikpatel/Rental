package com.example.rentals.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.rentals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewImagePagerAdapter extends PagerAdapter {
    FirebaseStorage storage;
    StorageReference storageReference;
    private Context context;
    private ArrayList<Uri> images= new ArrayList<Uri>();


    public ViewImagePagerAdapter(Context context, ArrayList<Uri> images) {
        this.context = context;
        this.images=images;



    }

    @Override
    public int getCount() {

        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
//        final String id = "NoF5tUfg1f0HM4vZM2RJ";
//
//        StorageReference listRef = storage.getInstance().getReference().child("images/" + id);
//
//        listRef.listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        for (StorageReference prefix : listResult.getPrefixes()) {
//                            // All the prefixes under listRef.
//                            // You may call listAll() recursively on them.
//                        }
//
//                        for (StorageReference item : listResult.getItems()) {
//                            // All the items under listRef.
//                            storageReference = storage.getInstance().getReference();
//                            String location = item.toString();
//                            String image = location.substring(location.length() - 1);
//                            System.out.println(image);
//                            storageReference = storage.getInstance().getReference();
//                            storageReference.child("images/" + id + "/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    // Got the download URL for 'users/me/profile.png'
//                                    //  ImageView image = (ImageView) dialog.findViewById(R.id.dialogimage);
//                                    // Picasso.get().load(uri).resize(120, 120).into(image);
//                                    System.out.println(images.size() + "maa");
//                                    images.add(uri);
//                                    notifyDataSetChanged();
//
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle any errors
//                                }
//                            });
//
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Uh-oh, an error occurred!
//                    }
//                });

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = view.findViewById(R.id.img1);
       // imageView.setImageURI(images.get(position));
        Picasso.get().load(images.get(position)).resize(120, 120).into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

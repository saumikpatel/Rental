package com.example.rentals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentals.Models.WishlistModel;
import com.example.rentals.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    Context context;
    List<WishlistModel> wishlist;

    public WishlistAdapter(Context context,List<WishlistModel> wishlist) {
        this.context = context;
        this.wishlist=wishlist;

    }

    @NonNull
    @Override
    public WishlistAdapter.WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_wishlistactivity, parent, false);
        return new WishlistAdapter.WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.WishlistViewHolder holder, int position) {
        Picasso.get().load(wishlist.get(position).getImage()).into(holder.aptimage);
        holder.price.setText(wishlist.get(position).getPrice());
        holder.type.setText(wishlist.get(position).getType());
        holder.bedroom.setText(wishlist.get(position).getBedroom());
        holder.bathroom.setText(wishlist.get(position).getBathroom());
        holder.location.setText(wishlist.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }
    public static final class WishlistViewHolder extends RecyclerView.ViewHolder{

        ImageView aptimage;
        TextView price, bedroom, bathroom,location,type;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            aptimage = itemView.findViewById(R.id.wishlistimage);
            price = itemView.findViewById(R.id.wishlistPrice);
            bedroom = itemView.findViewById(R.id.bednumberwishlist);
            bathroom = itemView.findViewById(R.id.bathnumberwishlist);
            location = itemView.findViewById(R.id.addresswishlist);
            type = itemView.findViewById(R.id.apartmentwishlist);



        }
    }
}

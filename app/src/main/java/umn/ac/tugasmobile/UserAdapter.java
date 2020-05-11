package umn.ac.tugasmobile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    List<User> mUsers;
    public UserAdapter(Context mContext, List<User> mUsers){
        this.mUsers= mUsers;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.tvDisplayName.setText(user.getDisplayName());
        if(user.getProfilePic().equals("default")){
            holder.ivPicture.setImageResource(R.drawable.blank);
        }
        else{
            Glide.with(mContext).load(user.getProfilePic()).into(holder.ivPicture);
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPicture;
        TextView tvDisplayName;
        ImageView heartFavorite;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user=mUsers.get(getAdapterPosition());
                    Intent i=new Intent(mContext, DetailUser.class);
                    i.putExtra("angkatan",user.getAngkatan());
                    i.putExtra("displayname",user.getDisplayName());
                    i.putExtra("nama",user.getNamalengkap());
                    i.putExtra("pp",user.getProfilePic());
                    i.putExtra("nim",user.getNim());
                    i.putExtra("status",user.getStatusPekerjaan());
                    i.putExtra("pekerjaan",user.getPekerjaan());
                    i.putExtra("nohp",user.getNomorhp());
                    i.putExtra("domisili",user.getDomisili());
                    mContext.startActivity(i);
                }
            });

        }
    }



}

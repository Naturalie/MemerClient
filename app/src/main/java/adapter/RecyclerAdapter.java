package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.memer.LoginActivity;
import com.example.memer.MainActivity;
import com.example.memer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import connection.handlers.Host;
import controllers.AddScore;
import entities.Meme;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Meme> memeList;
    private Context context;
    private Activity activity;
    public RecyclerAdapter(List<Meme> memeList, Context context ){
        this.memeList = memeList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Meme meme = memeList.get(position);
        holder.memeTitle.setText(meme.getImageTitle());
        holder.scoreText.setText(meme.getImageScore());
        holder.hotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddScore as = new AddScore(LoginActivity.preferences, meme.getImageName());
                if (as.getSucceed() == 0) {
                    holder.scoreText.setText(Integer.parseInt(holder.scoreText.getText().toString().split(" ")[0]) + 1 + " points");
                    Toast.makeText(context, "It's gettin' even hotter ;)", Toast.LENGTH_SHORT).show();
                } else if (as.getSucceed() == 1) {
                    Toast.makeText(context, "You have already liked that!", Toast.LENGTH_SHORT).show();
                } else if (as.getSucceed() == 2) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    Toast.makeText(context, "Token is no longer valid!", Toast.LENGTH_SHORT).show();
                    ((Activity)context).overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    Toast.makeText(context, "Server error occured!", Toast.LENGTH_SHORT).show();
                    ((Activity)context). overridePendingTransition(0, 0);
                }
            }
        });


        Picasso.get()
                .load("http://" + Host.IP + ":8080/images/" + meme.getImageName())
                .error(R.drawable.hotmeme)
                .placeholder(R.drawable.hotmeme)
                .fit()
                .into(holder.memeImg);

    }

    @Override
    public int getItemCount(){
        return memeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView memeImg;
        TextView memeTitle, scoreText;
        Button hotbutton;
        public MyViewHolder (View itemView){
            super(itemView);

            memeImg = itemView.findViewById(R.id.memeView);
            memeTitle = itemView.findViewById(R.id.memeTitle);
            scoreText = itemView.findViewById(R.id.scoreTextRecycler);
            hotbutton = itemView.findViewById(R.id.hotmemeButton2);


        }

    }

    public void addMemes(List<Meme> memes){
        Log.e("addmemes: ", "dziala");
        for(Meme me: memes){
            memeList.add(me);
        }

    }

}

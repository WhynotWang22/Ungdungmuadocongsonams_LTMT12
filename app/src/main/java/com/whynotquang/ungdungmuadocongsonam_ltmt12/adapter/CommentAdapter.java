package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholoder> {
    List<Comment> commentList;
    Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_get_comment, parent, false);
        return new Viewholoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.Viewholoder holder, int position) {
      Comment comment = commentList.get(position);
      holder.tvNameCmt.setText(comment.getUserName());
      holder.edGetcomment.setText(comment.getCommentDes());
      holder.ratingbar.setRating(comment.getRatingStar());
      Glide.with(context).load(comment.getUserIMG()).error(R.drawable.mau).into(holder.imgComment);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class Viewholoder extends RecyclerView.ViewHolder {
        private CircleImageView imgComment;
        private TextView tvNameCmt;
        private RatingBar ratingbar;
        private TextView edGetcomment;

        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            imgComment = (CircleImageView) itemView.findViewById(R.id.img_comment);
            tvNameCmt = (TextView) itemView.findViewById(R.id.tv_name_cmt);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            edGetcomment = (TextView) itemView.findViewById(R.id.ed_getcomment);
        }
    }
}

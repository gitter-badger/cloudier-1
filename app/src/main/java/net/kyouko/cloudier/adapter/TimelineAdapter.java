package net.kyouko.cloudier.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import net.kyouko.cloudier.R;
import net.kyouko.cloudier.model.Timeline;
import net.kyouko.cloudier.model.Tweet;
import net.kyouko.cloudier.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for {@link RecyclerView} to display a list of tweets.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TweetViewHolder> {

    private Context context;
    private Timeline timeline;


    public TimelineAdapter(Context context, Timeline timeline) {
        this.context = context;
        this.timeline = timeline;
    }


    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_tweet_card, parent, false);
        return new TweetViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = timeline.tweets.get(position);

        holder.avatar.setImageURI(ImageUtil.getInstance(context).parseImageUrl(tweet.avatarUrl));
        holder.nickname.setText(tweet.nickname);
        holder.username.setText(context.getString(R.string.text_pattern_username, tweet.username));
        holder.content.setText(tweet.content);

        if (tweet.imageUrls != null && !tweet.imageUrls.isEmpty()) {
            holder.image.setImageURI(
                    ImageUtil.getInstance(context).parseImageUrl(tweet.imageUrls.get(0))
            );
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }

        if (tweet.sourceTweet != null) {
            holder.sourceCard.setVisibility(View.VISIBLE);
            holder.sourceNickname.setText(tweet.sourceTweet.nickname);
            holder.sourceContent.setText(tweet.sourceTweet.content);

            if (tweet.sourceTweet.imageUrls != null && !tweet.sourceTweet.imageUrls.isEmpty()) {
                holder.sourceImage.setImageURI(ImageUtil.getInstance(context).
                        parseImageUrl(tweet.sourceTweet.imageUrls.get(0)));
                holder.sourceImage.setVisibility(View.VISIBLE);
            } else {
                holder.sourceImage.setVisibility(View.GONE);
            }
        } else {
            holder.sourceCard.setVisibility(View.GONE);
        }

        holder.commentCount.setText(String.valueOf(tweet.commentCount));
        holder.commentCount.setVisibility((tweet.commentCount > 0) ? View.VISIBLE : View.GONE);
        holder.retweetCount.setText(String.valueOf(tweet.retweetCount));
        holder.retweetCount.setVisibility((tweet.retweetCount > 0) ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return timeline.tweets.size();
    }


    public class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card) CardView card;
        @BindView(R.id.avatar) SimpleDraweeView avatar;
        @BindView(R.id.nickname) TextView nickname;
        @BindView(R.id.username) TextView username;
        @BindView(R.id.content) TextView content;
        @BindView(R.id.image) SimpleDraweeView image;
        @BindView(R.id.source_card) CardView sourceCard;
        @BindView(R.id.source_wrapper) View sourceWrapper;
        @BindView(R.id.source_image) SimpleDraweeView sourceImage;
        @BindView(R.id.source_nickname) TextView sourceNickname;
        @BindView(R.id.source_content) TextView sourceContent;
        @BindView(R.id.button_comment) View commentButton;
        @BindView(R.id.comment_count) TextView commentCount;
        @BindView(R.id.button_retweet) View retweetButton;
        @BindView(R.id.retweet_count) TextView retweetCount;
        @BindView(R.id.button_share) View shareButton;
        @BindView(R.id.button_menu) View menuButton;

        public TweetViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}

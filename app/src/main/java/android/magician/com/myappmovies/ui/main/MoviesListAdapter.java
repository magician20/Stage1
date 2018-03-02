package android.magician.com.myappmovies.ui.main;

import android.content.Context;
import android.magician.com.myappmovies.R;
import android.magician.com.myappmovies.data.model.Movie;
import android.magician.com.myappmovies.utilities.NetworkInfos;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Magician on 2/23/2018.
 */

class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListAdapterViewHolder> {
    private final Context mContext;
    private List<Movie> mMoviesList;

    MoviesListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MoviesListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.movie_card_hover;  //
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new MoviesListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesListAdapterViewHolder holder, int position) {
        //get data
        Movie movie = mMoviesList.get(position);
        //movie poster string file path
        String filePath = movie.getMoviePoster().substring(1);
        //Poster  image //i got problem when i build the uri for image
        //Glide.with(mContext).load(NetworkInfos.getImageUrlString(filePath)).into(holder.mMoviePoster);
        //Picasso fit()
        if (NetworkInfos.isNetworkConnectionAvailable(mContext)) {
            Picasso.with(mContext).load(NetworkInfos.getImageUrlString(filePath)).fit().into(holder.mMoviePoster);
        } else {
            Picasso.with(mContext).load(NetworkInfos.getImageUrlString(filePath))
                    .networkPolicy(NetworkPolicy.OFFLINE).into(holder.mMoviePoster);
        }

        //  Movie  Vote Average
        holder.mVoteAverage.setText(movie.getVoteAverage());

//       //here if u want to add click lisener when u card view
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onMovieCardSelected(position);
//            }
//        });
    }

    /**
     * Swaps the list used by the Adapter for its List data. This method is called by
     * {@link MainActivity} after a load has finished. When this method is called, we assume we have
     * a new set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newMoviesList the new list of Menu to use as MenuAdapter's data source
     */
    void swapList(final List<Movie> newMoviesList) {
        if (mMoviesList == null) {
            mMoviesList = newMoviesList;
            notifyItemRangeInserted(0, newMoviesList.size());//instead of notifyDataSetChanged();
        } else {
            /*
              Otherwise we use DiffUtil to calculate the changes and update accordingly. This
              shows the four methods you need to override to return a DiffUtil callback. The
              old list is the current list stored in mMenuItems, where the new list is the new
               values passed in from the observing the database.
              **/
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMoviesList.size();
                }

                @Override
                public int getNewListSize() {
                    return newMoviesList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMoviesList.get(oldItemPosition).getId() == newMoviesList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Movie oldItem = mMoviesList.get(oldItemPosition);
                    Movie newItem = newMoviesList.get(newItemPosition);
                    return oldItem.getId() == newItem.getId()
                            && oldItem.getMovieName().equalsIgnoreCase(newItem.getMovieName())
                            && oldItem.getVoteAverage().equalsIgnoreCase(newItem.getVoteAverage())
                            && oldItem.getMoviePoster().equals(newItem.getMoviePoster());
                }
            });
            mMoviesList = newMoviesList;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        if (mMoviesList != null) return mMoviesList.size();
        return 0;
    }

    public class MoviesListAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMoviePoster;
        private TextView mVoteAverage;
        private CardView mCardView;

        public MoviesListAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.image_poster);
            mVoteAverage = itemView.findViewById(R.id.rate_text);
            mCardView = itemView.findViewById(R.id.movie_card);

        }
    }
    //    public interface MovieAdapterListener {
//        void onAddToFavoriteSelected(int position);//position to define which object u click on by calculate it
//        void onMovieCardSelected(int position);
//    }

}

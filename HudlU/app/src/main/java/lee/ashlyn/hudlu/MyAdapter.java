package lee.ashlyn.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ashlyn.lee on 3/8/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String[] mListData;
    private OnAdapterInteractionListener mListener;

    public MyAdapter(Context context, String[] data) {
        mListener = (OnAdapterInteractionListener) context;
        mListData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView.setText(mListData[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_text);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }
}

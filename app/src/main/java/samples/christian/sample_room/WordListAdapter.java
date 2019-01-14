package samples.christian.sample_room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import samples.christian.sample_room.Entity.Word;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private final LayoutInflater mInflater;
    private final Context CONTEXT;
    private List<Word> mWordList;

    private WordListAdapterListener mWordListAdapterListener;

    public class WordViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener{

        private TextView mWordTextView;

        public WordViewHolder(View view){
            super(view);
            mWordTextView = view.findViewById(R.id.textView);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mWordListAdapterListener.onUpdate(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mWordListAdapterListener.onDelete(getAdapterPosition());
//            return false;
            return true;
        }
    }



    public WordListAdapter(Context context, WordListAdapterListener wordListAdapterListener){
        mInflater = LayoutInflater.from(context);
        CONTEXT = context;
        mWordListAdapterListener = wordListAdapterListener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWordList != null && !mWordList.isEmpty()){
            Word current = mWordList.get(position);
            holder.mWordTextView.setText(current.getWord());
        }

        else {
            //covers the case of data not being ready yet
            holder.mWordTextView.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public void setWordList(List<Word> wordList){
        mWordList = wordList;
        notifyDataSetChanged();
    }

    public interface WordListAdapterListener{
        void onDelete(int row);
        void onUpdate(int row);
    }
}

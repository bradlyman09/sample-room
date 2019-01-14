package samples.christian.sample_room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import samples.christian.sample_room.Entity.Word;
import samples.christian.sample_room.ViewModel.WordViewModel;

//reference https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

public class MainActivity extends AppCompatActivity implements WordListAdapter.WordListAdapterListener {
    private RecyclerView mWordListRecyclerView;
    private FloatingActionButton mAddWordFloatingActionButton;
    private AppCompatButton mGetAllWordsAppCompatButton;

    private List<Word> mWordList;
    private WordListAdapter mWordListAdapter;
    private WordViewModel mWordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
//        mWordList = mWordViewModel.getmAllWords().getValue();

        mWordViewModel.getmAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                Logger.d(words.toString());
                //update the cached copy of the words in the adapter
                mWordList = words;
                mWordListAdapter.setWordList(mWordList);
            }
        });

    }

    private void initView(){
        mAddWordFloatingActionButton = findViewById(R.id.floating_action_button);
        mAddWordFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddWordActivity.class),
                        NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        mWordList = new ArrayList<>();
        mWordListAdapter = new WordListAdapter(this, this);
        mWordListAdapter.setWordList(mWordList);


        mWordListRecyclerView = findViewById(R.id.recyclerview_word_list);
        mWordListRecyclerView.setAdapter(mWordListAdapter);
        mWordListRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        mGetAllWordsAppCompatButton = findViewById(R.id.button_get_words);
        mGetAllWordsAppCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(mWordViewModel.getmAllWords().getValue().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(AddWordActivity.EXTRA_REPLY));
            Logger.d(word.toString());
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDelete(int row) {
        mWordViewModel.delete(mWordList.get(row));
    }

    @Override
    public void onUpdate(int row) {
        Word word = mWordList.get(row);
        word.setmWord("updated");
        mWordViewModel.update(word);
    }
}

package samples.christian.sample_room.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import samples.christian.sample_room.Entity.Word;
import samples.christian.sample_room.Repository.WordRepository;

/**
 * Created by BradlyMan on 11/9/18.
 */

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new WordRepository(application);
        this.mAllWords = mRepository.getmAllWords();
    }

    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }

    public void update(Word word){
        mRepository.update(word);
    }

    public void delete(Word word){
        mRepository.delete(word);
    }


}

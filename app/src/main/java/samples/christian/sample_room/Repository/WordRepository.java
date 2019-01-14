package samples.christian.sample_room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import samples.christian.sample_room.Dao.WordDao;
import samples.christian.sample_room.Entity.Word;
import samples.christian.sample_room.WordRoomDatabase;

/**
 * Created by BradlyMan on 11/8/18.
 */

public class WordRepository{
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    private enum  TransactionType  {DELETE, ADD, UPDATE};

    public WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getINSTANCE(application);
        this.mWordDao = wordRoomDatabase.wordDao();
        this.mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        new dbTransactionAsyncTask(mWordDao, TransactionType.ADD).execute(word);
    }

    public void delete(Word word){
        new dbTransactionAsyncTask(mWordDao, TransactionType.DELETE).execute(word);
    }

    public void update(Word word){
        new dbTransactionAsyncTask(mWordDao, TransactionType.UPDATE).execute(word);
    }


    private static class dbTransactionAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;
        private TransactionType mTransactionType;

        dbTransactionAsyncTask(WordDao dao, TransactionType transactionType) {
            mAsyncTaskDao = dao;
            mTransactionType = transactionType;
        }

        @Override
        protected Void doInBackground(Word... words) {
            Log.d("WordRepository.JAVA", "dbTransactionAsyncTask doInBackground " + words[0].getWord());
            switch (mTransactionType){
                case ADD:
                    mAsyncTaskDao.insert(words[0]);
                    break;
                case DELETE:
                    mAsyncTaskDao.delete(words[0]);
                    break;
                case UPDATE:
                    mAsyncTaskDao.update(words[0]);
                    break;
            }
            return null;
        }
    }
}

package samples.christian.sample_room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import samples.christian.sample_room.Entity.Word;

/**
 * Created by BradlyMan on 11/7/18.
 */

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Update
    void update(Word word);

    @Query("DELETE from word_table")
    void deleteAll();

    @Query("SELECT * from word_table")
    LiveData<List<Word>> getAllWords();
}

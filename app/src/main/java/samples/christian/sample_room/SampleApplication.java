package samples.christian.sample_room;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by BradlyMan on 11/7/18.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

    }
}

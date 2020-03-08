package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Toast.makeText(this, "Welcome to SlayinMuzos!", Toast.LENGTH_LONG).show();
        return Service.START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}

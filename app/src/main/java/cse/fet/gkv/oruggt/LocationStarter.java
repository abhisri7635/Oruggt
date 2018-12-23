package cse.fet.gkv.oruggt;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class LocationStarter extends IntentService {
    public LocationStarter() {
        super("LocationStarter");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
Long time;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(new Intent(getApplication(),Location_Service.class));
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    time=System.currentTimeMillis()+1200000;
                    while ((time>=System.currentTimeMillis())){
                        if ((time==System.currentTimeMillis())){
                            startService(new Intent(getApplication(),Location_Service.class));
                        }
                    }
                }
            }
        });
        th.start();
        return super.onStartCommand(intent, flags, startId);
    }
}

package cse.fet.gkv.oruggt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static long time = 0;
    private static long timediff = 0;
    int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            i++;
            if (i==1)
                time=System.currentTimeMillis();
        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            i++;
            if (i==1)
                time=System.currentTimeMillis();
            else if (i==3){
                i=0;
                timediff=System.currentTimeMillis()-time;
                if (timediff<2000)
                    serv(context);
            }
        }

    }
    void serv(Context context){
        Intent i = new Intent(context, MyService.class);
        context.startService(i);
    }
}

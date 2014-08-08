package com.bigfootsoftwares.notes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i= new Intent(context, SpacedService.class);

        i.putExtra("KEY1", "Value to be used by the service");
        context.startService(i);
//        throw new UnsupportedOperationException1("Not yet implemented");
    }
}

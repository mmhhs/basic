package com.feima.baseproject.daemon;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

public class KeepLiveSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static KeepLiveSyncAdapter sSyncAdapter = null;
    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new KeepLiveSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }

    static class KeepLiveSyncAdapter extends AbstractThreadedSyncAdapter {
        public KeepLiveSyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }
        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            getContext().getContentResolver().notifyChange(KeepLiveAccountProvider.CONTENT_URI, null, false);
        }
    }
}
package com.feima.baseproject.daemon;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.feima.baseproject.R;

public class KeepAuthService extends Service {
    private KeepLiveAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new KeepLiveAuthenticator(this);
    }

    private KeepLiveAuthenticator getAuthenticator() {
        if (mAuthenticator == null)
            mAuthenticator = new KeepLiveAuthenticator(this);
        return mAuthenticator;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return getAuthenticator().getIBinder();
    }

    class KeepLiveAuthenticator extends AbstractAccountAuthenticator {
        private final Context context;
        private AccountManager accountManager;
        public KeepLiveAuthenticator(Context context) {
            super(context);
            this.context = context;
            accountManager = AccountManager.get(context);
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options)
                throws NetworkErrorException {
            // 添加账号 示例代码
            final Bundle bundle = new Bundle();
//            final Intent intent = new Intent(context, AuthActivity.class);
//            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return bundle;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
                throws NetworkErrorException {
            // 认证 示例代码
            String authToken = accountManager.peekAuthToken(account, getString(R.string.account_auth_type));
            //if not, might be expired, register again
            if (TextUtils.isEmpty(authToken)) {
                final String password = accountManager.getPassword(account);
                if (password != null) {
                    //get new token
                    authToken = account.name + password;
                }
            }
            //without password, need to sign again
            final Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(authToken)) {
                bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return bundle;
            }

            //no account data at all, need to do a sign
//            final Intent intent = new Intent(context, AuthActivity.class);
//            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//            intent.putExtra(AuthActivity.ARG_ACCOUNT_NAME, account.name);
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return bundle;
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
//            throw new UnsupportedOperationException();
            return null;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
                throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
                throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features)
                throws NetworkErrorException {
            return null;
        }
    }
}
package com.feima.baseproject.util.net;

import android.content.Context;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class CustomerSocketFactory extends SSLSocketFactory {

    private static final String PASSWD = "supertoys";

    public CustomerSocketFactory(KeyStore truststore)
            throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
        super(truststore);
    }

    public static SSLSocketFactory getSocketFactory(Context context) {
        InputStream input = null;
        try {
//            input = context.getResources().openRawResource(R.raw.supertoys);//证书文件名
            KeyStore trustStore = KeyStore.getInstance("BKS");
            trustStore.load(input, PASSWD.toCharArray());
            SSLSocketFactory factory = new CustomerSocketFactory(trustStore);
            factory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);

//            TrustManagerFactory tmf = TrustManagerFactory
//                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, tmf.getTrustManagers(), null);



            return factory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input = null;
            }
        }
    }

}
package com.enjoy.network;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cer = cf.generateCertificate(new FileInputStream("C:\\Users\\Administrator\\" +
                "Desktop\\hk.cer"));

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry("asdsadsa", cer);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        X509TrustManager userTrustManager = (X509TrustManager) trustManagers[0];

        SSLContext s = SSLContext.getInstance("TLS");

        // 初始化SSLContext实例
        s.init(null, new TrustManager[]{userTrustManager}, null);
        SSLSocketFactory socketFactory = s.getSocketFactory();
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .sslSocketFactory(socketFactory, userTrustManager)
                .build();
        Request request = new Request.Builder().url("https://stock.pingan.com.hk/home/upload/form/form_c_27.pdf")
                .get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                System.out.println(code);
            }
        });
        while (true) {
        }
    }
}
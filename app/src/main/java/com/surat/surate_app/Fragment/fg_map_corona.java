package com.surat.surate_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.surat.surate_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fg_map_corona#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fg_map_corona extends Fragment {
    private WebView webView;
    private TextView tvNoInternet;
    private ImageView img;
    private Button btnTryAgain;
//    private ProgressBar bar;

    boolean doubleBackToExitPressedOnce = false;
    String wUrl = "https://infocorona.magelangkab.go.id/mobile/map?accesstoken=56e5d86cd4e320da42d883897300d8eb";
    private Boolean close_app = false;
    private static String currenturl = "";

    public fg_map_corona() {
        // Required empty public constructor
    }

    public static fg_map_corona newInstance() {
        fg_map_corona fragment = new fg_map_corona();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fg_map_corona, container, false);

        webView = view.findViewById(R.id.webView);
//        bar = view.findViewById(R.id.progressBar2);
        tvNoInternet = view.findViewById(R.id.tvNoInternet);
        btnTryAgain = view.findViewById(R.id.btnTryAgain);
        img = view.findViewById(R.id.img);


        if(isNetworkAvailable())
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyBrowser());
            webView.loadUrl(wUrl);

            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

        }
        else
        {
            webView.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            btnTryAgain.setVisibility(View.VISIBLE);
            tvNoInternet.setVisibility(View.VISIBLE);
        }

        btnTryAgain.setOnClickListener(l -> {
            if(isNetworkAvailable())
            {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new MyBrowser());
                webView.loadUrl(wUrl);

                webView.setDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

            }
            else
            {
                webView.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                btnTryAgain.setVisibility(View.VISIBLE);
                tvNoInternet.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class MyBrowser extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return super.shouldOverrideUrlLoading(view,url);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
package com.example.buzzme.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.example.buzzme.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;

public class ConnectionCheck {

    public static boolean getConnectionStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static void showNoInternetSnackBar(Context context, View rootLayout) {
        Snackbar snackbar = Snackbar.make(rootLayout, "No internet connection!", Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackBarBackgroundColor));
        TextView sbText = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        sbText.setTextColor(ContextCompat.getColor(context, R.color.snackBarTextColor));
        sbText.setTextSize(16f);
        snackbar.show();
    }

}

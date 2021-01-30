package com.amazon.android.contentbrowser.payments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.android.contentbrowser.R;
import com.amazon.android.model.content.Content;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.amazon.android.contentbrowser.ContentBrowser.PRICE_MAP;
import static com.amazon.android.contentbrowser.app.ContentBrowserApplication.GSON;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.HTTP_CLIENT;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.PAYTV_SERVER;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.createPayIdUrl;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.getAddresses;

public class PaymentDialog {


    public static void createPayIdInputDialog(Activity context,
                                              Content content,
                                              DialogInterface.OnClickListener onClickListener)
            throws Exception {
        final double price;
        final String priceString;
        if (PRICE_MAP.containsKey(content.getId())) {
            priceString = context.getString(PRICE_MAP.get(content.getId()));
            price = Double.parseDouble(priceString.substring(1)); // remove $.
        } else {
            throw new Exception("Could not find price for item: " + content.getId() + ". This needs to be added to the PRICE_MAP.");
        }

        ViewGroup subView = (ViewGroup) context.getLayoutInflater().// inflater view
                inflate(R.layout.shop_tv_input_dialog, null, false);

        TextView purchaseText = subView.findViewById(R.id.shop_tv_text);
        purchaseText.setText(String.format(Locale.US, "Scan the QR code below to complete purchase of %s.\n\nPrice: %s", content.getTitle(), priceString));

        new AlertDialog.Builder(context)
                .setView(subView)
                .setTitle("Scan code to complete purchase")
                .setPositiveButton("Done", onClickListener)
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                .show();


    }
}

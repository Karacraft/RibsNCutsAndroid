package com.karacraft.ribsncuts.helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karacraft.ribsncuts.R;

public class CustomToast
{

    /** Custom Toast */
    public static void showToastMessage(String message, Context context ,int duration)
    {
        LayoutInflater inflater = ( (Activity) context).getLayoutInflater();
        View toastView = inflater.inflate(R.layout.custom_toast_layout,(ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));

        TextView tvMessage =toastView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
//        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setView(toastView);
        toast.show();
    }


}

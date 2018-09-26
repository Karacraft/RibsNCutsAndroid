package com.karacraft.ribsncuts;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/** Mother of All Activities.*/
public class BaseActivity extends AppCompatActivity
{

    ProgressDialog progressDialog;      //Progress Dialog to be shown during long operations

    void enableProgressDialog(String message)
    {
        /** Show ProgressDialog */
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    void disableProgressDialog()
    {
        /** Dismiss the Progress Dialog **/
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    /** Custom Toast */
    public void showToastMessage(String message,int duration)
    {
        LayoutInflater inflater =  this.getLayoutInflater();
        View toastView = inflater.inflate(R.layout.custom_toast_layout,(ViewGroup) this.findViewById(R.id.custom_toast_layout));

        TextView tvMessage =toastView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Toast toast = new Toast(this);
        toast.setDuration(duration);
//        toast.setGravity(Gravity.CENTER|Gravity.FILL_HORIZONTAL,0,0);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
//        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setView(toastView);
        toast.show();
    }

}

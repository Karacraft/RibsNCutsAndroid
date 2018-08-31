package com.karacraft.ribsncuts.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.karacraft.ribsncuts.R;



/**
 *
 *
 * NOT USING THESE CLASS
 *
 *
 *
 * */
public class CustomProgressDialog extends Dialog
{

    //Listener
    private Context context;
    TextView tvMessage;

    public CustomProgressDialog(@NonNull Context context)
    {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        tvMessage = findViewById(R.id.tvMessage);

    }

    public void setMessage(String message)
    {
        tvMessage.setText(message);
    }

}


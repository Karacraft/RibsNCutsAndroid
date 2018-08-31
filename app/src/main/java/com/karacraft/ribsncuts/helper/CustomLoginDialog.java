package com.karacraft.ribsncuts.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.karacraft.ribsncuts.R;


/**
 *
 *
 * NOT USING THESE CLASS
 *
 *
 *
 * */
public class CustomLoginDialog extends AlertDialog
{

    //Listener
    public NoticeDialogListener listener;
    private Context context;

    private Button btnCustomLoginUserLogin;
    private Button btnCustomLoginNewUserRegister;
    private EditText etEmail;
    private EditText etPassword;

    public interface NoticeDialogListener {
        void customLoginButtonClicked(AlertDialog dialog);
        void customNewUserRegisterButtonClicked(AlertDialog dialog);
    }

    /**
     * Creates a builder for an alert dialog that uses the default alert
     * dialog theme.
     * <p>
     * The default alert dialog theme is defined by
     * {@link android.R.attr#alertDialogTheme} within the parent
     * {@code context}'s theme.
     *
     * @param context the parent context
     */
    public CustomLoginDialog(@NonNull Context context, NoticeDialogListener listener)
    {
        super(context);
        /** Ensure to set the listener here*/
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_login_dialog);       //Custom Layout
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //For Animation


        btnCustomLoginUserLogin = findViewById(R.id.btnCustomLoginSubmit);
        btnCustomLoginNewUserRegister = findViewById(R.id.btnCustomLoginNewUserRegister);
        etEmail = findViewById(R.id.etCustomLoginEmail);
        etPassword = findViewById(R.id.etCustomLoginPassword);

        /** Set Listener*/

    }



}


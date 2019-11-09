package com.example.pawel.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsChangeMyPassword extends AppCompatActivity {
        EditText mMyCurrentPass, mFirstNewPass, mSecondNewPass;
        Button mChangePassBtn;
        String currentPass, firstNewPass, secondNewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_my_password);

        mMyCurrentPass= findViewById(R.id.myCurrentPass);
        mFirstNewPass=findViewById(R.id.myFirstNewPass);
        mSecondNewPass=findViewById(R.id.mySecondNewPass);
        mChangePassBtn=findViewById(R.id.admin_setting_change_myPass_btn);

//        mChangePassBtn.setEnabled(false);

mChangePassBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        checkData();
    }
});


    }



    private void checkData(){
        currentPass= mMyCurrentPass.getText().toString().trim();
        firstNewPass= mFirstNewPass.getText().toString().trim();
        secondNewPass= mSecondNewPass.getText().toString().trim();

        if ((TextUtils.isEmpty(currentPass)) ||(TextUtils.isEmpty(firstNewPass))||(TextUtils.isEmpty(secondNewPass)))  {
            Toast.makeText(this, "Uzupełnij brakujące pola", Toast.LENGTH_SHORT).show();
        }
        else if (!((TextUtils.isEmpty(currentPass)) &&(TextUtils.isEmpty(firstNewPass))&&(TextUtils.isEmpty(secondNewPass)))){



            if (TextUtils.equals(firstNewPass,secondNewPass)) {
                Toast.makeText(this, "Działa", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Wpisane hasła różnią się", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Działa", Toast.LENGTH_SHORT).show();
        }


    }
}

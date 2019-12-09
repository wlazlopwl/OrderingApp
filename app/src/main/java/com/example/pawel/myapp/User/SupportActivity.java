package com.example.pawel.myapp.User;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawel.myapp.Admin.BottomSheetFragment;
import com.example.pawel.myapp.R;

public class SupportActivity extends AppCompatActivity {

    String[] support_questions;
    String[] support_answer;
    ListView supportListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_support);
        supportListView= (ListView) findViewById(R.id.supportListView);
        final Button contactBtn= (Button) findViewById(R.id.support_contact_btn);
    support_questions=getResources().getStringArray(R.array.support_questions);
        support_answer=getResources().getStringArray(R.array.support_answer);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,support_questions);
        supportListView.setAdapter(adapter);

       supportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();
                args.putString("answer", support_answer[position]);

                    supportAnswerDialogFragment dialogFragment = new supportAnswerDialogFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    dialogFragment.setArguments(args);


                dialogFragment.show(fm,"support");




//                bottomSheetDialogFragment.setArguments(args);
//                bottomSheetDialogFragment.show(fm,"support");




            }
        });
       contactBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               supportContactDialogFragment contactDialog = new supportContactDialogFragment();
               FragmentManager fm = getSupportFragmentManager();

               contactDialog.show(fm, "contact");


           }
       });

    }



}

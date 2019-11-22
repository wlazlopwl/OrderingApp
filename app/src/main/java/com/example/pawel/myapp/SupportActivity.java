package com.example.pawel.myapp;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SupportActivity extends AppCompatActivity {

    String[] support_questions;
    String[] support_answer;
    ListView supportListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        supportListView= (ListView) findViewById(R.id.supportListView);
        Button contactBtn= (Button) findViewById(R.id.support_contact_btn);
    support_questions=getResources().getStringArray(R.array.support_questions);
        support_answer=getResources().getStringArray(R.array.support_answer);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,support_questions);
        supportListView.setAdapter(adapter);

       supportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();
                args.putString("answer", support_answer[position]);

                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetFragment();

                   // supportAnswerDialogFragment dialogFragment = new supportAnswerDialogFragment();
                    FragmentManager fm = getSupportFragmentManager();
                   // dialogFragment.setArguments(args);
                    //dialogFragment.show(fm,"support");




                bottomSheetDialogFragment.setArguments(args);
                bottomSheetDialogFragment.show(fm,"support");




            }
        });
       contactBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle args = new Bundle();
               BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
               FragmentManager fm = getSupportFragmentManager();

               bottomSheetDialogFragment.setArguments(args);
               bottomSheetDialogFragment.show(fm,"support");


           }
       });

    }



}

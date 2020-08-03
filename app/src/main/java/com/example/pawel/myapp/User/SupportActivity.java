package com.example.pawel.myapp.User;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.pawel.myapp.R;

public class SupportActivity extends AppCompatActivity {

    String[] support_questions;
    String[] support_answer;
    ListView supportListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_support);
        supportListView = (ListView) findViewById(R.id.supportListView);
        final Button contactBtn = (Button) findViewById(R.id.support_contact_btn);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.support_toolbar);
        toolbar.setTitle("Pomoc");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        support_questions = getResources().getStringArray(R.array.support_questions);
        support_answer = getResources().getStringArray(R.array.support_answer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, support_questions);
        supportListView.setAdapter(adapter);
//        simple_expandable_list_item_1
        supportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();
                args.putString("answer", support_answer[position]);

                supportAnswerDialogFragment dialogFragment = new supportAnswerDialogFragment();
                FragmentManager fm = getSupportFragmentManager();
                dialogFragment.setArguments(args);


                dialogFragment.show(fm, "support");


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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}

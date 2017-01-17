package com.example.admin.studentreportcard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.admin.studentreportcard.R.string.marks;
import static com.example.admin.studentreportcard.R.string.subject;
import static com.example.admin.studentreportcard.R.string.surname;


public class DisplayData extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DatabaseHelper myDB;


    EditText editTxtName, editTxtSurname, editTxtSnu, editTxtSubject, editTxtMarks;


    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

       editTxtName = (EditText) findViewById(R.id.editTextName);
        editTxtSurname = (EditText) findViewById(R.id.editTextSurname);
        editTxtSnu = (EditText) findViewById(R.id.editTextSnumber);
        editTxtSubject = (EditText) findViewById(R.id.editTextSubject);
        editTxtMarks = (EditText) findViewById(R.id.editTextMarks);

        myDB = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int value = extras.getInt("id");

            if(value>0){
                Cursor c = myDB.getData(value);
                id_To_Update = value;
                c.moveToFirst();

                String nam = c.getString(c.getColumnIndex(DatabaseHelper.COL_2));
                String surnam = c.getString(c.getColumnIndex(DatabaseHelper.COL_3));
                String studentnu = c.getString(c.getColumnIndex(DatabaseHelper.COL_4));
                String subjec = c.getString(c.getColumnIndex(DatabaseHelper.COL_5));
                String mar = c.getString(c.getColumnIndex(DatabaseHelper.COL_6));

                if (!c.isClosed()){
                    c.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                editTxtName.setText((CharSequence)nam);
                editTxtName.setFocusable(false);
                editTxtName.setClickable(false);

               editTxtSurname.setText((CharSequence)surnam);
                editTxtSurname.setFocusable(false);
                editTxtSurname.setClickable(false);

                editTxtSnu.setText((CharSequence)studentnu);
                editTxtSnu.setFocusable(false);
                editTxtSnu.setClickable(false);

                editTxtSubject.setText((CharSequence)subjec);
                editTxtSubject.setFocusable(false);
                editTxtSubject.setClickable(false);

                editTxtMarks.setText((CharSequence)mar);
                editTxtMarks.setFocusable(false);
                editTxtMarks.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Bundle extras = getIntent().getExtras();

        if (extras !=null){
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_data, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
        }
    }
        return true;
}

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.Edit_Data:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                editTxtName.setEnabled(true);
                editTxtName.setFocusableInTouchMode(true);
                editTxtName.setClickable(true);

                editTxtSurname.setEnabled(true);
                editTxtSurname.setFocusableInTouchMode(true);
                editTxtSurname.setClickable(true);

                editTxtSnu.setEnabled(true);
                editTxtSnu.setFocusableInTouchMode(true);
                editTxtSnu.setClickable(true);

                editTxtSubject.setEnabled(true);
                editTxtSubject.setFocusableInTouchMode(true);
                editTxtSubject.setClickable(true);

                editTxtMarks.setEnabled(true);
                editTxtMarks.setFocusableInTouchMode(true);
                editTxtMarks.setClickable(true);

                return true;
                case R.id.Delete_Data:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteData).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        myDB.deleteData(id_To_Update);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                    }
                });
                    AlertDialog d = builder.create();
                    d.setTitle("Are you sure?");
                    d.show();

                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }}
    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (myDB.updateData(id_To_Update, editTxtName.getText().toString(), editTxtSurname.getText().toString(),
                        editTxtSnu.getText().toString(), editTxtSubject.getText().toString(), editTxtMarks.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (myDB.insertData(editTxtName.getText().toString(), editTxtSurname.getText().toString(), editTxtSnu.getText().toString(),
                        editTxtSubject.getText().toString(), editTxtMarks.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}

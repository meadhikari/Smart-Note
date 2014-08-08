package com.bigfootsoftwares.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NewNotes extends ActionBarActivity {

    EditText title;
    EditText content;
    Button start_remembering;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setTitle("Add Notes");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b5b5")));
        int titleId = getResources().getIdentifier("action_bar_title", "id","android");
        TextView yourTextView = (TextView) findViewById(titleId);
        yourTextView.setTextSize(30);
        Typeface face = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");
        yourTextView.setTypeface(face);

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        title.setTypeface(face);
        content.setTypeface(face);

        start_remembering = (Button) findViewById(R.id.start_remembering);
        start_remembering.setTypeface(face);
        start_remembering.setTextSize(30);

        start_remembering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    NotesDataSource nds = new NotesDataSource(getApplicationContext());
                    if (!title.getText().toString().equals("") || !content.getText().toString().equals("") )
                    {
                        nds.insertNotes(title.getText().toString(),content.getText().toString());
                        Toast.makeText(getApplicationContext(),"Note Added.",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(NewNotes.this,MainActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please write something.",Toast.LENGTH_LONG).show();
                    }


                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error Please try again",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about)
        {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            View localView = getLayoutInflater().inflate(R.layout.aboutus, null);
            TextView localTextView1 = (TextView)localView.findViewById(R.id.title);
            Typeface localTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
            localTextView1.setTypeface(localTypeface);
            TextView localTextView2 = (TextView)localView.findViewById(R.id.content);
            SpannableString localSpannableString = new SpannableString("Humans more easily remember or learn items when they are studied a few times spaced over a long time span rather than repeatedly studied in a short span of time\n\n" +
                    "Just click on the + icon and start adding notes and let the app handle the remembering part for you\n\n" +
                    "Long press a note to delete it\n\n\n" +
                    "Warning: Having too many notes at once could lead to multiple notification making this app unusable, limit to few notes at a time to get the most from the app");
            Linkify.addLinks(localSpannableString, 15);
            localTextView2.setTypeface(localTypeface);
            localTextView2.setText(localSpannableString);
            localBuilder.setView(localView).setPositiveButton("Ok", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                }
            });
            localBuilder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }*/
}

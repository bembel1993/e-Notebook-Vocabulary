package com.example.enotebookvocabulary;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String FILENAME = "text.txt";
    private TextView textView;
    private ArrayAdapter<Word> adapter;
    private EditText russ, eng, ger;
    private List<Word> words;
    ListView listView;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        russ = findViewById(R.id.russ);
        eng = findViewById(R.id.eng);
        ger = findViewById(R.id.ger);

        listView = findViewById(R.id.list);
        textView = findViewById(R.id.textFileTxt);

        words = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        listView.setAdapter(adapter);

        //search method
        searchView = findViewById(R.id.search);
        //end search method
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo5);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Choose and correct item menu
        switch (item.getItemId()) {
            case R.id.action_open:
              //  openFile(FILENAME);
                return true;
            case R.id.action_save:
                saveFile(FILENAME);
                return true;
            default:
                return true;
        }
    }

    public void searchMethod(View view){
        words = JSONHelper.importFromJSON(this);

        if(words!=null) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        listView.setAdapter(adapter);
            /*if(words!=null) {*/
            Toast.makeText(this, "Data is search", Toast.LENGTH_LONG).show();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void openFile(View view) {
        FileInputStream fin = null;
        ListView listView = findViewById(R.id.list);
        try {
            fin = openFileInput(FILENAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            //    textView.setText(text);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
            listView.setAdapter(adapter);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            String russian = russ.getText().toString();
            String english = eng.getText().toString();
            String germany = ger.getText().toString();

           /* if(russian.equals("")) {
                Toast.makeText(this, "Field russ is empty", Toast.LENGTH_LONG).show();
            }
            if(english.equals("")) {
                Toast.makeText(this, "Field eng is empty", Toast.LENGTH_LONG).show();
            }
            if(germany.equals("")) {
                Toast.makeText(this, "Field ger is empty", Toast.LENGTH_LONG).show();
            }*/
            if(russian.equals("") || english.equals("") || germany.equals("")) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
            }
            else if (!russian.equals("") && !english.equals("") && !germany.equals("")){
            Word wordsTxt = new Word(russian, english, germany);
                osw.write(wordsTxt.toString());
                //    osw.write(eng.getText().toString());
                //  osw.write(ger.getText().toString());
                Toast.makeText(this, "Data saved in TXT", Toast.LENGTH_LONG).show();
                osw.close();
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

    }



   /* public void addWords(View view){
        String russian = russ.getText().toString();
        String english = eng.getText().toString();
        String germany = ger.getText().toString();
        Word words1 = new Word(russian, english, germany);
        words.add(words1);
        adapter.notifyDataSetChanged();
    }*/


    public void save(View view){

        String russian = russ.getText().toString();
        String english = eng.getText().toString();
        String germany = ger.getText().toString();

       /* if(russian.equals("")) {
            Toast.makeText(this, "Field russ is empty", Toast.LENGTH_LONG).show();
        }
        if(english.equals("")) {
            Toast.makeText(this, "Field eng is empty", Toast.LENGTH_LONG).show();
        }
        if(germany.equals("")) {
            Toast.makeText(this, "Field ger is empty", Toast.LENGTH_LONG).show();
        }*/
        if(russian.equals("") || english.equals("") || germany.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else if(!russian.equals("") && !english.equals("") && !germany.equals("")){

            Word words1 = new Word(russian, english, germany);

            words.add(words1);
            adapter.notifyDataSetChanged();

            boolean result = JSONHelper.exportToJSON(this, words);

            if (result) {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void open(View view){
        words = JSONHelper.importFromJSON(this);
        if(words!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Data is open", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Failed to open data", Toast.LENGTH_LONG).show();
        }
    }


}
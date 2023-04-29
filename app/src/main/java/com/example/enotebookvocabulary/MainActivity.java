package com.example.enotebookvocabulary;

import static org.apache.commons.io.FileUtils.readFileToString;

import android.os.Bundle;
import android.os.FileUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    ArrayList array_list;

    ArrayAdapter arrayAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        array_list = new ArrayList();
        russ = findViewById(R.id.russ);
        eng = findViewById(R.id.eng);
        ger = findViewById(R.id.ger);

        listView = findViewById(R.id.list);
       // textView = findViewById(R.id.textFileTxt);

        words = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        listView.setAdapter(adapter);

        //search method
        searchView = findViewById(R.id.search);
        //end search method
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo5);

     /*   findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   russ = findViewById(R.id.russ);
              //  String russian = russ.getText().toString();
               // List<Word> wordsdel = new ArrayList<>();
                // Load the contents of the JSON file into a string
                if(array_list.size()>0) {
                    if (!russ.getText().toString().isEmpty()) {

                        array_list.remove(russ.getText().toString());
                     //   wordsdel.remove(eng.getText().toString());
                     //   wordsdel.remove(ger.getText().toString());
                        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, array_list);
                        listView.setAdapter(arrayAdapter);
                        Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "There is no element to delete", Toast.LENGTH_LONG).show();
                }
            }
        });*/
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

            if(russian.equals("")) {
                Toast.makeText(this, "Field russ is empty", Toast.LENGTH_LONG).show();
            }
            else if(english.equals("")) {
                Toast.makeText(this, "Field eng is empty", Toast.LENGTH_LONG).show();
            }
            else if(germany.equals("")) {
                Toast.makeText(this, "Field ger is empty", Toast.LENGTH_LONG).show();
            }
         //   else if(russian.equals("") || english.equals("") || germany.equals("")) {
           //     Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
           // }
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

        if(russian.equals("")) {
            Toast.makeText(this, "Field russ is empty", Toast.LENGTH_LONG).show();
        }
        else if(english.equals("")) {
            Toast.makeText(this, "Field eng is empty", Toast.LENGTH_LONG).show();
        }
        else if(germany.equals("")) {
            Toast.makeText(this, "Field ger is empty", Toast.LENGTH_LONG).show();
        }
      //  else if(russian.equals("") || english.equals("") || germany.equals("")) {
        //    Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
       // }
        else if(!russian.equals("") && !english.equals("") && !germany.equals("")){
            words = JSONHelper.importFromJSON(this);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
            listView.setAdapter(adapter);

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
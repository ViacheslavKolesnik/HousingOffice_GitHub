package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Streets extends AppCompatActivity {
    ArrayList<String> name=new ArrayList<>();
    public static ArrayList<Integer> IDStreet=new ArrayList<>();
    public static  ArrayList<Integer> sendId=new ArrayList<>();
    ListView lvMain;
    //int IDSubRegion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streets);
        new ParseTask().execute();
    }
    public void Next(View view){
        sendId.clear();
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            Log.d("myLog", "---------------------------------------------------9");
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                sendId.add(IDStreet.get(key));
                Log.d("myLog", "---------------------------------------------------11  "+IDStreet.get(key));
            }
        }

        Intent intent = new Intent(this, Adressee.class);
        startActivity(intent);
    }
    public int cd=1;
    public void All(View view){
        if(cd%2!=0) for (int i=0;i<IDStreet.size();i++) lvMain.setItemChecked(i,true);
        else for (int i=0;i<IDStreet.size();i++) lvMain.setItemChecked(i,false);
        cd++;
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(Void... params) {
            String s="";
            try {
                URL url = new URL("http://house-utilities-api.azurewebsites.net/api/streets/getbyregion/key=314,id=10");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                s = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String res) {
            lvMain = (ListView) findViewById(R.id.lvMain);
            lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            try {
                JSONArray streets = new JSONArray(res);

                for (int i = 0; i < streets.length(); i++) {
                    JSONObject street = streets.getJSONObject(i);
                    name.add(street.getString("Name"));
                    IDStreet.add(Integer.parseInt(street.getString("StreetId")));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<>(Streets.this,android.R.layout.simple_list_item_multiple_choice,name);
            lvMain.setAdapter(adapter);
        }
    }
}

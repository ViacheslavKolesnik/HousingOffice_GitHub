package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Send extends AppCompatActivity {
    EditText send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        send=(EditText)findViewById(R.id.editText3);
        Log.d("mylog","-----"+ Apartment.sendemail.toString()+"-------");
    }
    public void Next(View view){
        new Thread(new Runnable() {
            public void run() {
                try {
                    sendRequest(send.getText().toString(), Apartment.sendemail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Intent intent = new Intent(this, Return.class);
        startActivity(intent);
    }
    public static String getHTML(String urlToRead)
    {
        URL url;
        Log.d("mylog","-----1------");
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        return result;
    }
    private static void sendRequest(String text, List<String> eMails) throws Exception
    {
        String reqList = String.format("http://api.feedgee.com/1.0/listNew?apikey=c18c99b2397b4c599a66973df27b3849&name=temp");
        Log.d("mylog","-----2------");
        String sList = getHTML(reqList);
        String sId = sList.substring(sList.indexOf("<id>") + 4);
        sId = sId.substring(0, sId.indexOf("<"));
        int id = Integer.parseInt(sId);
        Log.d("mylog","-----3------");
        String reqSendCreate = "";
        reqSendCreate += "http://api.feedgee.com/1.0/listSubscribeOptIn?apikey=c18c99b2397b4c599a66973df27b3849&list_id=" + id + "&emails=";
        for (int i = 0; i < eMails.size(); ++i)
        {
            reqSendCreate += eMails.get(i);
            if (i != eMails.size() - 1)
                reqSendCreate += ",";
        }
        Log.d("mylog","-----3------");
        reqSendCreate += "&phones=";
        for (int i = 1; i < eMails.size(); ++i)
            reqSendCreate += ",";
        reqSendCreate += "&optin=FALSE&update_existing=TRUE";
        getHTML(reqSendCreate);

        String[] texts=text.split(" ");
        String fuckSpacebarAndThisFuckingEncoding="";
        for (int i = 0; i < texts.length; ++i)
        {
            fuckSpacebarAndThisFuckingEncoding+=texts[i];
            if (i != texts.length - 1)
                fuckSpacebarAndThisFuckingEncoding += "+";
        }
        Log.d("mylog","-----4------");
        String reqCampCreate = String.format("http://api.feedgee.com/1.0/campaignEmailNew?apikey=c18c99b2397b4c599a66973df27b3849&list_ids="+id+"&subject=hagtyde@gmail.com&html="+fuckSpacebarAndThisFuckingEncoding+"&names=&values=&isTransactional=FALSE");
        reqCampCreate = new String(reqCampCreate.getBytes("UTF-8"), "windows-1251");
        String Campain = getHTML(reqCampCreate);

        sId = Campain.substring(Campain.indexOf("<id>") + 4);
        sId = sId.substring(0, sId.indexOf("<"));
        id = Integer.parseInt(sId);
        Log.d("mylog","-----5------");
        String reqSend = String.format("http://api.feedgee.com/1.0/campaignEmailSendNow?apikey=c18c99b2397b4c599a66973df27b3849&campaign_Id="+sId);
        getHTML(reqSend);
        Log.d("mylog", "-----6------");
    }
}

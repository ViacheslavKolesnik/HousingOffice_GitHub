package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Porches extends AppCompatActivity {
    private static final String dburl = "jdbc:jtds:sqlserver://houseProject.mssql.somee.com";
    private static final String dbuser = "hagtyde_SQLLogin_1";
    private static final String dbpassword = "nblhstmj3e";
    ArrayList<String> name=new ArrayList<>();
    public static ArrayList<Integer> IDPorches=new ArrayList<>();
    public static  ArrayList<Integer> sendId=new ArrayList<>();
    ArrayList<Integer> IDHouses;
    String id="(";
    ListView lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porches);
        IDHouses= Adressee.sendId;
        lvMain = (ListView) findViewById(R.id.lvMain);
        // устанавливаем режим выбора пунктов списка
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // Создаем адаптер, используя массив из файла ресурсов
        for (int i = 0; i < IDHouses.size(); i++) {
            if (i == 0) id += IDHouses.get(i) + "";
            else id += ", " + IDHouses.get(i);
        }
        id += ")";
        Thread d = new Thread(new Runnable() {
            public void run() {
                Log.d("myLog", "---------------------------------------------------2");
                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;

                String query = "SELECT * FROM Porches WHERE HouseId IN" + id;
                Log.d("myLog", "---------------------------------------------------IDHauses=" + id);
                Log.d("myLog", "---------------------------------------------------3");
                try {
                    // opening database connection to MySQL server
                    con = DriverManager.getConnection(dburl, dbuser, dbpassword);
                    Log.d("myLog", "---------------------------------------------------4");
                    // getting Statement object to execute query
                    stmt = con.createStatement();
                    Log.d("myLog", "---------------------------------------------------5");
                    // executing SELECT query
                    rs = stmt.executeQuery(query);
                    Log.d("myLog", "---------------------------------------------------6");
                    while (rs.next()) {
                        Log.d("myLog", "--------------------------------------------------ITERATION");
                        int number = rs.getInt("PorchNumber");
                        name.add(number + "");
                        int iD = rs.getInt("PorchId");
                        IDPorches.add(iD);
                        //list.add(new House(iD,number));
                        Log.d("myLog", "---------------------------------------------------number" + number + "ID" + iD);
                    }
                    Log.d("myLog", "---------------------------------------------------7");

                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    //close connection ,stmt and resultset here
                    try {
                        con.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        stmt.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        rs.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Porches.this, android.R.layout.simple_list_item_multiple_choice, name);
        lvMain.setAdapter(adapter);
        Log.d("myLog", "---------------------------------------------------8");
    }
    public void Next(View view) {
        sendId.clear();
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            Log.d("myLog", "---------------------------------------------------9");
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                sendId.add(IDPorches.get(key));
                Log.d("myLog", "---------------------------------------------------11  " + IDPorches.get(key));
            }
            Intent intent = new Intent(this, Apartment.class);
            startActivity(intent);
        }
    }
    public int cd=1;
    public void All(View view){
        if(cd%2!=0) for (int i=0;i<IDPorches.size();i++) lvMain.setItemChecked(i,true);
        else for (int i=0;i<IDPorches.size();i++) lvMain.setItemChecked(i,false);
        cd++;
    }
}

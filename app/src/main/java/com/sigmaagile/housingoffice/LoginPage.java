package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {
    private static final String dburl = "jdbc:jtds:sqlserver://houseProject.mssql.somee.com";
    private static final String dbuser = "hagtyde_SQLLogin_1";
    private static final String dbpassword = "nblhstmj3e";
    public static ArrayList<Account> list;
    public boolean isinlist;
    public boolean correctdata;
    public int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        correctdata=true;
        list = new ArrayList<>();
        setContentView(R.layout.activity_login_page);
        new Thread(new Runnable(){
            public void run(){
                Connection con=null;
                Statement stmt=null;
                ResultSet rs=null;
                String query = "select * from SubRegions";
                try {
                    // opening database connection to MySQL server
                    con = DriverManager.getConnection(dburl, dbuser, dbpassword);
                    // getting Statement object to execute query
                    stmt = con.createStatement();

                    // executing SELECT query
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        int ID=rs.getInt("SubRegionId");
                        String  password=rs.getString("Password");
                        list.add(new Account(name,password,ID));
                    }
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    //close connection ,stmt and resultset here
                    try {
                        con.close();
                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                    catch(NullPointerException ex){
                        ex.printStackTrace();
                    }
                    try {
                        stmt.close();
                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                    catch(NullPointerException ex){
                        ex.printStackTrace();
                    }
                    try {
                        rs.close();
                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                    catch(NullPointerException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAdressee(View view){
        correctdata=false;
        isinlist=false;

        //EditText tt1 = (EditText) findViewById(R.id.editText);
        //tt1.setText(list.get(0).login);
        //EditText tt2 = (EditText) findViewById(R.id.editText2);
        //tt2.setText(list.get(0).password);
        String l=((EditText)findViewById(R.id.editText)).getText().toString();
        String l2=((EditText)findViewById(R.id.editText2)).getText().toString();
        for (int i = 0; i < list.size(); i++) {
            Log.d("myLogs",i+"-----"+list.get(i).login);
            if((l.compareTo(list.get(i).login))==0&&l2.compareTo(list.get(i).password)==0){
                isinlist=true;
                correctdata=true;
                ID=list.get(i).id;
                Log.d("myLogs",list.get(i).id+"");
                break;
            }
        }

        if(isinlist) {
            Intent intent = new Intent(this, Streets.class);
            Log.d("my Logs",ID+"");
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
        if(!correctdata){
            ((TextView) findViewById(R.id.textView2)).setText("Incorrect login or password");
        }
        else{
            ((TextView) findViewById(R.id.textView2)).setText("");
        }
    }
}

class Account{
    public String login;
    public String password;
    public int id;
    Account(String s, String p, int i){
        login=s;
        password=p;
        id=i;
    }
}
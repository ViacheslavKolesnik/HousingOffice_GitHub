package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {
    private ArrayList<Account> list;
    public boolean isinlist;
    public boolean iscorrectpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iscorrectpassword=true;
        list = new ArrayList<>();
        list.add(new Account("Valera","valera123"));
        list.add(new Account("Vasia","vasia123"));
        setContentView(R.layout.activity_login_page);
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
        iscorrectpassword=false;
        isinlist=false;
        String l=((EditText)findViewById(R.id.editText)).getText().toString();
        String l2=((EditText)findViewById(R.id.editText2)).getText().toString();
        for (int i = 0; i < list.size(); i++) {
            if((l.equals(list.get(i).login))&&l2.equals(list.get(i).password)){
                isinlist=true;
                iscorrectpassword=true;
                break;
            }
        }
        if(isinlist) {
            Intent intent = new Intent(this, Adressee.class);
            startActivity(intent);
        }
        if(!iscorrectpassword){
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
    Account(String s, String p){
        login=s;
        password=p;
    }
}
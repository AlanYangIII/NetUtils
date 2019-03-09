package com.example.alanyang.netutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User.Builder().url("http://bihu.jay86.com/login.php").method("POST").property("application/x-www-form-urlencoded;charset=utf-8").params("username=Alann&password=Alann").build();
                NetUtils instance = NetUtils.getInstance();
                instance.excute(user, new NetUtils.Callback() {
                    @Override
                    public void onResponse(final String response) {
                        System.out.println(response);
                        Log.d("tag", response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    @Override
                    public void onFailed(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });
    }
}

package com.example.mysqlappl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().toString();
    private TextView userid;
    private TextView password;

    private TextView resultUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid = (TextView)findViewById( R.id.editTextNumberDecimal );
        password = (TextView)findViewById( R.id.editTextTextPassword );

        resultUserid = (TextView)findViewById(R.id.resultusername);

        //スレッドからUIを変更するためにHandlerを用意する。
        final Handler handler = new Handler();

        Button loginButton = (Button)findViewById( R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( TAG, "click loginButton !");
                Thread thread = new Thread( new Runnable() {
                    @Override
                    public void run() {
                        Log.d( TAG, "thread run !");
                        PreparedStatement preparedStatement;
                        Connection connect = DBConnect.getConnection();
//                        String sql = "select * from member where member_id = ? and password = ?";
                        String sql = "select * from member where member_id = ?";
                        try {
                            preparedStatement = connect.prepareStatement( sql );
                            preparedStatement.setString( 1, userid.getText().toString() );
//                            preparedStatement.setString( 2, password.getText().toString() );

                            Log.d( TAG, preparedStatement.toString() );

                            ResultSet resultSet = preparedStatement.executeQuery();

                            // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if ( resultSet.next() ) {
                                            String data = resultSet.getString("name");
                                            resultUserid.setText( data );
                                        }
                                        else {
//                                            Toast.makeText( getApplicationContext(), "", Toast.LENGTH_LONG ).show();
                                            resultUserid.setText( "データなし" );
                                        }
                                        resultSet.close();
                                        preparedStatement.close();
                                        connect.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Log.d( TAG, "pre thread start");
                thread.start();
            }
        });
    }
}
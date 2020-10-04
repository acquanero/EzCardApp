package com.acquanero.ezcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acquanero.ezcard.io.ApiUtils;
import com.acquanero.ezcard.io.EzCardApiService;
import com.acquanero.ezcard.model.UserIdToken;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EzCardApiService myAPIService;

    private TextView mailUser;
    private TextView password;

    private UserIdToken useridtoken;

    SharedPreferences dataDepot;

    SharedPreferences.Editor dataDepotEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recupero del layout los botones y los campos de texto
        Button loginButton = (Button) findViewById(R.id.button_login);
        mailUser = (TextView) findViewById(R.id.campo_usuario);
        password = (TextView) findViewById(R.id.campo_password);

        //Traigo una instancia de retrofit para realizar los request
        myAPIService = ApiUtils.getAPIService();

        //Creo una instancia de SahredPreference para almacenar informacion
        //el archivo se encuentra en /data/data/[nombre del proyecto]/shared_prefs/archivo.xml
        dataDepot = PreferenceManager.getDefaultSharedPreferences(this);

        //Vuelvo editable mi SharedPreference
        dataDepotEditable = dataDepot.edit();

        //En esta seccion deberia chequear si ya estoy logueado (tengo token)
        //Si (hay token) =>
        //Ver si tengo tarjetas agregadas
        //Sin tarjetas => ir a AgregadoDeTarjetas Activity
        //Con Tarjetas => ir a VistaDeServicios Activity

        //asocio el evento correspondiente al boton de login
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                logIn(mailUser.getText().toString(), password.getText().toString());


            }
        });
    }

    //metodo a ejecutar al presionar el boton login
    public void logIn(String mail, String passw) {
        myAPIService.getUserInfo(mail, passw).enqueue(new Callback<UserIdToken>() {
            @Override
            public void onResponse(Call<UserIdToken> call, Response<UserIdToken> response) {

                if(response.isSuccessful()) {

                    //guardo el id y el token en una variable
                    int idUsuario = response.body().getUserId();
                    String token = response.body().getToken();

                    //almaceno el id y el token en el SharedPreference
                    dataDepotEditable.putInt("user_id", idUsuario);
                    dataDepotEditable.putString("token", token);
                    dataDepotEditable.apply();

                    //Chequeo que se hayan almacenado
                    System.out.println("----------------------------------");
                    System.out.println("User id: " + dataDepot.getInt("user_id", -1) + " Token: " + dataDepot.getString("token", "null"));
                    System.out.println("----------------------------------");

                    Log.i("RTA SUCCESS", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UserIdToken> call, Throwable t) {
                Log.e("RTA FAIL", "Unable to submit post to API.");
            }
        });
    }


}
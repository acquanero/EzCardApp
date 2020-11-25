package com.acquanero.ezcard.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acquanero.ezcard.R;
import com.acquanero.ezcard.models.Card;
import com.acquanero.ezcard.models.Provider;
import com.acquanero.ezcard.models.UserData;
import com.google.gson.Gson;

public class EditProviderActivity extends AppCompatActivity {

    SharedPreferences dataDepot;
    private TextView cardNameLabel, serviceTitle;
    private Provider provider;
    private Card associatedCard;
    private Button buttonDisassociateCard, buttonDeleteService, buttonChangeName;
    private int idProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle datos = getIntent().getExtras();
        idProvider = datos.getInt("idProvider");

        associatedCard = null;

        //Creo una instancia de SahredPreference para almacenar informacion
        //el archivo se encuentra en /data/data/[nombre del proyecto]/shared_prefs/archivo.xml
        dataDepot = PreferenceManager.getDefaultSharedPreferences(this);

        String userJson = dataDepot.getString("usuario", "null");
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userJson, UserData.class);

        for (Provider p : userData.getProviders()){
            if(p.getProviderId() == idProvider){
                provider = p;
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_provider);

        serviceTitle = findViewById(R.id.labelTitleServiceName);
        serviceTitle.setText(provider.getProviderName());

        for (Card c : userData.getCards()){
            if(c.getCardId() == provider.getCardId()){
                associatedCard = c;
            }
        }

        cardNameLabel = findViewById(R.id.cardNameLabel);
        buttonDisassociateCard = findViewById(R.id.disassociateCardsButton);

        if (associatedCard != null){
            buttonDisassociateCard.setVisibility(View.VISIBLE);
            cardNameLabel.setText(associatedCard.getName());

            buttonDisassociateCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent goToDissociate = new Intent(getApplicationContext(), EnterPinToConfirmActivity.class);
                    goToDissociate.putExtra("flag","enterPinToUnbindProvider");
                    goToDissociate.putExtra("providerId", idProvider);
                    startActivity(goToDissociate);

                }
            });

        } else {
            buttonDisassociateCard.setVisibility(View.GONE);
            cardNameLabel.setText(getResources().getString(R.string.no_card_associated));
        }

        buttonChangeName = findViewById(R.id.buttonChangeProviderName);
        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToChangeName = new Intent(getApplicationContext(), EnterProviderNewNameActivity.class);
                goToChangeName.putExtra("providerName", provider.getProviderName());
                goToChangeName.putExtra("providerId", idProvider);
                startActivity(goToChangeName);

            }
        });


        buttonDeleteService = findViewById(R.id.buttonDeleteService);
        buttonDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToDelete = new Intent(getApplicationContext(), EnterPinToConfirmActivity.class);
                goToDelete.putExtra("flag","enterPinToDeleteProvider");
                goToDelete.putExtra("providerName", provider.getProviderName());
                goToDelete.putExtra("providerId", idProvider);
                startActivity(goToDelete);

            }
        });

    }
}
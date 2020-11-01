package com.acquanero.ezcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acquanero.ezcard.model.Card;
import com.acquanero.ezcard.model.UserData;
import com.google.gson.Gson;

public class SelectNewCardForService extends AppCompatActivity {

    SharedPreferences dataDepot;

    private int providerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_card_for_service);

        Bundle datos = getIntent().getExtras();
        providerId = datos.getInt("providerId");

        //Creo una instancia de SahredPreference para almacenar informacion
        //el archivo se encuentra en /data/data/[nombre del proyecto]/shared_prefs/archivo.xml
        dataDepot = PreferenceManager.getDefaultSharedPreferences(this);

        String userJson = dataDepot.getString("usuario", "null");
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userJson, UserData.class);

        View butonAddCard = findViewById(R.id.buttonAddCardNew);

        butonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddNewCardActivity.class);
                startActivity(i);

            }
        });


        //Guardo el LinearLayout del CardsActivity en una variable
        LinearLayout linearLayoutCards = findViewById(R.id.linearLayoutSelectCard);


        //Con un for each recorro la lista de tarjetas y genero el imageButton con un label por cada tarjeta
        //y los inserto en un linearLayout horizontal
        //y a su vez este ultimo, lo inserto en cada renglon del linearLayout principal
        for (Card card : userData.getCards()){

            ImageView botonImage = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
            botonImage.setLayoutParams(layoutParams);

            TextView txt = new TextView(this);
            txt.setText(card.getName());
            txt.setGravity(Gravity.CENTER);

            LinearLayout lineaHorizontal = new LinearLayout(this);
            lineaHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            lineaHorizontal.setGravity(Gravity.CENTER_VERTICAL);

            if(card.getIcon() == 1){

                botonImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_card, null));

            } else if (card.getIcon() == 2){

                botonImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_circle, null));

            } else {

                botonImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arroba, null));

            }


            final int numIdCard = card.getCardId();

            txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                }
            });

            botonImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                }
            });

            lineaHorizontal.addView(botonImage);
            lineaHorizontal.addView(txt);

            linearLayoutCards.addView(lineaHorizontal);

            LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            View lineaSpace = new View(this);
            lineaSpace.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

            linearLayoutCards.addView(lineaSpace, spaceParams);


        }

        Button dessacociateCardsButton = new Button(this);
        dessacociateCardsButton.setText(getResources().getString(R.string.disassociate_from_all_cards));
        LinearLayout.LayoutParams buttonDessaParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonDessaParams.setMargins(0,10,0,10);
        dessacociateCardsButton.setBackground(ContextCompat.getDrawable(this, R.drawable.boton_estilo_cancel));
        dessacociateCardsButton.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        linearLayoutCards.addView(dessacociateCardsButton, buttonDessaParams);

        dessacociateCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToDissociate = new Intent(getApplicationContext(), EnterPinToDisassociateService.class);
                goToDissociate.putExtra("providerId", providerId);
                startActivity(goToDissociate);

            }
        });


    }
}
package com.acquanero.ezcard.ui.cards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acquanero.ezcard.AddNewCardActivity;
import com.acquanero.ezcard.EditCardActivity;
import com.acquanero.ezcard.R;
import com.acquanero.ezcard.model.Card;
import com.acquanero.ezcard.model.UserData;
import com.google.gson.Gson;

public class CardsFragment extends Fragment {

    SharedPreferences dataDepot;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cards, container, false);

        //Creo una instancia de SahredPreference para almacenar informacion
        //el archivo se encuentra en /data/data/[nombre del proyecto]/shared_prefs/archivo.xml
        dataDepot = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String userJson = dataDepot.getString("usuario", "null");
        Gson gson = new Gson();
        UserData userData = gson.fromJson(userJson, UserData.class);

        View butonAddCard = root.findViewById(R.id.buttonAddCard);

        butonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), AddNewCardActivity.class);
                startActivity(i);

            }
        });


        //Guardo el LinearLayout del CardsActivity en una variable
        LinearLayout linearLayoutCards = (LinearLayout) root.findViewById(R.id.linearLayoutCards);


        //Con un for each recorro la lista de tarjetas y genero el imageButton con un label por cada tarjeta
        //y los inserto en un linearLayout horizontal
        //y a su vez este ultimo, lo inserto en cada renglon del linearLayout principal
        for (Card card : userData.getCards()){



            ImageView botonImage = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
            botonImage.setLayoutParams(layoutParams);

            TextView txt = new TextView(getActivity());
            txt.setText(card.getName());
            txt.setGravity(Gravity.CENTER);

            LinearLayout lineaHorizontal = new LinearLayout(getContext());
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

                Context context = getContext();

                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, EditCardActivity.class);
                    i.putExtra("cardid", numIdCard);
                    startActivity(i);

                }
            });

            botonImage.setOnClickListener(new View.OnClickListener() {

                Context context = getContext();

                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, EditCardActivity.class);
                    i.putExtra("cardid", numIdCard);
                    startActivity(i);

                }
            });

            lineaHorizontal.addView(botonImage);
            lineaHorizontal.addView(txt);

            linearLayoutCards.addView(lineaHorizontal);

            LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            View lineaSpace = new View(getActivity());
            lineaSpace.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

            linearLayoutCards.addView(lineaSpace, spaceParams);


        }

        return root;
    }
}
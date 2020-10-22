package com.acquanero.ezcard.ui.cards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acquanero.ezcard.EditCardActivity;
import com.acquanero.ezcard.R;
import com.acquanero.ezcard.RegisterStepTwo;
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


        //Guardo el GridLayout del CardsActivity en una variable
        GridLayout gridCards = (GridLayout) root.findViewById(R.id.gridCardsz);

        //Con un for each recorro la lista de tarjetas y genero el imageButton con un label por cada tarjeta
        //y los inserto en un linearLayout vertical
        //y a su vez este ultimo, lo inserto en cada celda del GridLayout
        for (Card card : userData.getCards()){

            LinearLayout linearLayoutInsideGrid = new LinearLayout(getActivity());
            LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
            linearLayoutInsideGrid.setOrientation(LinearLayout.VERTICAL);
            linearLayoutInsideGrid.setGravity(Gravity.CENTER);
            paramsLinear.setMargins(0, 0, 55, 20);

            Button botonImage = new Button(getActivity());
            //botonImage.setText(card.getName());
            TextView txt = new TextView(getActivity());
            txt.setText(card.getName());
            txt.setGravity(Gravity.CENTER);
            botonImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_background, null));

            final int numIdCard = card.getCardId();

            botonImage.setOnClickListener(new View.OnClickListener() {

                Context context = getContext();

                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, EditCardActivity.class);
                    i.putExtra("cardid", numIdCard);
                    startActivity(i);

                }
            });

            linearLayoutInsideGrid.addView(botonImage, paramsLinear);
            linearLayoutInsideGrid.addView(txt,paramsLinear);

            gridCards.addView(linearLayoutInsideGrid);


        }

        return root;
    }
}
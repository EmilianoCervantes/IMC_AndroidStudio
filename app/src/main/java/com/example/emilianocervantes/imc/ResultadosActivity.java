package com.example.emilianocervantes.imc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadosActivity extends Activity {

    private TextView nombre, imc, ideal, energy;
    private ImageView imageView;

    private boolean mujer = true;

    private final int REQUEST_CODE = 7007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        nombre = (TextView)findViewById(R.id.nombreUser);
        imc = (TextView)findViewById(R.id.imcUser);
        ideal = (TextView)findViewById(R.id.pesoIdeal);
        energy = (TextView)findViewById(R.id.energiaGastar);
        imageView = (ImageView)findViewById(R.id.imagePeso);

        //Intent de activity_main
        Persona p = (Persona)getIntent().getSerializableExtra("persona");
        if(!p.sexo.equalsIgnoreCase("mujer")){
            mujer = false;
        } else {
            mujer = true;
        }

        calcular(p);
    }

    public void recalcula(View view){
        Intent intent = new Intent(this,DatosNuevosActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if (data.hasExtra("persona")){
                Persona p = (Persona)data.getSerializableExtra("persona");
                calcular(p);
            }
        }
    }

    //Para no repetir code
    private void calcular(Persona p){
        double imcCalc = p.peso/Math.pow(p.altura,2.0);
        double idealCalc = Math.pow(p.altura,2.0)*22;
        double energiaCalc = idealCalc * 30;

        nombre.setText(p.nombre);
        imc.setText("Tu IMC es: "+String.valueOf(imcCalc));
        ideal.setText("Tu peso ideal es de: "+String.valueOf(idealCalc)+" kg");
        energy.setText("La energía a gastar es de: "+String.valueOf(energiaCalc)+" kcal");

        if(mujer){
            if(imcCalc <= 17.5){
                imageView.setImageResource(R.drawable.woman_bmi_17_5);
            } else if (imcCalc > 17.5 && imcCalc <= 18.5){
                imageView.setImageResource(R.drawable.woman_bmi_18_5);
            } else if (imcCalc > 18.5 && imcCalc <= 22.0){
                imageView.setImageResource(R.drawable.woman_bmi_22);
            } else if (imcCalc > 22.0 && imcCalc <= 24.9){
                imageView.setImageResource(R.drawable.woman_bmi_24_9);
            } else if (imcCalc > 24.9 && imcCalc <= 30.0){
                imageView.setImageResource(R.drawable.woman_bmi_30);
            } else {
                imageView.setImageResource(R.drawable.woman_bmi_40);
            }
        } else {
            if(imcCalc <= 17.5){
                imageView.setImageResource(R.drawable.men_bmi_17_5);
            } else if (imcCalc > 17.5 && imcCalc <= 18.5){
                imageView.setImageResource(R.drawable.men_bmi_18_5);
            } else if (imcCalc > 18.5 && imcCalc <= 22.0){
                imageView.setImageResource(R.drawable.men_bmi_22_0);
            } else if (imcCalc > 22.0 && imcCalc <= 24.9){
                imageView.setImageResource(R.drawable.men_bmi_24_9);
            } else if (imcCalc > 24.9 && imcCalc <= 30.0){
                imageView.setImageResource(R.drawable.men_bmi_30);
            } else {
                imageView.setImageResource(R.drawable.men_bmi_40);
            }
        }
    }

    public void openWeb(View view){
        String url = "https://es.wikipedia.org/wiki/Índice_de_masa_corporal";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void share(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Message");
        intent.putExtra(Intent.EXTRA_TEXT, "Mi IMC es : "+imc.getText().toString());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share with:"));

    }
}

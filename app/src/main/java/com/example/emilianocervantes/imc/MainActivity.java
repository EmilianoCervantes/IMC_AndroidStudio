package com.example.emilianocervantes.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private EditText nombre, peso, altura;
    private Spinner sexo;
    private String sexoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText)findViewById(R.id.nombre);
        peso = (EditText)findViewById(R.id.peso);
        altura = (EditText)findViewById(R.id.altura);
        sexo = (Spinner)findViewById(R.id.sexo);
        final String[] sexos = {"Mujer", "Hombre"};
        //ArrayAdapter<T> es para poderle dar datos al Spinner:
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sexos);
        //Todo esto es nada más la configuracion del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sexoSeleccionado = sexos[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Así se lo da al spinner:
        sexo.setAdapter(adapter);
    }

    public void imc(View view){
        Persona persona = new Persona();
        if(TextUtils.isEmpty(nombre.getText())){
            nombre.setError("Ingresa tu nombre");
            return;
        }
        if(TextUtils.isEmpty(peso.getText())){
            peso.setError("Ingresa tu peso");
            return;
        }
        if(TextUtils.isEmpty(altura.getText())){
            altura.setError("Ingresa tu altura");
            return;
        }
        persona.nombre = nombre.getText().toString();
        persona.peso = Double.parseDouble(peso.getText().toString());
        persona.altura = Double.parseDouble(altura.getText().toString());
        persona.sexo = sexoSeleccionado;
        /*if(TextUtils.isEmpty(sexoSeleccionado)){
            sexo.setError("Selecciona tu sexo");
            return;
        }*/
        Intent intent = new Intent(MainActivity.this,ResultadosActivity.class);
        intent.putExtra("persona", persona);
        startActivity(intent);
    }
}

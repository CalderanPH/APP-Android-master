package br.paulocalderan.gestaofinanceira;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ListView listViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        listViewUsuario = findViewById(R.id.listViewUsuario);

        listViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                Object usuarios = listViewUsuario.getItemAtPosition(position);

                Toast.makeText(SecondActivity.this,
                        usuarios + getString(R.string.foi_clicado),
                        Toast.LENGTH_SHORT).show();

            }
        });
        popularLista();
    }

    private void popularLista() {
        String[] nomes = getResources().getStringArray(R.array.nomes);
        String[] generos = getResources().getStringArray(R.array.generos);
        int[] idades = getResources().getIntArray(R.array.idades);
        int[] salarios = getResources().getIntArray(R.array.salarios);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (int cont = 0; cont < nomes.length; cont++){
            usuarios.add(new Usuario(
                    nomes[cont],
                    generos[cont],
                    idades[cont],
                    salarios[cont])
            );
        }
        ArrayAdapter<Usuario> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        usuarios);

        listViewUsuario.setAdapter(adapter);
    }


}
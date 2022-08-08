package br.paulocalderan.gestaofinanceira;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewUsuarios;
    private ArrayAdapter<Usuario> listAdapter;
    private ArrayList<Usuario> listUsuarios;
    public static final int NOVO = 1;

    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewUsuarios = findViewById(R.id.listViewUsuario);
        listViewUsuarios.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        posicaoSelecionada = position;

                    }
                }
        );
        popularLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSalvar:
                Intent i = new Intent(this, UsuarioActivity.class);
                startActivityForResult(i, R.layout.activity_usuario);
                break;
            case R.id.menuSobre:
                Intent intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void popularLista() {
        listUsuarios = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listUsuarios);

        listViewUsuarios.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();

            String nome = bundle.getString(UsuarioActivity.NOME);
            String genero = bundle.getString(UsuarioActivity.GENERO);
            int idade = bundle.getInt(String.valueOf(UsuarioActivity.IDADE));
            double salario = bundle.getInt(String.valueOf(UsuarioActivity.SALARIO));

            listUsuarios.add(new Usuario(nome, genero, idade, salario));
        }

        listAdapter.notifyDataSetChanged();
    }
}




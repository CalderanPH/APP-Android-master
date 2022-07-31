package br.paulocalderan.gestaofinanceira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewUsuarios;
    private ArrayAdapter<Usuario> listAdapter;
    private ArrayList<Usuario> listUsuarios;

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

    private void popularLista() {
        listUsuarios = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listUsuarios);

        listViewUsuarios.setAdapter(listAdapter);
    }

    public void adicionarUser(View view) {
        UsuarioActivity.cadastrar(this);
    }


    public void abrirSobre(View view) {
        AutoriaActivity.autoria(this);
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




package br.paulocalderan.gestaofinanceira;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {
    private Switch aSwitch;
    SharedPref sharedPref;

    private ListView listViewUsuarios;
    private ArrayAdapter<Usuario> listAdapter;
    private ArrayList<Usuario> listUsuarios;

    private ActionMode actionMode;
    private int posicaoSelecionada = -1;
    private View viewSelecionada;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.menu_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuItemAlterar:
                    alterar();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluir();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewUsuarios.setEnabled(true);
        }
    };

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
                        alterar();
                    }
                }
        );
        listViewUsuarios.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewUsuarios.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null) {
                            return false;
                        }

                        posicaoSelecionada = position;

                        viewSelecionada = view;

                        listViewUsuarios.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });

        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightState() == true) {
            setTheme(R.style.Theme_GestaoFinanceira);
        } else setTheme(R.style.Theme_GestaoFinanceira);

        aSwitch = findViewById(R.id.switch1);

        if (sharedPref.loadNightState() == true) {
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        aSwitch.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            if (aSwitch.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                sharedPref.setNightModeState(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                sharedPref.setNightModeState(false);
            }
        }));

        popularLista();
    }

    private void popularLista() {
        listUsuarios = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listUsuarios);

        listViewUsuarios.setAdapter(listAdapter);
    }

    private void excluir() {
        listUsuarios.remove(posicaoSelecionada);

        listAdapter.notifyDataSetChanged();
    }

    private void alterar() {
        Usuario usuario = listUsuarios.get(posicaoSelecionada);

        UsuarioActivity.alterarUsuario(this, usuario);
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

            if (requestCode == UsuarioActivity.ALTERAR) {
                Usuario usuario = listUsuarios.get(posicaoSelecionada);
                usuario.setNome(nome);
                usuario.setIdade(idade);
                usuario.setSalario(salario);
                usuario.setGenero(genero);

                posicaoSelecionada = -1;

            } else {
                listUsuarios.add(new Usuario(nome, genero, idade, salario));
            }
        }

        listAdapter.notifyDataSetChanged();
        setResult(Activity.RESULT_OK, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemNovo:
                UsuarioActivity.novoUsuario(this);
                return true;
            case R.id.menuSobre:
                Intent intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
    }
}




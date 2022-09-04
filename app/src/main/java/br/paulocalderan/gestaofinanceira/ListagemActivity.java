package br.paulocalderan.gestaofinanceira;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import br.paulocalderan.gestaofinanceira.domain.Usuario;
import br.paulocalderan.gestaofinanceira.persistencia.UsuarioDatabase;
import br.paulocalderan.gestaofinanceira.util.SharedPref;
import br.paulocalderan.gestaofinanceira.util.UtilsGUI;

public class ListagemActivity extends AppCompatActivity {
    private static final int REQUEST_NOVA_PESSOA = 1;
    private static final int REQUEST_ALTERAR_PESSOA = 2;

    private Switch aSwitch;
    SharedPref sharedPref;

    private ListView listViewUsuarios;
    private ArrayAdapter<Usuario> listAdapter;

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
                        Usuario usuario = (Usuario) parent.getItemAtPosition(position);
                        UsuarioActivity.alterarUsuario(ListagemActivity.this,
                                REQUEST_ALTERAR_PESSOA,
                                usuario);
                    }
                }
        );

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

        registerForContextMenu(listViewUsuarios);
    }

    private void popularLista() {

        UsuarioDatabase database = UsuarioDatabase.getDatabase(this);
        List<Usuario> list = database.usuarioDao().queryAll();

        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                list);

        listViewUsuarios.setAdapter(listAdapter);
    }

    private void excluir(final Usuario usuario) {
        String mensagem = getString(R.string.want_to_really_delete) + "\n" + usuario.getNome();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                UsuarioDatabase database =
                                        UsuarioDatabase.getDatabase(ListagemActivity.this);

                                database.usuarioDao().delete(usuario);

                                listAdapter.remove(usuario);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_NOVA_PESSOA || requestCode == REQUEST_ALTERAR_PESSOA) &&
                resultCode == Activity.RESULT_OK) {

            popularLista();
        }
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
                UsuarioActivity.novoUsuario(this, REQUEST_NOVA_PESSOA);
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
        getMenuInflater().inflate(R.menu.menu_item_selecionado, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Usuario usuario = (Usuario) listViewUsuarios.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.menuItemAlterar:
                UsuarioActivity.alterarUsuario(this, REQUEST_ALTERAR_PESSOA, usuario);
                return true;
            case R.id.menuItemExcluir:
                excluir(usuario);
                return true;
            case R.id.menuSobre:
                Intent intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}




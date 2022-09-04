package br.paulocalderan.gestaofinanceira;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.paulocalderan.gestaofinanceira.domain.Usuario;
import br.paulocalderan.gestaofinanceira.persistencia.UsuarioDatabase;

public class UsuarioActivity extends AppCompatActivity {
    public static final String NOME = "NOME";
    public static final String ID = "ID";
    public static final int IDADE = 0;
    public static final String GENERO = "GENERO";
    public static final double SALARIO = 0;

    public static final String MODO = "MODO";
    public static final int ALTERAR = 2;
    public static final int NOVO = 1;

    private int modo;
    private String nomeOriginal;
    private String idadeOriginal;
    private String salarioOriginal;
    private int generoOriginal;
    private Usuario usuario;


    private CheckBox cbAluguel, cbMercado;
    private RadioGroup radioGroupSex;
    private Spinner spinner;
    private EditText editTextNome, editTextIdade, editTextSalario;
    private RadioButton radioButtonMas, radioButtonFem;

    public static void novoUsuario(AppCompatActivity activity, int requestCode) {
        Intent intent = new Intent(activity, UsuarioActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void alterarUsuario(AppCompatActivity activity, int requestCode, Usuario usuario) {

        Intent intent = new Intent(activity, UsuarioActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, usuario.getId());

        activity.startActivityForResult(intent, requestCode);

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        cbAluguel = findViewById(R.id.checkBoxAluguel);
        cbMercado = findViewById(R.id.checkBoxMercado);
        spinner = findViewById(R.id.spinner);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        editTextNome = findViewById(R.id.editTextNomeUser);
        editTextIdade = findViewById(R.id.editTextIdadeUser);
        editTextSalario = findViewById(R.id.editTextSalUser);
        radioButtonMas = findViewById(R.id.radioButtonMas);
        radioButtonFem = findViewById(R.id.radioButtonFem);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO, NOVO);
        if (modo == ALTERAR) {
            setTitle(getString(R.string.alterar_usuario));

            int id = bundle.getInt(ID);

            UsuarioDatabase database = UsuarioDatabase.getDatabase(this);
            usuario = database.usuarioDao().queryForId((long) id);

            nomeOriginal = bundle.getString(NOME);
            idadeOriginal = String.valueOf(bundle.getInt(String.valueOf(IDADE)));
            salarioOriginal = String.valueOf(bundle.getDouble(String.valueOf(SALARIO)));
            generoOriginal = bundle.getInt(GENERO);

            editTextNome.setText(nomeOriginal);
            editTextIdade.setText(idadeOriginal);
            editTextSalario.setText(salarioOriginal);

            RadioButton button;
            switch (generoOriginal) {
                case Usuario.MASCULINO:
                    button = findViewById(R.id.radioButtonMas);
                    button.setChecked(true);
                    break;

                case Usuario.FEMININO:
                    button = findViewById(R.id.radioButtonFem);
                    button.setChecked(true);
                    break;
            }

        } else {
            setTitle(getString(R.string.novo_usuario));

            usuario = new Usuario();
        }

        popularSpinner();
    }

    private void salvar() {
        Intent intent = new Intent();

        //Edit Text Nome
        String nome = editTextNome.getText().toString();
        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.error_nome,
                    Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        int idade = Integer.parseInt(editTextIdade.getText().toString());
        int salario = Integer.parseInt(editTextSalario.getText().toString());

        if (modo == ALTERAR && nome.equals(nomeOriginal)) {
            cancelar();
            return;
        }

        //RadioButton
        String mensagem3 = "";
        switch (radioGroupSex.getCheckedRadioButtonId()) {
            case R.id.radioButtonMas:
                CharSequence mas = radioButtonMas.getText();
                intent.putExtra(GENERO, mas);
                usuario.setGenero(String.valueOf(mas));
                mensagem3 = getString(R.string.radioMas) +
                        getString(R.string.foi_selecionado);
                break;

            case R.id.radioButtonFem:
                CharSequence fem = radioButtonFem.getText();
                intent.putExtra(GENERO, fem);
                usuario.setGenero(String.valueOf(fem));
                mensagem3 = getString(R.string.radioFem) +
                        getString(R.string.foi_selecionado);
                break;

            default:
                mensagem3 = getString(R.string.nada_selecionado);
        }
        Toast.makeText(this, mensagem3, Toast.LENGTH_SHORT).show();

        //Check Box
        String mensagem = "";

        if (cbAluguel.isChecked()) {
            mensagem += getString(R.string.checkAluguel) + "\n";
        }
        if (cbMercado.isChecked()) {
            mensagem += getString(R.string.checkMercado) + "\n";
        }
        if (mensagem.equals("")) {
            mensagem = getString(R.string.nenhuma_opcao);
        } else {
            mensagem = getString(R.string.selecionado) + "\n" + mensagem;
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

        //spinner
        String mensagem2 = "";

        String ling = (String) spinner.getSelectedItem();
        if (ling != null) {
            mensagem2 = ling + getString(R.string.foiSelecionado);
        } else {
            mensagem2 = getString(R.string.nenhuma_op);
        }
        Toast.makeText(this, mensagem2, Toast.LENGTH_SHORT).show();

        intent.putExtra(NOME, nome);
        intent.putExtra(String.valueOf(IDADE), idade);
        intent.putExtra(String.valueOf(SALARIO), salario);

        usuario.setNome(nome);
        usuario.setIdade(idade);
        usuario.setSalario(salario);

        UsuarioDatabase database = UsuarioDatabase.getDatabase(this);

        if (modo == NOVO) {
            database.usuarioDao().insert(usuario);
        } else {
            database.usuarioDao().update(usuario);
        }

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void desmarcarTodos() {
        editTextNome.setText(null);
        editTextIdade.setText(null);
        editTextSalario.setText(null);
        cbAluguel.setChecked(false);
        cbMercado.setChecked(false);
        radioGroupSex.clearCheck();

        editTextNome.requestFocus();

        String mensagem = "";
        mensagem = getString(R.string.mensagem_desmarcar);

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void popularSpinner() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("selecione uma data");
        lista.add(getString(R.string.venc1));
        lista.add(getString(R.string.venc2));
        lista.add(getString(R.string.venc3));
        lista.add(getString(R.string.venc4));
        lista.add(getString(R.string.venc5));
        lista.add(getString(R.string.venc6));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, lista);

        spinner.setAdapter(adapter);
    }

    public void cancelar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuItemSalvar:
                salvar();
                return true;

            case R.id.menuItemLimpar:
                desmarcarTodos();
                return true;

            case android.R.id.home:

            case R.id.menuItemCancelar:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
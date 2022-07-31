package br.paulocalderan.gestaofinanceira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UsuarioActivity extends AppCompatActivity {
    public static final String NOME = "NOME";
    public static final int IDADE = 0;
    public static final double SALARIO = 0;
    public static final String GENERO = "GENERO";
    public static final int NOVO = 1;

    private CheckBox cbAluguel, cbMercado;
    private RadioGroup radioGroupSex;
    private Spinner spinner;
    private EditText editTextNome, editTextIdade, editTextSalario;
    private RadioButton radioButtonMas, radioButtonFem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        cbAluguel = findViewById(R.id.checkBoxAluguel);
        cbMercado = findViewById(R.id.checkBoxMercado);
        spinner = findViewById(R.id.spinner);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        editTextNome = findViewById(R.id.editTextNomeUser);
        editTextIdade = findViewById(R.id.editTextIdadeUser);
        editTextSalario = findViewById(R.id.editTextSalUser);
        radioButtonMas = findViewById(R.id.radioButtonMas);
        radioButtonFem = findViewById(R.id.radioButtonFem);

        popularSpinner();
    }

    public static void cadastrar(AppCompatActivity activity) {
        Intent intent = new Intent(activity, UsuarioActivity.class);
        intent.putExtra("", NOVO);
        activity.startActivityForResult(intent, NOVO);
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

    public void salvar(View view) {
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


        //RadioButton
        String mensagem3 = "";
        switch (radioGroupSex.getCheckedRadioButtonId()) {
            case R.id.radioButtonMas:
                CharSequence mas = radioButtonMas.getText();
                intent.putExtra(GENERO, mas);
                mensagem3 = getString(R.string.radioMas) +
                        getString(R.string.foi_selecionado);
                break;

            case R.id.radioButtonFem:
                CharSequence fem = radioButtonFem.getText();
                intent.putExtra(GENERO, fem);
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


        setResult(Activity.RESULT_OK, intent);

        finish();


    }

    public void cancelar(View view) {
        onBackPressed();
    }

    public void desmarcarTodos(View view) {
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

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}
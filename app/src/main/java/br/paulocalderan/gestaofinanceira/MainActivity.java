package br.paulocalderan.gestaofinanceira;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbAluguel, cbMercado;
    private RadioGroup radioGroupSex;
    private Spinner spinner;
    private EditText editTextNome;
    private EditText editTextIdade;
    private EditText editTextGenero;
    private EditText editTextSalario;

    Button button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbAluguel = findViewById(R.id.checkBoxAluguel);
        cbMercado = findViewById(R.id.checkBoxMercado);
        spinner = findViewById(R.id.spinner);
        radioGroupSex = findViewById(R.id.radioGroup);
        editTextNome = findViewById(R.id.editTextNomeUser);
        editTextIdade = findViewById(R.id.editTextIdadeUser);
        editTextGenero = findViewById(R.id.editTextGenUser);
        editTextSalario = findViewById(R.id.editTextSalUser);
        button = findViewById(R.id.btnListView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        popularSpinner();
    }

    private void popularSpinner() {
        ArrayList<String> lista = new ArrayList<>();
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
        //Edit Text Nome
        String nome = editTextNome.getText().toString();
        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.error_nome,
                    Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        Integer.parseInt(editTextIdade.getText().toString());
        editTextGenero.getText().toString();
        Integer.parseInt(editTextSalario.getText().toString());

        Toast.makeText(this,
                nome.trim(),
                Toast.LENGTH_SHORT).show();

        //RadioButton
        String mensagem3 = "";
        switch (radioGroupSex.getCheckedRadioButtonId()) {
            case R.id.radioButtonMas:
                int mas = R.id.radioButtonMas;
                mensagem3 = getString(R.string.radioMas) +
                        getString(R.string.foi_selecionado);
                break;

            case R.id.radioButtonFem:
                int fem = R.id.radioButtonFem;
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

    }

    public void desmarcarTodos(View view) {
        editTextNome.setText(null);
        cbAluguel.setChecked(false);
        cbMercado.setChecked(false);
        radioGroupSex.clearCheck();

        editTextNome.requestFocus();

        String mensagem = "";
        mensagem = getString(R.string.mensagem_desmarcar);

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();


    }

}
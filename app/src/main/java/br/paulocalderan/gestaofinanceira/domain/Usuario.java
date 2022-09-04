package br.paulocalderan.gestaofinanceira.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    public static final int MASCULINO = 1;
    public static final int FEMININO = 2;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private String genero;
    private int idade;
    private double Salario;

    public Usuario(String nome, String genero, int idade, double salario) {
        this.nome = nome;
        this.genero = genero;
        this.idade = idade;
        Salario = salario;
    }

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getSalario() {
        return Salario;
    }

    public void setSalario(double salario) {
        Salario = salario;
    }

    @Override
    public String toString() {
        return getNome()
                + " / "
                + getGenero()
                + " / idade: "
                + getIdade()
                + " / salario: "
                + getSalario();
    }

}

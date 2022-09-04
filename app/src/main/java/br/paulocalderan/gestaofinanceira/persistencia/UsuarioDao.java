package br.paulocalderan.gestaofinanceira.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.paulocalderan.gestaofinanceira.domain.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    long insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM usuario WHERE id = :id")
    Usuario queryForId(Long id);

    @Query("SELECT * FROM usuario ORDER BY nome ASC")
    List<Usuario> queryAll();

}
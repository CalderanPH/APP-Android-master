package br.paulocalderan.gestaofinanceira.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.paulocalderan.gestaofinanceira.domain.Usuario;

@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class UsuarioDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();

    private static UsuarioDatabase instance;

    public static UsuarioDatabase getDatabase(final Context context) {
        if (instance == null) {

            synchronized (UsuarioDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            UsuarioDatabase.class,
                            "usuario.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }

}
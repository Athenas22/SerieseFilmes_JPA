package repositorio;

import java.util.List;
import modelo.Genero;
import util.Util;

public class RepositorioGenero extends Repositorio<Genero> {

    @Override
    public Genero localizar(Object chave) {
        // Encontra um género pelo ID (chave primária)
        return Util.getManager().find(Genero.class, chave);
    }

    @Override
    public List<Genero> listar() {
        // Lista todos os géneros cadastrados
        return Util.getManager().createQuery("select g from Genero g", Genero.class).getResultList();
    }
}
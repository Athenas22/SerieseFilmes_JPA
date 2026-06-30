package repositorio;

import java.util.List;
import modelo.Genero;
import util.Util;

public class RepositorioGenero extends Repositorio<Genero> {

    @Override
    public Genero localizar(Object chave) {
        return Util.getManager().find(Genero.class, chave);
    }

    @Override
    public List<Genero> listar() {
        return Util.getManager().createQuery("select g from Genero g", Genero.class).getResultList();
    }
}

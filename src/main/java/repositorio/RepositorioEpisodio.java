package repositorio;

import java.util.List;
import modelo.Episodio;
import util.Util;

public class RepositorioEpisodio extends Repositorio<Episodio> {

    @Override
    public Episodio localizar(Object chave) {
        // Encontra um episódio pelo ID (chave primária)
        return Util.getManager().find(Episodio.class, chave);
    }

    @Override
    public List<Episodio> listar() {
        // Lista todos os episódios cadastrados
        return Util.getManager().createQuery("select e from Episodio e", Episodio.class).getResultList();
    }
}

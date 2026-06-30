package repositorio;

import java.util.List;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException; 
import modelo.Serie;
import util.Util;

public class RepositorioSerie extends Repositorio<Serie> {

    @Override
    public Serie localizar(Object chave) {
        return Util.getManager().find(Serie.class, chave);
    }

    @Override
    public List<Serie> listar() {
        return Util.getManager().createQuery("select s from Serie s", Serie.class).getResultList();
    }


    public Serie buscarPorNome(String nome) {
        try {
            TypedQuery<Serie> q = Util.getManager().createQuery(
                    "select s from Serie s where s.nome = :nome", 
                    Serie.class);
            q.setParameter("nome", nome);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }

    public List<Serie> consultarSeriesPorAno(int ano) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s where EXTRACT(YEAR FROM s.dataLancamento) = :ano", 
                Serie.class);
        q.setParameter("ano", ano);
        return q.getResultList();
    }

    public List<Serie> consultarSeriesPorGenero(String nomeGenero) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s join s.generos g where g.nome = :nome", 
                Serie.class);
        q.setParameter("nome", nomeGenero);
        return q.getResultList();
    }

    public List<Serie> consultarSeriesComMaisDeNEpisodios(int n) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s where size(s.episodios) > :n", 
                Serie.class);
        q.setParameter("n", n);
        return q.getResultList();
    }
}

package repositorio;

import java.util.List;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException; // Importação muito importante!
import modelo.Serie;
import util.Util;

public class RepositorioSerie extends Repositorio<Serie> {

    // ====================================================================
    // MÉTODOS ABSTRATOS OBRIGATÓRIOS
    // ====================================================================

    @Override
    public Serie localizar(Object chave) {
        // Encontra uma série no banco pela chave primária (ID)
        return Util.getManager().find(Serie.class, chave);
    }

    @Override
    public List<Serie> listar() {
        // Retorna todas as séries do banco
        return Util.getManager().createQuery("select s from Serie s", Serie.class).getResultList();
    }

    // ====================================================================
    // MÉTODOS DE BUSCA ESPECÍFICOS
    // ====================================================================

    // NOVO MÉTODO: Adicionado para a classe RequisitoSerie conseguir funcionar!
    public Serie buscarPorNome(String nome) {
        try {
            TypedQuery<Serie> q = Util.getManager().createQuery(
                    "select s from Serie s where s.nome = :nome", 
                    Serie.class);
            q.setParameter("nome", nome);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna nulo pacificamente se a série não existir
        }
    }

    // Consulta 1: Séries do ano X
    public List<Serie> consultarSeriesPorAno(int ano) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s where EXTRACT(YEAR FROM s.dataLancamento) = :ano", 
                Serie.class);
        q.setParameter("ano", ano);
        return q.getResultList();
    }

    // Consulta 2: Séries do género de nome X
    public List<Serie> consultarSeriesPorGenero(String nomeGenero) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s join s.generos g where g.nome = :nome", 
                Serie.class);
        q.setParameter("nome", nomeGenero);
        return q.getResultList();
    }

    // Consulta 3: Séries com mais de N episódios
    public List<Serie> consultarSeriesComMaisDeNEpisodios(int n) {
        TypedQuery<Serie> q = Util.getManager().createQuery(
                "select s from Serie s where size(s.episodios) > :n", 
                Serie.class);
        q.setParameter("n", n);
        return q.getResultList();
    }
}
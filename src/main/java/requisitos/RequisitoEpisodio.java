package requisitos;

import java.util.List;
import modelo.Episodio;
import repositorio.RepositorioEpisodio;

public class RequisitoEpisodio {

    private RepositorioEpisodio repoEpisodio;

    public RequisitoEpisodio() {
        this.repoEpisodio = new RepositorioEpisodio();
    }

    /**
     * Lista todos os episódios cadastrados no banco de dados.
     * @return Lista de episódios.
     */
    public List<Episodio> listarTodos() {
        return repoEpisodio.listar();
    }

    /**
     * Exclui um episódio específico do sistema pelo seu ID.
     * @param id ID do episódio.
     * @throws Exception Se o episódio não for encontrado ou houver erro na transação.
     */
    public void excluirEpisodio(int id) throws Exception {
        RepositorioEpisodio.begin();
        try {
            Episodio e = repoEpisodio.localizar(id);
            if (e == null) {
                throw new Exception("Episódio não encontrado para exclusão.");
            }

            // Remove o vínculo com a série antes de deletar o episódio, se aplicável
            if (e.getSerie() != null) {
                e.getSerie().remover(e);
            }

            repoEpisodio.deletar(e);
            RepositorioEpisodio.commit();
        } catch (Exception e) {
            RepositorioEpisodio.rollback();
            throw e;
        }
    }
}
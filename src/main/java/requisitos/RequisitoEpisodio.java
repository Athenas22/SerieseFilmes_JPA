package requisitos;

import java.util.List;
import modelo.Episodio;
import repositorio.RepositorioEpisodio;

public class RequisitoEpisodio {

    private RepositorioEpisodio repoEpisodio;

    public RequisitoEpisodio() {
        this.repoEpisodio = new RepositorioEpisodio();
    }

    public List<Episodio> listarTodos() {
        return repoEpisodio.listar();
    }


    public void excluirEpisodio(int id) throws Exception {
        RepositorioEpisodio.begin();
        try {
            Episodio e = repoEpisodio.localizar(id);
            if (e == null) {
                throw new Exception("Episódio não encontrado para exclusão.");
            }

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

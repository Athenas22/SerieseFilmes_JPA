package requisitos;

import java.util.List;
import modelo.Genero;
import repositorio.RepositorioGenero;

public class RequisitoGenero {

    private RepositorioGenero repoGenero;

    public RequisitoGenero() {
        this.repoGenero = new RepositorioGenero();
    }


    public void cadastrarGenero(String nome) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do gênero não pode estar vazio.");
        }

        RepositorioGenero.begin();
        try {
            List<Genero> todos = repoGenero.listar();
            for (Genero g : todos) {
                if (g.getNome().equalsIgnoreCase(nome.trim())) {
                    throw new Exception("O gênero '" + nome + "' já está cadastrado.");
                }
            }

            Genero novo = new Genero(nome.trim());
            repoGenero.criar(novo);
            RepositorioGenero.commit();
        } catch (Exception e) {
            RepositorioGenero.rollback();
            throw e;
        }
    }

    /**
     * Exclui um gênero do sistema pelo seu ID.
     * @param id ID do gênero a ser excluído.
     * @throws Exception Se o gênero não for encontrado ou se houver erro ao deletar.
     */
    public void excluirGenero(int id) throws Exception {
        RepositorioGenero.begin();
        try {
            Genero g = repoGenero.localizar(id);
            if (g == null) {
                throw new Exception("Gênero não encontrado para exclusão.");
            }

            // Regra de negócio: impede excluir gênero se ele ainda estiver vinculado a alguma série
            if (!g.getSeries().isEmpty()) {
                throw new Exception("Não é possível excluir o gênero '" + g.getNome() + "' porque ele está associado a séries.");
            }

            repoGenero.deletar(g);
            RepositorioGenero.commit();
        } catch (Exception e) {
            RepositorioGenero.rollback();
            throw e;
        }
    }

    /**
     * Lista todos os gêneros cadastrados no banco de dados.
     * @return Lista de gêneros.
     */
    public List<Genero> listarTodos() {
        return repoGenero.listar();
    }
}

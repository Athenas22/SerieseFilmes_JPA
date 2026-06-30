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


    public void excluirGenero(int id) throws Exception {
        RepositorioGenero.begin();
        try {
            Genero g = repoGenero.localizar(id);
            if (g == null) {
                throw new Exception("Gênero não encontrado para exclusão.");
            }

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


    public List<Genero> listarTodos() {
        return repoGenero.listar();
    }
}

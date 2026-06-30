package requisitos;

import java.time.LocalDate;
import java.util.List;
import modelo.Episodio;
import modelo.Genero;
import modelo.Serie;
import repositorio.RepositorioGenero;
import repositorio.RepositorioSerie;

public class RequisitoSerie {
    
    private RepositorioSerie repoSerie;
    private RepositorioGenero repoGenero;

    public RequisitoSerie() {
        this.repoSerie = new RepositorioSerie();
        this.repoGenero = new RepositorioGenero();
    }


    public void cadastrarSerie(String nome, LocalDate dataLancamento, byte[] foto) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome da série não pode estar vazio.");
        }
        if (dataLancamento == null) {
            throw new Exception("A data de lançamento deve ser informada.");
        }

        RepositorioSerie.begin();
        try {
            Serie existente = repoSerie.buscarPorNome(nome);
            if (existente != null) {
                throw new Exception("Já existe uma série cadastrada com o nome: " + nome);
            }

            Serie nova = new Serie(nome, dataLancamento);
            if (foto != null) {
                
            }

            repoSerie.criar(nova);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }

    public void removerPrimeiroGenero(String nomeSerie) throws Exception {
        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie); 
            if (s == null) {
                throw new Exception("Série não encontrada no banco de dados.");
            }

            if (!s.getGeneros().isEmpty()) {
                Genero g = s.getGeneros().getFirst(); 
                s.remover(g); 
                
                repoSerie.atualizar(s);
                RepositorioSerie.commit();
            } else {
                throw new Exception("A série '" + s.getNome() + "' não possui gêneros associados.");
            }
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e; 
        }
    }


    public void adicionarGeneroASerie(String nomeSerie, String nomeGenero) throws Exception {
        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie);
            if (s == null) {
                throw new Exception("Série não encontrada.");
            }

            Genero g = null;
            List<Genero> todosGeneros = repoGenero.listar();
            for (Genero aux : todosGeneros) {
                if (aux.getNome().equalsIgnoreCase(nomeGenero)) {
                    g = aux;
                    break;
                }
            }

            if (g == null) {
                throw new Exception("Gênero '" + nomeGenero + "' não encontrado no sistema.");
            }

            if (s.getGeneros().contains(g)) {
                throw new Exception("Esta série já está associada ao gênero selecionado.");
            }

            s.adicionar(g);
            repoSerie.atualizar(s);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }


    public void adicionarEpisodio(String nomeSerie, String nomeEpisodio) throws Exception {
        if (nomeEpisodio == null || nomeEpisodio.trim().isEmpty()) {
            throw new Exception("O nome do episódio não pode ser nulo ou vazio.");
        }

        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie);
            if (s == null) {
                throw new Exception("Série não encontrada para associar o episódio.");
            }

            Episodio novoEpisodio = new Episodio(nomeEpisodio);
            s.adicionar(novoEpisodio);

            repoSerie.atualizar(s);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }


    public void excluirSerie(String nomeSerie) throws Exception {
        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie);
            if (s == null) {
                throw new Exception("Série não encontrada para exclusão.");
            }

            repoSerie.deletar(s);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }


    public List<Serie> listarTodas() {
        return repoSerie.listar();
    }


    public List<Serie> consultarPorAno(int ano) {
        return repoSerie.consultarSeriesPorAno(ano);
    }

    public List<Serie> consultarPorGenero(String genero) {
        return repoSerie.consultarSeriesPorGenero(genero);
    }

    public List<Serie> consultarComMaisDeNEpisodios(int n) {
        return repoSerie.consultarSeriesComMaisDeNEpisodios(n);
    }
}

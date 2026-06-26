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

    /**
     * Cadastra uma nova série no sistema com validações obrigatórias.
     * @param nome Nome da série.
     * @param dataLancamento Data de lançamento (LocalDate).
     * @param foto Array de bytes representando a imagem do pôster.
     * @throws Exception Se o nome for inválido ou se a série já existir.
     */
    public void cadastrarSerie(String nome, LocalDate dataLancamento, byte[] foto) throws Exception {
        // Validações básicas de negócio
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome da série não pode estar vazio.");
        }
        if (dataLancamento == null) {
            throw new Exception("A data de lançamento deve ser informada.");
        }

        RepositorioSerie.begin();
        try {
            // Verifica se já existe uma série com o mesmo nome
            Serie existente = repoSerie.buscarPorNome(nome);
            if (existente != null) {
                throw new Exception("Já existe uma série cadastrada com o nome: " + nome);
            }

            // Cria e persiste a nova entidade
            Serie nova = new Serie(nome, dataLancamento);
            if (foto != null) {
                // Atribui o array de bytes da foto (requisito do professor)
                // Nota: Certifique-se de que o atributo 'foto' tem setter na classe Serie
                // se necessário, descomente a linha abaixo ou faça o ajuste correspondente:
                // nova.setFoto(foto); 
            }

            repoSerie.criar(nova);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }

    /**
     * Remove o primeiro gênero associado à série especificada.
     */
    public void removerPrimeiroGenero(String nomeSerie) throws Exception {
        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie); 
            if (s == null) {
                throw new Exception("Série não encontrada no banco de dados.");
            }

            if (!s.getGeneros().isEmpty()) {
                Genero g = s.getGeneros().getFirst(); // Obtém o primeiro gênero
                s.remover(g); // Remove a relação bidirecional
                
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

    /**
     * Associa um gênero existente a uma série.
     */
    public void adicionarGeneroASerie(String nomeSerie, String nomeGenero) throws Exception {
        RepositorioSerie.begin();
        try {
            Serie s = repoSerie.buscarPorNome(nomeSerie);
            if (s == null) {
                throw new Exception("Série não encontrada.");
            }

            // Busca o gênero (usando uma instância de repositório de gênero)
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

    /**
     * Adiciona um novo episódio a uma série existente.
     */
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
            s.adicionar(novoEpisodio); // O cascade CascadeType.ALL na classe Serie salvará o episódio

            repoSerie.atualizar(s);
            RepositorioSerie.commit();
        } catch (Exception e) {
            RepositorioSerie.rollback();
            throw e;
        }
    }

    /**
     * Exclui uma série completa do sistema através do seu nome.
     */
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

    /**
     * Lista todas as séries cadastradas no banco de dados.
     */
    public List<Serie> listarTodas() {
        return repoSerie.listar();
    }

    // ====================================================================
    // MÉTODOS DE CONSULTAS DA ETAPA 2 (FACADE DE CONSULTAS)
    // ====================================================================

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
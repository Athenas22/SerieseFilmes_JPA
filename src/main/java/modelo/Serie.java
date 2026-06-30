package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "serie20242370007")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private LocalDate dataLancamento;

    @Lob 
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    
    @OneToMany(mappedBy = "serie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Episodio> episodios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Genero> generos = new ArrayList<>();

    public Serie() {}

    public Serie(String nome, LocalDate dataLancamento) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void adicionar(Episodio e) {
        episodios.add(e);
        e.setSerie(this);
    }
    
    public void remover(Episodio e) {
        episodios.remove(e);
        e.setSerie(null);
    }

    public void adicionar(Genero g) {
        generos.add(g);
        g.getSeries().add(this);
    }
    
    public void remover(Genero g) {
        generos.remove(g);
        g.getSeries().remove(this);
    }

    @Override
    public String toString() {
        String texto = "id=" + id + ", nome=" + nome + ", lancamento=" + dataLancamento;
        
        texto += ", episodios: ";
        for (Episodio e : episodios) {
            texto += e.getNome() + ", ";
        }
            
        texto += "generos: ";
        for (Genero g : generos) {
            texto += g.getNome() + ", ";
        }
            
        return texto;
    }
}

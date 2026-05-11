package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "serie20242370007")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private int ano;

    @OneToMany(mappedBy = "serie",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Episodio> episodios = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Genero> generos = new ArrayList<>();

    public Serie() {} // obrigatorio para JPA

    public Serie(String nome, int ano) {
        this.nome = nome;
        this.ano = ano;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public List<Episodio> getEpisodios() { return episodios; }
    public List<Genero> getGeneros() { return generos; }

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
        String texto = "id=" + id + ", nome=" + nome + ", ano=" + ano;
        texto += ",  episodios: ";
        for (Episodio e : episodios)
            texto += e.getNome() + ",";
        texto += ",  generos: ";
        for (Genero g : generos)
            texto += g.getNome() + ",";
        return texto;
    }
}
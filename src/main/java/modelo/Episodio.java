package modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodio20242370015")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Serie serie;

    public Episodio() {} // obrigatorio para JPA

    public Episodio(String nome) {
        this.nome = nome;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }

    @Override
    public String toString() {
        return "id=" + id + ", nome=" + nome +
                (serie != null ? ", serie=" + serie.getNome() : ", sem serie");
    }
}
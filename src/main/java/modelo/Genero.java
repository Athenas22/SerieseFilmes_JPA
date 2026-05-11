package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "genero20242370026")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @ManyToMany(mappedBy = "generos")
    private List<Serie> series = new ArrayList<>();

    public Genero() {} // obrigatorio para JPA

    public Genero(String nome) {
        this.nome = nome;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Serie> getSeries() { return series; }

    @Override
    public String toString() {
        String texto = "id=" + id + ", nome=" + nome;
        texto += ",  series: ";
        for (Serie s : series)
            texto += s.getNome() + ",";
        return texto;
    }
}
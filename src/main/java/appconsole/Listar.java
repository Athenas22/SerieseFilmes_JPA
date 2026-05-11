package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Episodio;
import modelo.Genero;
import modelo.Serie;
import util.Util;

public class Listar {
    private EntityManager manager;

    public Listar() {
        try {
            Util.conectar();
            manager = Util.getManager();

            System.out.println("\nListagem de Series");
            TypedQuery<Serie> q1 = manager.createQuery("select s from Serie s", Serie.class);
            List<Serie> series = q1.getResultList();
            for (Serie s : series)
                System.out.println(s);

            System.out.println("\nListagem de Episodios");
            TypedQuery<Episodio> q2 = manager.createQuery("select e from Episodio e order by e.id", Episodio.class);
            List<Episodio> episodios = q2.getResultList();
            for (Episodio e : episodios)
                System.out.println(e);

            System.out.println("\nListagem de Generos");
            TypedQuery<Genero> q3 = manager.createQuery("select g from Genero g", Genero.class);
            List<Genero> generos = q3.getResultList();
            for (Genero g : generos)
                System.out.println(g);

        } catch (Exception e) {
            System.out.println("problema: " + e.getMessage());
        }

        Util.desconectar();
        System.out.println("fim do programa");
    }

    // =================================================
    public static void main(String[] args) {
        new Listar();
    }
}
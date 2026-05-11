package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Serie;
import util.Util;

public class Consultar {
    private EntityManager manager;

    public Consultar() {
        try {
            Util.conectar();
            manager = Util.getManager();

            List<Serie> series;
            TypedQuery<Serie> q;

            // Consulta 1: series do ano X
            System.out.println("\n--- series do ano 2011");
            q = manager.createQuery(
                    "select s from Serie s where s.ano = :ano",
                    Serie.class);
            q.setParameter("ano", 2011);
            series = q.getResultList();
            for (Serie s : series) System.out.println(s);

            // Consulta 2: series do genero de nome X
            System.out.println("\n--- series do genero Drama");
            q = manager.createQuery(
                    "select s from Serie s join s.generos g where g.nome = :nome",
                    Serie.class);
            q.setParameter("nome", "Drama");
            series = q.getResultList();
            for (Serie s : series) System.out.println(s);

            // Consulta 3: series com mais de N episodios
            System.out.println("\n--- series com mais de 2 episodios");
            q = manager.createQuery(
                    "select s from Serie s where size(s.episodios) > :n",
                    Serie.class);
            q.setParameter("n", 2);
            series = q.getResultList();
            for (Serie s : series) System.out.println(s);

        } catch (Exception e) {
            System.out.println("problema: " + e.getMessage());
        }

        Util.desconectar();
        System.out.println("\nfim do programa");
    }

    // =================================================
    public static void main(String[] args) {
        new Consultar();
    }
}
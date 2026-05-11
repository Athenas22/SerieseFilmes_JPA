package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import modelo.Genero;
import modelo.Serie;
import util.Util;

public class Deletar {
    private EntityManager manager;

    public Deletar() {
        try {
            Util.conectar();
            manager = Util.getManager();

            System.out.println("tarefa: deletar 'Teen Wolf' e seus episodios e relacionamentos");

            manager.getTransaction().begin();
            TypedQuery<Serie> q = manager.createQuery(
                    "select s from Serie s where s.nome = 'Teen Wolf'", Serie.class);
            Serie s = q.getSingleResultOrNull();

            if (s == null) {
                System.out.println("serie inexistente no banco");
                return;
            }

            // desfazer relacionamentos com generos antes de deletar
            for (Genero g : s.getGeneros())
                g.getSeries().remove(s);
            s.getGeneros().clear();

            // episodios sao deletados automaticamente (orphanRemoval = true)
            manager.remove(s);
            manager.getTransaction().commit();

            System.out.println("deletou com sucesso");

        } catch (NonUniqueResultException e) {
            manager.getTransaction().rollback();
            System.out.println("nome duplicado");
        } catch (Exception e) {
            manager.getTransaction().rollback();
            System.out.println("problema: " + e.getMessage());
        }

        Util.desconectar();
        System.out.println("fim do programa");
    }

    // =================================================
    public static void main(String[] args) {
        new Deletar();
    }
}
package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import modelo.Genero;
import modelo.Serie;
import util.Util;

public class Alterar {
    private EntityManager manager;

    public Alterar() {
        try {
            Util.conectar();
            manager = Util.getManager();

            manager.getTransaction().begin();
            TypedQuery<Serie> q = manager.createQuery(
                    "select s from Serie s where s.nome = 'Breaking Bad'", Serie.class);
            Serie s = q.getSingleResult();

            Genero g = s.getGeneros().getFirst();
            s.remover(g);

            manager.getTransaction().commit();

            System.out.println("removeu relacionamento: " + s.getNome() + " - " + g.getNome());

        } catch (NonUniqueResultException e) {
            manager.getTransaction().rollback();
            System.out.println("nome duplicado no banco");
        } catch (NoResultException e) {
            manager.getTransaction().rollback();
            System.out.println("serie nao encontrada no banco");
        } catch (Exception e) {
            manager.getTransaction().rollback();
            System.out.println("problema: " + e.getMessage());
        }

        Util.desconectar();
        System.out.println("fim do programa");
    }

    // =================================================
    public static void main(String[] args) {
        new Alterar();
    }
}
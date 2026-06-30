package appconsole;

import jakarta.persistence.EntityManager;
import modelo.Episodio;
import modelo.Genero;
import modelo.Serie;
import util.Util;

import java.time.LocalDate; 

public class Cadastrar {
    private EntityManager manager;

    public Cadastrar() {
        try {
            Util.conectar();
            manager = Util.getManager();

            System.out.println("Cadastrando series, episodios e generos...");
            Genero gDrama   = new Genero("Drama");
            Genero gAcao    = new Genero("Acao");
            Genero gComedia = new Genero("Comedia");
            Genero gSciFi   = new Genero("Sci-Fi");

            Serie s;

            manager.getTransaction().begin();

            s = new Serie("Breaking Bad", LocalDate.of(2008, 1, 20));
            s.adicionar(new Episodio("Piloto"));
            s.adicionar(new Episodio("Cat's in the Bag"));
            s.adicionar(new Episodio("And the Bag's in the River"));
            s.adicionar(new Episodio("Cancer Man"));
            s.adicionar(new Episodio("Gray Matter"));
            s.adicionar(new Episodio("Crazy Handful of Nothin"));
            s.adicionar(gDrama);
            s.adicionar(gAcao);
            manager.persist(s);

            s = new Serie("Stranger Things", LocalDate.of(2016, 7, 15));
            s.adicionar(new Episodio("O desaparecimento de Will Byers"));
            s.adicionar(new Episodio("A estranha na Maple Street"));
            s.adicionar(new Episodio("A pulga e o acrobata"));
            s.adicionar(gSciFi);
            s.adicionar(gDrama); 
            manager.persist(s);

            s = new Serie("The Office", LocalDate.of(2005, 3, 24));
            s.adicionar(new Episodio("Pilot"));
            s.adicionar(new Episodio("Diversity Day"));
            s.adicionar(gComedia);
            manager.persist(s);

            s = new Serie("Brooklyn Nine-Nine", LocalDate.of(2013, 9, 17));
            s.adicionar(new Episodio("Piloto"));
            s.adicionar(new Episodio("The Tagger"));
            s.adicionar(new Episodio("The Slump"));
            s.adicionar(gComedia);
            s.adicionar(gAcao);
            manager.persist(s);

            s = new Serie("Teen Wolf", LocalDate.of(2011, 6, 5));
            s.adicionar(new Episodio("A lua do lobo"));
            s.adicionar(new Episodio("Segunda chance"));
            s.adicionar(gDrama);
            s.adicionar(gSciFi);
            manager.persist(s);

           
            manager.getTransaction().commit();

            System.out.println("cadastrou com sucesso");

        } catch (Exception e) {
        
            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("problema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
            System.out.println("fim do programa");
        }
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}

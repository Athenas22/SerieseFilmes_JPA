package util;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
    private static EntityManager manager;
    private static EntityManagerFactory factory;
    private static final Logger logger = LogManager.getLogger(Util.class);

    public static void conectar() {
        if (manager == null) {
            Properties propriedades = new Properties();
            try {
                propriedades.load(Util.class.getResourceAsStream("/util/ip.properties"));
            } catch (IOException e) {
                throw new RuntimeException("arquivo /util/ip.properties inexistente");
            }

            String sgbd = propriedades.getProperty("sgbd");

            if (sgbd.equals("postgresql")) {
                logger.info("----configurando postgresql");
                factory = Persistence.createEntityManagerFactory("hibernate-postgresql");
            }
            if (sgbd.equals("mysql")) {
                logger.info("----configurando mysql");
                factory = Persistence.createEntityManagerFactory("hibernate-mysql");
            }

            manager = factory.createEntityManager();
            logger.info("-------- conectou banco de dados");
        }
    }

    public static void desconectar() {
        if (manager != null && manager.isOpen()) {
            manager.close();
            factory.close();
            manager = null;
            logger.debug("-------- desconectou banco de dados");
        }
    }

    public static EntityManager getManager() {
        return manager;
    }
}
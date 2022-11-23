package connector;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AbstractJPADAO {

    protected EntityManager entityManager;

    public void init() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("com.pvt");
        entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    public void close() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }
}

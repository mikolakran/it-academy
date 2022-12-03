package configurations;

import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        JpaTransactionManager bean = context.getBean(JpaTransactionManager.class);
        EntityManagerFactory entityManagerFactory = bean.getEntityManagerFactory();
        User user = new User("n","n","n","n");
        assert entityManagerFactory != null;
    }
}

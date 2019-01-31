package assignment.perzistencija;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HiberanteUtil {
    
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    
    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
    
    static void close() {
        if(serviceRegistry!= null) {
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
    }
}

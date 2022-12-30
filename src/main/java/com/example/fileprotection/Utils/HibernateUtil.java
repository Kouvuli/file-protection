package com.example.fileprotection.Utils;




import com.example.fileprotection.Entities.Account;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


public class HibernateUtil {
    private final static SessionFactory FACTORY;
    static {
        Configuration conf=new Configuration();
        Properties props=new Properties();
        props.put(Environment.DIALECT,"org.hibernate.dialect.PostgreSQLDialect");
        props.put(Environment.DRIVER,"org.postgresql.Driver");
        props.put(Environment.URL,"jdbc:postgresql://localhost/BT01.03");
        props.put(Environment.PASS,"1234");
        props.put(Environment.USER,"postgres");
        props.put(Environment.SHOW_SQL,"false");

        conf.setProperties(props);


        conf.addAnnotatedClass(Account.class);




        ServiceRegistry registry=new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        FACTORY=conf.buildSessionFactory(registry);
    }

    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}

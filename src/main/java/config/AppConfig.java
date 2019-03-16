package config;

import java.util.Properties;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ma.ensa.aop.LogAfter;
import ma.ensa.entities.Avoir;
import ma.ensa.entities.Client;
import ma.ensa.entities.Commande;
import ma.ensa.entities.LigneCommande;
import ma.ensa.entities.LigneCommandeId;
import ma.ensa.entities.Produit;
import ma.ensa.panier.IPanier;
import ma.ensa.panier.Panier;


@Configuration
@ComponentScan("ma.ensa.dao,ma.ensa.service")
@EnableTransactionManagement
public class AppConfig {
		
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/ecommercedb02");
	    dataSource.setUsername("root");
	    dataSource.setPassword("");
	    return dataSource;
	}
	
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setAnnotatedClasses(new Class[]{
        		Produit.class,
        		Commande.class,
        		LigneCommande.class,
        		LigneCommandeId.class,
        		Client.class,
        		Avoir.class
        		});
        return sessionFactory;
     }
	
	private Properties getHibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.hbm2ddl.auto","create");
	    properties.put("hibernate.show_sql","true");
	    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	    return properties;
	}
	
	//@Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
    
    @Bean(name="panier")
    public IPanier getPanier(){
    	return new Panier();
    }
    
    @Bean(name="logaop")
	public LogAfter getlogAop(){
		return new LogAfter();
	}
    
    @Bean(name="panierProxy")
    public ProxyFactoryBean getProxy() throws ClassNotFoundException{
		ProxyFactoryBean panierProxy = new ProxyFactoryBean();
		panierProxy.setProxyInterfaces(new Class<?>[]{IPanier.class} );
		panierProxy.setTarget(getPanier());
		panierProxy.setInterceptorNames(new String[]{"logaop"});
		return panierProxy;
	}
	
}

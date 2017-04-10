package fr.epita.iam.iamcore.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import fr.epita.iam.datamodel.Identity;

public class TestHibernate {
	
	 //creating configuration object  
    private Configuration cfg;  
    
    //Session factory object  
    private SessionFactory factory;  
      
    //Session object  
    private Session session;  
      


	@Before
	public void setup(){
		 //creating configuration object  
	    cfg = new Configuration();  
	    //populates the data of the configuration file 
	    cfg.configure("hibernate.cfg.xml"); 
	      
	    //creating session factory object  
	    factory = cfg.buildSessionFactory();  
	      
	     
	    
	}
	
	@Test
	public void testSaveIdentity(){
		
		//creating session object  
	    session = factory.openSession(); 
		//creating transaction object  
	    Transaction transaction = session.beginTransaction();
	    Identity  identity = new Identity("1", "Thomas", "thomas.broussard@gmail.com");
	    // saving identity
	    session.persist(identity);
	    transaction.commit();
	    //close session
	    session.close();
		
	}
	
}

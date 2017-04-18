/**
 * 
 */
package fr.epita.iam.iamcore.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityHibernateDAO;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestHibernateHomework {
	
	
	@Inject
	IdentityHibernateDAO identityDao;
	
	
	private static final Logger LOGGER = LogManager.getLogger(TestSpring.class);
	

	@Test
	public void testHibernate() throws SQLException{
		
		List<Identity> identities = identityDao.readAll();
		LOGGER.info(identities);
		assertEquals(identities.size(), 0);
	
		Identity identity = new Identity("Thomas", "thomas.broussard@gmail.com");

		identityDao.create(identity);
		
		identities = identityDao.readAll();
		LOGGER.info(identities);
		assertEquals(identities.size(), 1);
		
		Identity identity2 = new Identity("Thomas2", "thomas2.broussard@gmail.com");
		identityDao.create(identity2);
		identities = identityDao.readAll();
		assertEquals(identities.size(), 2);
		LOGGER.info(identities);
		
		
		identity2.setDisplayName("Thoms");
		identityDao.update(identity2);		
		identities = identityDao.readAll();
		assertEquals(identities.size(), 2);
		LOGGER.info(identities);
		
		identityDao.delete(identity2);
		identities = identityDao.readAll();
		assertEquals(identities.size(), 1);
		LOGGER.info(identities);
		
		
	
		
		
	}



}

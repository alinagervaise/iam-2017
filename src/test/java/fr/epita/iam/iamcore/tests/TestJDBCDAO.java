/**
 * 
 */
package fr.epita.iam.iamcore.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestJDBCDAO {
	
	@Inject
	DataSource ds;
	
	@Inject 
	IdentityJDBCDAO dao;
	
	private static boolean initialized = false;
	private static final Logger LOGGER = LogManager.getLogger(TestJDBCDAO.class);
	

	public static void globalSetup(DataSource source) throws SQLException{
		LOGGER.info("beginning the setup");
		Connection connection = source.getConnection();
		PreparedStatement pstmt = connection.prepareStatement("CREATE TABLE IDENTITIES " 
	    + " (IDENTITIES_UID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITIES_PK PRIMARY KEY, " 
	    + " IDENTITIES_DISPLAYNAME VARCHAR(255), "
	    + " IDENTITIES_EMAIL VARCHAR(255), "
	    + " IDENTITIES_BIRTHDATE DATE "
	    + " )");
		
		pstmt.execute();
		connection.commit();
		pstmt.close();
		connection.close();
		LOGGER.info("setup finished : ready to proceed with the testcase");
		
	}
	
	@Before
	public void setUp() throws SQLException{
		LOGGER.info("before test setup");
		if (! initialized){
			globalSetup(ds);
			initialized = true;
		}
	}
	
	
	@Test
	public void basicTest() throws SQLException{
		//IdentityJDBCDAO dao = new IdentityJDBCDAO();
		dao.writeIdentity(new Identity(null, "Thomas Broussard", "thomas.broussard@gmail.com"));
		
	}
	
	@Test
	public void testInsert() throws SQLException{
		LOGGER.info("beginning testInsert");
		
		Connection connection = ds.getConnection();
		String displayName = "Thomas Broussard";
		String validationSQL =  " select * from IDENTITIES  where  IDENTITIES_DISPLAYNAME = ?";
		
		PreparedStatement pstmt = connection.prepareStatement(validationSQL);
		pstmt.setString(1, displayName);
		/**
		 * List result = new ArrayList();
		 *  while (resultSet.next()){
		 *  	result.append(.....)
		 */
	
		ResultSet resultSet = pstmt.executeQuery();
		assertNotNull(resultSet);
		assertEquals(resultSet.next(), true);
		assertEquals(resultSet.getString("IDENTITIES_DISPLAYNAME"), "Thomas Broussard");
		assertEquals(resultSet.getString("IDENTITIES_EMAIL"), "thomas.broussard@gmail.com");
		assertEquals(resultSet.next(), false);
		connection.commit();
		pstmt.close();
		connection.close();
		LOGGER.info("testInsert finished ");
		
	}

	private static Connection getConnection(String connectionString, String username, String password) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionString, username, password);
		return connection;
	}
	
	@After
	public void tearDown(){
		LOGGER.info("after test cleanup");
	}
	
	
	@AfterClass()
	public static void globalTearDown(){
		LOGGER.info("global cleanup");
	}

}

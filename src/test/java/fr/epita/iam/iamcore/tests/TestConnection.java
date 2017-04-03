package fr.epita.iam.iamcore.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.epita.iam.services.Configuration;

public class TestConnection {
	
	static private String connectionString;
	static private String username;
	static private String password;
	
	
	@BeforeClass
	public static void setUp() throws IOException{
		//use properties and system path
		Properties properties = new Properties();
		//System variable launch argument of jvm
		String filePath = System.getProperty("fr.epita.iam.confFilePath");
		
		// file path
		FileInputStream inputStreamFile = new FileInputStream(filePath);
		// classpath 
		// properties.load(this.getClass()getResourcesAsStream("/test.properties)
		properties.load(inputStreamFile);
		connectionString = properties.getProperty("jdbc.connection.string");
		username = properties.getProperty("jdbc.connection.user");
		password = properties.getProperty("jdbc.connection.pwd");
		
	}

	@Test
	public void test() throws SQLException  {
		DriverManager.getConnection(connectionString, username, password);	
	}
	
	@Test 
	public void testConfiguration() throws IOException, SQLException{
		Configuration configuration = Configuration.getIsntance();
		Properties properties = configuration.getProperties();
		assertEquals("jdbc:derby:memory:IAM;create=true", configuration.getConnectionString());
		assertEquals("TOM", configuration.getUsername());
		assertEquals("TOM", configuration.getPassword());
		
		assertEquals("jdbc:derby:memory:IAM;create=true",properties.getProperty("jdbc.connection.string"));
		assertEquals("TOM",properties.getProperty("jdbc.connection.user"));
		assertEquals("TOM",properties.getProperty("jdbc.connection.pwd"));
		Connection connection = configuration.getConnection();
		assertNotNull(connection);
		
	}
}

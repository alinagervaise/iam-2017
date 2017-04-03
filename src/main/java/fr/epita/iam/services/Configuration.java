package fr.epita.iam.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {
	String connectionString;
	String username;
	String password;
	Properties properties;
	private static Configuration instance;
	private static final Logger LOGGER = LogManager.getLogger(Configuration.class);
	
	private Configuration(){
		initProperties();
	}
	public static Configuration getIsntance(){
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public void initProperties(){
		properties = new Properties();
		//String filePath = System.getProperty("fr.epita.iam.confFilePath");
		String filePath = "/src/test/resources/test.properties";
		try {
			properties.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			LOGGER.debug("Configuration file not found!", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.debug(e.getMessage());
		}
	}
	public Properties getProperties() throws IOException{
		properties = new Properties();
		return properties;	
	}
	public Connection getConnection() throws SQLException{
		try {
			properties = this.getProperties();
		} catch (IOException e) {
			LOGGER.debug("getConnection: {}", e);
		}
		connectionString = properties.getProperty("jdbc.connection.string");
		username = properties.getProperty("jdbc.connection.user");
		password = properties.getProperty("jdbc.connection.pwd");
		
		return DriverManager.getConnection(connectionString, username, password);
	}
	public String getConnectionString() {
		return properties.getProperty("jdbc.connection.string");
	}
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	public String getUsername() {
		return properties.getProperty("jdbc.connection.user");
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return properties.getProperty("jdbc.connection.pwd");
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}

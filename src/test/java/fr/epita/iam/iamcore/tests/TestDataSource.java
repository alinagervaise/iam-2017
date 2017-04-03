package fr.epita.iam.iamcore.tests;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestDataSource {
	@Inject 
	DataSource ds;
	
	@Test
	public void testDataSource() throws SQLException {
		ds.getConnection();
	}

}

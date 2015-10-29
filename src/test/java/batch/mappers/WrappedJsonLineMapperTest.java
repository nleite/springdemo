package batch.mappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.util.JSONParseException;

public class WrappedJsonLineMapperTest {

	private WrappedVideoJsonLineMapper obj;
	private File jsonFile;
	private File multiLineFile;
	
	@Before
	public void setUp() throws Exception {
		obj = new WrappedVideoJsonLineMapper();
		jsonFile = new File(getClass().getResource("/batch/mappers/videostest.json").toURI());
		multiLineFile = new File(getClass().getResource("/batch/mappers/multiline.json").toURI());
		Assert.assertNotNull(jsonFile);
		Assert.assertNotNull(multiLineFile);
		
			
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testMapLine() throws Exception{
		BufferedReader 	reader = new BufferedReader( new FileReader( jsonFile));
		String line = reader.readLine();
		int n = 1;
		while(line != null){
			Assert.assertNotNull(obj.mapLine(line, n));
			line = reader.readLine();
		}
		reader.close();
	}
	
	@Test(expected=Exception.class)
	public void testMapMultiLine() throws Exception{
		BufferedReader reader = new BufferedReader( new FileReader( multiLineFile));
		String line = reader.readLine();
		int n = 1;
		obj.mapLine(line, n);
		reader.close();
	}
	

}

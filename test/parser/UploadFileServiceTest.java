package parser;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import mediamath.parser.ParsedFile;

public class UploadFileServiceTest extends JerseyTest {

	@Override
    protected Application configure() {
        return new ResourceConfig(ParsedFile.class);
    }
	 
	@Test
	public void test1()  {
		ParsedFile parsedFile = new ParsedFile("Hello !!Hello ! My name is Patricia. Lovely blueweather!", "blue");
		int expectedTotal = 7; 
		Map<String, Integer> expectedOccurrences = new HashMap<String, Integer>();
		expectedOccurrences.put("hello", 2); 
		expectedOccurrences.put("my", 1);
		expectedOccurrences.put("name", 1);
		expectedOccurrences.put("is", 1);
		expectedOccurrences.put("patricia", 1);
		expectedOccurrences.put("lovely", 1);
		Assert.assertEquals(expectedTotal, parsedFile.getTotal());
		Assert.assertEquals(expectedOccurrences, parsedFile.getOccurrences());
	}
	
	@Test
	public void test2() {
		ParsedFile parsedFile = new ParsedFile("", "");
		int expectedTotal = 0; 
		Map<String, Integer> expectedOccurrences = new HashMap<String, Integer>();
		Assert.assertEquals(expectedTotal, parsedFile.getTotal());
		Assert.assertEquals(expectedOccurrences, parsedFile.getOccurrences());
	}
	
	@Test
	public void test3() {
		ParsedFile parsedFile = new ParsedFile("blueberries bluewalnuts.", "BLUE");
		int expectedTotal = 0; 
		Map<String, Integer> expectedOccurrences = new HashMap<String, Integer>();
		Assert.assertEquals(expectedTotal, parsedFile.getTotal());
		Assert.assertEquals(expectedOccurrences, parsedFile.getOccurrences());
	}
	

}

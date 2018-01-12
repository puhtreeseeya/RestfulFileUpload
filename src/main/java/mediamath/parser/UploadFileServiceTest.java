package mediamath.parser;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class UploadFileServiceTest extends JerseyTest {
	
	private static String baseuri = "http://localhost:8080/parser/webapi/file/upload";

	@Path("hello")
    public static class HelloResource {
        @GET
        public String getHello() {
            return "Hello World!";
        }
    }
 
    @Override
    protected Application configure() {
        return new ResourceConfig(UploadFileService.class);
    }
 
    @Test
    public void test() {
    		
    		final FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
    		final String value = "Hello World";
    		final FormDataContentDisposition dispo = FormDataContentDisposition//
    				.name("file")//
    				.fileName("test.txt")//
    				.size(value.getBytes().length)//
    				.build();
    		final FormDataBodyPart bodyPart = new FormDataBodyPart(dispo, value);
    		formDataMultiPart.bodyPart(bodyPart);
    		
    		//Client client = ClientBuilder.newClient(); 
    		//WebTarget target = client.target(baseuri); 
    		Response response = target("/webapi/file/upload").request().post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA), Response.class);
    		
    		
    }
}

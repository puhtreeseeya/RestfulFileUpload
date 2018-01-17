package mediamath.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/file")
public class UploadFileService {
	
	static final String BANNED_WORD = "blue"; 
	static List<ParsedFile> parsedFileList = new ArrayList<ParsedFile>();  
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(uploadedInputStream, writer, "UTF-8"); 
		} catch(IOException e) {
			e.printStackTrace(); 
		}
		String input = writer.toString();
		ParsedFile parsedFile = new ParsedFile(input, "blue"); 
		String output = parsedFile.toJsonString();  
		return Response.status(200).entity(output).build();
		
	}
	
	@GET
	@Path("/{fileId}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String queryFile(@PathParam("fileId") String fileId) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		int id = Integer.parseInt(fileId); 
		ParsedFile file;  
		try {
			file = parsedFileList.get(id); 
		} catch(IndexOutOfBoundsException e) {
			String jsonString = gson.toJson(null);
			return jsonString;  
		}
		String jsonString = gson.toJson(file); 
		return jsonString; 
	}
	
}
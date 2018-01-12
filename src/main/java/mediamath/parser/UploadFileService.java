package mediamath.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	static ParsedFileList list = new ParsedFileList(); 
	
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
		String theString = writer.toString();
		String jsonString = parseInput(theString); 
		return Response.status(200).entity(jsonString).build();
		
	}
	
	@GET
	@Path("/{fileId}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String queryFile(@PathParam("fileId") String fileId) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		int id = Integer.parseInt(fileId); 
		ParsedFile file;  
		try {
			file = list.getParsedFile(id);
		} catch(IndexOutOfBoundsException e) {
			String jsonString = gson.toJson(null);
			return jsonString;  
		}
		String jsonString = gson.toJson(file); 
		return jsonString; 
	}
	
	public String parseInput(String input) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String str = input.replaceAll("[^A-Za-z]", " "); 
		String[] arr = str.split("\\s+");  
		int total = 0; 
		for(int i=0; i<arr.length; i++) {
			if(map.get(arr[i]) == null && !arr[i].contains(BANNED_WORD)) {
				total++; 
				map.put(arr[i], 1); 
			} else if(!arr[i].contains(BANNED_WORD)) {
				total++; 
				map.put(arr[i], map.get(arr[i])+1); 		
			}
		}
		ParsedFile parsedFile = new ParsedFile(total, map); 
		list.addParsedFile(parsedFile);	
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonString = gson.toJson(parsedFile);
		System.out.println(jsonString);
		
		return jsonString; 
	}
	
	
}
package mediamath.parser;

import java.util.ArrayList;
import java.util.List;

public class ParsedFileList {
	
	private List<ParsedFile> parsedFileList = new ArrayList<ParsedFile>(); 
	
	public void addParsedFile(ParsedFile file) {
		this.parsedFileList.add(file); 
	}
	
	public ParsedFile getParsedFile(int index) {
		return this.parsedFileList.get(index); 
	}
}

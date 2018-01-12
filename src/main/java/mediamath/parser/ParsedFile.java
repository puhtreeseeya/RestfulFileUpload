package mediamath.parser;

import java.util.HashMap;
import java.util.Map;

public class ParsedFile {
	private int total; 
	private Map<String, Integer> occurrences; 
	
	public ParsedFile(int total, Map<String, Integer> map) {
		this.total = total; 
		this.occurrences = map; 
	}
	
	public int getTotal() {
		return this.total; 
	}
	
	public Map<String, Integer> getOccurrences() {
		return this.occurrences; 
	}
}

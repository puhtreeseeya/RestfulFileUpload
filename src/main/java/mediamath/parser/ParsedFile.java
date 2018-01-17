package mediamath.parser;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParsedFile {
	private int total; 
	private Map<String, Integer> occurrences; 
	
	public ParsedFile(String input, String bannedWord) {
		input = input.toLowerCase(); 
		bannedWord = bannedWord.toLowerCase(); 
		Map<String, Integer> wordToFrequency = new HashMap<String, Integer>();
		String str = input.replaceAll("[^A-Za-z]", " "); //removes any non alphabet characters
		String[] arr = str.split("\\s+"); //array containing only alphabet character words 
		int total = 0; 
		for(int i=0; i<arr.length; i++) {
			if(wordToFrequency.get(arr[i]) == null && !arr[i].contains(bannedWord)) {
				total++; 
				wordToFrequency.put(arr[i], 1); 
			} else if(!arr[i].contains(bannedWord)) {
				total++; 
				wordToFrequency.put(arr[i], wordToFrequency.get(arr[i])+1); 		
			}
		}
		this.total = total;
		this.occurrences = wordToFrequency; 
	}
	
	public int getTotal() {
		return this.total; 
	}
	
	public Map<String, Integer> getOccurrences() {
		return this.occurrences; 
	}
	
	public String toJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String parsedFileJsonString = gson.toJson(this);
		return parsedFileJsonString; 
	}
}

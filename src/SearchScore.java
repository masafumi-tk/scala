

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class SearchScore{
	HashMap<String,Double> feelMap = new HashMap<String,Double>();
	FileReader filereader =null;
	BufferedReader br = null;
	
	SearchScore(){
		
		 	try{
				filereader = new FileReader("Dictionary/FeelingJP.txt");
				br = new BufferedReader(filereader);
				
				String line;
				String[] word;
				
				while((line = br.readLine()) != null){
					
				word = line.split(":",0);
				feelMap.put(word[0],Double.parseDouble(word[3])+1);
				
				}
				
			}
			
			catch(FileNotFoundException e){
			      System.out.println(e);
			}
		 
			catch(IOException e){
			      System.out.println(e);
			}
			
			finally {
		        try {
		            br.close();
		            filereader.close();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
	}
	 
	public Double getFeelingScore(String line) throws IOException{
		List<String> wordList = new ArrayList<String>();
		wordList = getWordList(line);
		double allScore = 0;
		int count = 0;
		
		for(String key:wordList){
			if(feelMap.containsKey(key)==true){
				allScore = allScore+feelMap.get(key);
				count++;
			}	
		}
		
		return allScore/count;
	}
	
	public List<String> getWordList(String str) throws IOException{
		List<String> wordList =new ArrayList<String>();
		Tokenizer tokenizer = Tokenizer.builder().build();
		
			List<Token> result = tokenizer.tokenize(str);
			
			for (Token token : result){
				wordList.add(token.getSurfaceForm());
			}
			
		return wordList;
	}
	
    public boolean isNoun(String str)throws IOException
    {
    	Tokenizer tokenizer = Tokenizer.builder().build();
    	List<Token> tokens = tokenizer.tokenize(str);
    	String[] word2 = null;
    	for (Token token : tokens) 
    	{
    		word2 = token.getPartOfSpeech().split(",",0);
    	}
    	if(word2[0].equals("–¼ŽŒ")){
    		System.out.println(word2[0]+"test1");
    		return true;
    	}
    	else{
    		System.out.println(word2[0]+"test2");
    		return false;
    	}
    }
	
}
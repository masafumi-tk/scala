

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class PieelStreams 
{	  
    public void start() throws TwitterException 
    {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, 2016);
    	TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
    	SearchScore seachscore = new SearchScore();
    	String crlf = System.getProperty("line.separator");  //OS依存改行コード
    	TreeMap<String,Double> scoreCounter = new TreeMap<String,Double>();
    	TreeMap<String,Integer> wordCounter = new TreeMap<String,Integer>();
   
    	StatusListener listener = new StatusListener() {

    		@Override
    	    public void onStatus(Status status) {
    			
    			if(isJap(status.getText()) == true){
    				  try{
    					    double score = seachscore.getFeelingScore(status.getText());
    					  	
    					    System.out.println("Tweet:"+status.getText());
    					  	System.out.println("Score:"+score);
    			            
    					    
    			            File file = new File("Tweets/countWord.csv"); 
    			            FileWriter fw = new FileWriter(file,true);
    			            
    			            //名詞のみをカウント
    			           for(String word:seachscore.getWordList(status.getText())){
    			            	if(isNoun(word)&&isJap(word)){
    			            		if(!scoreCounter.containsKey(word)){
    			            			scoreCounter.put(word,score);
    			            			wordCounter.put(word,1); 		
    			            			fw.write(word+","+score+crlf);   
    			            		}
    			            		else{
    			    
    			            			scoreCounter.put(word,scoreCounter.get(word)+score);
    			            			wordCounter.put(word,wordCounter.get(word)+1);
    			            			fw.write(word+","+scoreCounter.get(word)+crlf);
			    			           
    			            		}
    			            		fw.close();
    			            	}
    			            }      
    			       } 
    				  catch(IOException e){
    					  System.out.println(e);
    			      }
    				  
    			}
    			
    		}
    		
    		 @Override
             public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                 System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
             }
             @Override
             public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                 System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
             }
             @Override
             public void onScrubGeo(long userId, long upToStatusId) {
                 System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
             }
             @Override
             public void onStallWarning(StallWarning warning) {
                 System.out.println("Got stall warning:" + warning);
             }
             @Override
             public void onException(Exception ex) {
                 ex.printStackTrace();
             }
             
    	};
    	
    	
    	twitterStream.addListener(listener);
    	twitterStream.sample();
    	
    	//double[][] locations = {new double[]{129.5,28.4},new double[]{146.1,46.20}};
        //FilterQuery filter = new FilterQuery();	
        //filter.locations( locations );
       // twitterStream.filter( filter );
    	
    	
    }
    
    //日本が含まれているかの判断
    public static boolean isJap(String str)
    {
    	for(int i = 0 ; i < str.length() ; i++) {
    		char ch = str.charAt(i);
    		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
    		if (Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
    			return true;

    		if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
    			return true;

    		if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
    			return true;

    		if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
    			return true;

    		if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
    			return true;
    	}
    	return false;
    }
    
    public static boolean isNoun(String str)throws IOException
    {
    	Tokenizer tokenizer = Tokenizer.builder().build();
    	List<Token> tokens = tokenizer.tokenize(str);
    	String[] word2 = null;
    	for (Token token : tokens) {
    		word2 = token.getPartOfSpeech().split(",",0);
    	}
    	if(word2[0].equals("名詞"))
    		return true;
    	else
    		return false;
    }
    
    
}

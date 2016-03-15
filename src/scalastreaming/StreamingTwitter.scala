package scalastreaming
import org.atilika.kuromoji._
import org.atilika.kuromoji.Tokenizer._
import java.lang.Character.UnicodeBlock
import twitter4j._
import conf._

object StreamingTwitter {
    def main(args: Array[String]) = {
      val twitterStream: TwitterStream = new TwitterStreamFactory().getInstance
    // Twitterへのアクセスアカウント情報を定義する
      
      val listener: StatusListener = new StatusListener {
        def onStatus(status: Status) = {
              println(status.getUser.getName + " : " + status.getText)
            }
        def onDeletionNotice(s: StatusDeletionNotice) = {}
        def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
        def onException(ex: Exception) = ex.printStackTrace()
        def onScrubGeo(userId: Long, upToStatusId: Long) = {}
        def onStallWarning(warning:StallWarning ) = {
             println("Got stall warning:" + warning);
            }
      }
      
      twitterStream.addListener(listener);
    	twitterStream.sample();
    }
    
    def isJap(str : String) : Boolean = {
     
      for(i<-0 until str.length()){
         var ch = str.charAt(i)
         var unicodeBlock:Character.UnicodeBlock = Character.UnicodeBlock.of(ch)
         if(Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
           return true
         if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
    			return true;
    		 if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
    			return true;
         if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
    			return true;
         if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
    			return true;
      }
      return false
    }
    
    def isNoun(str:String):Boolean ={
    	
    	return true
    }
    
    
    
    
}
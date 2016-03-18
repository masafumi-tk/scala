package scalastreaming
import org.atilika.kuromoji._
import org.atilika.kuromoji.Tokenizer._
import java.lang.Character.UnicodeBlock
import twitter4j._
import conf._
import scala.collection.mutable.HashMap
import scala.io.Source
import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token
import java.util.List;



object StreamingTwitter {
    def main(args: Array[String]) :Unit= {
      val twitterStream: TwitterStream = new TwitterStreamFactory().getInstance
      val feelingDictionary : FeelingDictionary = new FeelingDictionary();
      val listener: StatusListener = new StatusListener {
        def onStatus(status: Status) = {
              print(status.getText())
              
              
              
            }
        def onDeletionNotice(s: StatusDeletionNotice) = {}
        def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
        def onException(ex: Exception) = ex.printStackTrace()
        def onScrubGeo(userId: Long, upToStatusId: Long) = {}
        def onStallWarning(warning:StallWarning ) = {
             println("Got stall warning:" + warning)
            }
      }
     
      var test : List[String] = getWordList("ê¥å¥Ç™ëﬂïﬂÇ≥ÇÍÇΩ")
      
      //twitterStream.addListener(listener)
    	//twitterStream.sample()
    }
    
    def getWordList(str:String):List[String] ={
      val tokenizer:Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
      val tokens:List[Token] = tokenizer.tokenize(str)
      var word : List[String] = null
       
      
     
      return word
     
    }
    
    def isJap(str : String) : Boolean = {
     
      for(i<-0 until str.length()){
         var ch = str.charAt(i)
         var unicodeBlock:Character.UnicodeBlock = Character.UnicodeBlock.of(ch)
         if(Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
           return true
         if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
    			return true
    		 if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
    			return true
         if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
    			return true
         if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
    			return true
      }
      return false
    }
    
    def isNoun(str:String):Boolean ={
      val tokenizer:Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
      val tokens = tokenizer.tokenize(str).toArray()
      var word : Array[String] = null
      
      tokens.foreach{ t => 
        val token = t.asInstanceOf[Token]
        word = token.getPartOfSpeech().split(",",0)
      }
      if(word(0).equals("ñºéå"))
        return true
      else
        return true
    }
    
    
}
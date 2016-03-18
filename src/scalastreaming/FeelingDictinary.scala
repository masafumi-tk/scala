package scalastreaming

import scala.collection.mutable.HashMap
import java.io.FileReader;
import scala.io.Source
import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token

class FeelingDictionary{
  val feelMap = new HashMap[String,Double];
  var source : Source = null;
  setFeelMap("FeelingJP.txt") //感情スコアマップの生成
  
  def getFellingScore(str:String) : Double = {
    val tokenizer:Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
    val tokens = tokenizer.tokenize(str).toArray()
    var tokenScore : Double = 0
    var allScore : Double = 0;
    var count:Int = 0
    
    tokens.foreach({ t => 
      val token = t.asInstanceOf[Token]
      if(feelMap.contains(token.getSurfaceForm())==true){
        tokenScore = feelMap(token.getSurfaceForm())
        allScore += tokenScore
        count += 1
      }
    })
    return allScore/count  
  }
     
  def setFeelMap(fileName:String){
      var word : Array[String] = null
      var source : Source = null
	    source = Source.fromFile("Dictionary/"+fileName)
	    source.getLines.foreach({line => 
	      word = line.split(":",0)
	      feelMap.put(word(0),word(3).toDouble+1)
	    })
	    source.close
  }
  
}
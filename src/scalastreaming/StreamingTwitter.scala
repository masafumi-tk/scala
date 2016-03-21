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
import java.util.ArrayList;
import java.util.TreeMap;

object StreamingTwitter {
  def main(args: Array[String]): Unit = {
    val twitterStream: TwitterStream = new TwitterStreamFactory().getInstance
    val feelingDictionary: FeelingDictionary = new FeelingDictionary();
    val tokenizer: Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
    val scoreCounter: TreeMap[String, Double] = new TreeMap[String, Double]
    val wordCounter: TreeMap[String, Integer] = new TreeMap[String, Integer]

    val listener: StatusListener = new StatusListener {
      override def onStatus(status: Status) = {
        val tweet: String = status.getText()
        print(status.getText())
        if (isJap(status.getText()) == true) {
          var score: Double = feelingDictionary.getFellingScore(status.getText())
          val tweetTokens = tokenizer.tokenize(tweet).toArray()

          //–¼ŽŒ‚Ì‚Ý‚ðƒJƒEƒ“ƒg
          tweetTokens.foreach { t =>
            val word: String = t.asInstanceOf[Token].getSurfaceForm()
            if (isNoun(word) && isJap(word)) {
              if (!scoreCounter.containsKey(word)) {
                scoreCounter.put(word, score)
                wordCounter.put(word, 1)
                println("new:" + word)
              } else {
                scoreCounter.put(word, scoreCounter.get(word) + score)
                wordCounter.put(word, wordCounter.get(word) + 1)
                println("exWord:" + word)
              }
            }
          }

        }

      }
      override def onDeletionNotice(s: StatusDeletionNotice) = {}
      override def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
      override def onException(ex: Exception) = ex.printStackTrace()
      override def onScrubGeo(userId: Long, upToStatusId: Long) = {}
      override def onStallWarning(warning: StallWarning) = {
        println("Got stall warning:" + warning)
      }
    }

    twitterStream.addListener(listener)
    twitterStream.sample()
  }

  //•¶Í‚ð’PŒê‚²‚Æ‚ÉList‚ÉŠi”[‚µreturn
  def getWordList(str: String): ArrayList[String] = {
    val tokenizer: Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
    val tokens = tokenizer.tokenize(str).toArray()
    var word: ArrayList[String] = new ArrayList[String]()
    tokens.foreach { t =>
      val token = t.asInstanceOf[Token]
      word.add(token.getSurfaceForm())
    }
    return word
  }
  //“ú–{Œê‚©‚Ì”»’f
  def isJap(str: String): Boolean = {
    for (i <- 0 until str.length()) {
      var ch = str.charAt(i)
      var unicodeBlock: Character.UnicodeBlock = Character.UnicodeBlock.of(ch)
      if (Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
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
  //–¼ŽŒ‚©‚Ç‚¤‚©‚Ì”»’f
  def isNoun(str: String): Boolean = {
    val tokenizer: Tokenizer = Tokenizer.builder().mode(Tokenizer.Mode.NORMAL).build()
    val tokens = tokenizer.tokenize(str).toArray()
    var word: Array[String] = null

    tokens.foreach { t =>
      val token = t.asInstanceOf[Token]
      word = token.getPartOfSpeech().split(",", 0)
    }
    if (word(0).equals("–¼ŽŒ"))
      return true
    else
      return true
  }
}

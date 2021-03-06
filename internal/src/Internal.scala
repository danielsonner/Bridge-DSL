case class InvalidVulnerabilityException(s : String) extends Exception

/**
 * A Direction represents a player sitting in one of the four cardinal directions.
 */
object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West = Value
}

import Direction._

/**
 * Represents a single bid. Artifical bids may be indicated using `*`. `-` may
 * be used to indicate no ability to bid.
 */
case class Bid(a: String)
{
  override def toString : String = 
  {
    require(a.length <= 4, {println(s"$a is not a legal bid."
        +" Bids must be 4 characters or less. Use p for pass and x for double.")})
    var extraSpaces = ""
    for (i <- 1 to (3-a.length)) extraSpaces += " "
    // usually we want a leading space, but in the case of a four character bid
    // like 4NT* we can't afford leading space
    val firstSpace = if (a.length == 4) "" else " "
    firstSpace + a.toUpperCase.replace('S', '♠').replace('H', '♥').replace('D', '♦').replace('C', '♣') + extraSpaces
  }
}

/**
 * A case class to store a series of annotations about a bridge auction.
 * Can be passed into the Auction's display function.
 */
case class Annotations(annotations : List[String]) {}

/**
 * A full auction in bridge, which consists of a list of bids.
 * The list of bids always starts with the bid made by West. A bid of '-'
 * can be used to indicate that a player cannot bid.
 */
case class Auction(a : List[Bid])
{
  /**
   * Converts something like 5 to ⁵
   * I was unable to find perfectly similar unicode superscripts so two
   * digit numbers may look a bit strange such as ¹⁰ but very
   * very few bidding sequences will have 10 or more artificial bids
   */
  def numToSuperScript(n : String) : String =
  {
    if (n.length() == 0) return ""
    val Zero = "⁰"
    val One = "¹"
    val Two = "²"
    val Three = "³"
    val Four = "⁴"
    val Five = "⁵"
    val Six = "⁶"
    val Seven = "⁷"
    val Eight = "⁸"
    val Nine = "⁹"
    val retV = n(0) match {
      case '0' => Zero
      case '1' => One
      case '2' => Two
      case '3' => Three
      case '4' => Four
      case '5' => Five
      case '6' => Six
      case '7' => Seven
      case '8' => Eight
      case '9' => Nine
    }
    if (n.length() == 1)
      retV
    else
      retV + numToSuperScript(n.tail)
  }

  /**
   * Takes something like "4NT*" and 1 and converts it to
   * "4NT¹"
   */
  def addAnnotationSuperscipt(s : String, n : Int) : String =
  {

    s.dropRight(1) + numToSuperScript(n.toString())
  }

  /**
   * Displays an auction. Can optionally specify to only display the first afterHowLong bids,
   * to show the vulnerability (None Vul, Both Vul, E/W Vul, or N/S Vul), and to show annotations
   * for artificial bids (note that the artificial bids should be marked with an `*`).
   */
  def display(afterHowLong : Int = a.length, vulnerability: String = "", ann : Annotations = null) =
  {
    vulnerability.toLowerCase() match {
      case "" => None
      case "none vul" => println("None Vul\n")
      case "both vul" => println("Both Vul\n")
      case "e/w vul" =>  println("E/W Vul\n")
      case "n/s vul" => println("N/S Vul\n")
      case x => if(!x.equals("")) {
          println("Vulnerability must be either: None Vul, Both Vul, E/W Vul, or N/S Vul")
          throw new InvalidVulnerabilityException("Vulnerability must be either: None Vul, Both Vul, E/W Vul, or N/S Vul")
        }
    }
    require(afterHowLong <= a.length, {println("There were only "+a.length+s" bids but you are trying to display $afterHowLong bids.")})
    var formattedBids : String = ""
    var currentSuperScript : Int = 1
    for (i <- 1 to afterHowLong)
    {
      val currentBid : String = a(i-1).toString()
      if (currentBid contains "*") {
        formattedBids += addAnnotationSuperscipt(currentBid, currentSuperScript)
        currentSuperScript += 1
      } else {
        formattedBids += currentBid
      }
      if (i % 4 == 0)
        formattedBids += "\n"
    }
    if (afterHowLong != a.length)
      formattedBids += " ?"
    println(s" W   N   E   S\n$formattedBids")

    // Display the explanations of the bids
    if (ann != null)
    {
      for (x <- 0 to (ann.annotations.length-1))
      {
        println((x+1).toString() + ". " + ann.annotations(x))
      }
      println("") // add a newline after so it doesnt run into next displayed thing
    }
  }

  def display(vulnerability: String) : Unit = display(a.length, vulnerability)
}

/**
 * Represents a single playing card, such as the Ace of Spades.
 * Ten is represented with a T so that things will align nicely.
 */
case class Card(value : Char, suit: Char)
{
  override def toString: String = (value.toString.toUpperCase)
  def toStringSuited: String =
    {
     require("shdcSHDC" contains suit, {println(s"$suit is not a legal suit."
        +" Suits must be c, h, d, or s.")})
      var printedSuit : String = suit match{
        case 's'|'S' => "♠"
        case 'h'|'H' => "♥"
        case 'd'|'D' => "♦"
        case _ => "♣"
      }
      value.toString.toUpperCase + printedSuit
    }
}

/**
 * Represents one bridge hand. May contain less than 13 cards.
 */
case class Hand(h : List[Card])
{
  override def toString: String = 
  {
    val SPACER = "             "
    val hands = listStringHands
    val nSpades = listStringHands(0)
    val nHearts = listStringHands(1)
    val nDiamonds = listStringHands(2)
    val nClubs = listStringHands(3)
    s"$SPACER$nSpades\n$SPACER$nHearts\n$SPACER$nDiamonds\n$SPACER$nClubs"
  }

  /**
   * Returns a list of the hands suits formatted nicely. List is ordered by suit
   * hierarchy (spades, hearts, diamonds, clubs).
   */
  def listStringHands: List[String] = 
  {
    val nSpades = "♠" + h.filter(_.suit == 's').mkString("")
    val nHearts = "♥" + h.filter(_.suit == 'h').mkString("")
    val nDiamonds = "♦" + h.filter(_.suit == 'd').mkString("")
    val nClubs = "♣" + h.filter(_.suit == 'c').mkString("")
    List(nSpades,nHearts,nDiamonds,nClubs)
  }
}

/**
 * Represents a full bridge deal of 4 hands.
 */
case class Deal(n: Hand, e: Hand, s: Hand, w: Hand)
{

  /**
   * Helper function that does th work of formatting and displaying the hands.
   */
  def displayHelp(n: Hand, e: Hand, s: Hand, w: Hand, 
      dirOne : Direction = null, dirTwo : Direction = null) : Unit =
  {
    val SPACER = "             "
    val el : List[String] = e.listStringHands
    val wl : List[String] = w.listStringHands
    var ewCombined = (wl zip el).map{ case (m, a) => {
      var extraSpaces : String = ""
      for (i <- 1 to (13-m.length)) {extraSpaces += " "}
      s"$m$SPACER$extraSpaces$a\n"}}.mkString("")
    
    // determine what to print based off dirOne and dirTwo
    val N = if (dirOne == North || dirTwo == North) s"$n\n" else ""
    val E = if (dirOne == East || dirTwo == East) el.map{x => s"$SPACER$SPACER$x"}mkString("\n") else ""
    val S = if (dirOne == South || dirTwo == South) s"\n$s" else ""
    val W = if (dirOne == West || dirTwo == West) wl.mkString("\n") else ""
    if ((dirOne == null && dirTwo == null)) 
      println(s"$n\n$ewCombined\n$s\n")
    else if ((dirOne == East && dirTwo == West) 
        || (dirOne == West && dirTwo == East))
      println(s"$ewCombined\n")
    else
      println(s"$N$E$W$S\n")
    
  }

  def display () : Unit = 
  {
    displayHelp(n,e,s,w)
  }
  
  def display (a : Direction) =
  {
    displayHelp(n,e,s,w,a)
  }

  def display (a : Direction, b : Direction) =
  {
    displayHelp(n,e,s,w,a,b)
  }

  def displayAfter(numTricks : Int, played : PlayDiagram) : Unit =
  {
    require(numTricks*4 <= played.cardL.length)
    // currently -'s get added by literal extension but when the card is played they won't be
    // added here. Not sure if we want -'s or not here so quick fix is removing them
    var shortenedPlayed : List[Card] = List(Card('-', 's'), Card('-', 'h'), Card('-', 'd'), Card('-', 'c'))
    for (i <- 0 to (4*numTricks -1)) shortenedPlayed = played.cardL(i) :: shortenedPlayed
    val N = Hand(n.h.filter(x => !(shortenedPlayed contains x)))
    val E = Hand(e.h.filter(x => !(shortenedPlayed contains x)))
    val S = Hand(s.h.filter(x => !(shortenedPlayed contains x)))
    val W = Hand(w.h.filter(x => !(shortenedPlayed contains x)))
    displayHelp(N, E, S, W)
  }
}

/**
 * Represents a play diagram which is a list of cards in the order they have
 * been played.
 */
case class PlayDiagram(cardL : List[Card])
{
  def displayHelp(cards: List[Card], out : String, n : Int) : Unit =
  {
    val SP = "    "
    if (cards.length > 4)
      displayHelp(cards.drop(4), out + n + ". " + cards(0).toStringSuited + SP + cards(1).toStringSuited
          + SP + cards(2).toStringSuited + SP + cards(3).toStringSuited + "\n", n+1)
    else
      println(out + n + ". " + cards.map{x => x.toStringSuited + SP}.mkString(""))
  }

  def display () : Unit =
  {
    displayHelp(cardL, "   Lead  2nd   3rd   4th\n", 1)
  }
}

object BridgeExtender {

  /**
   * Converts something like "5c" to the appropriate Card
   */
	implicit def StringToCard(value : String) : Card =
		{
			var rank : Char = value(0)
			if (rank == '1') rank = 'T'
			val suit : Char = value.last
			Card(rank, suit)
		}
	
	/**
	 * Converts a string such as "103hKJ106432dQ943c" with or without whitespace to a Hand
	 */
	implicit def StringToCardL(value : String) : Hand =
	{
	  var hand = value
	  // add in voided suits
	  if (!hand.contains("s"))
	    hand = "-s" + hand
	  if (!hand.contains("h"))
	    hand = hand.split("s").mkString("s-h")
	  if (!hand.contains("d"))
	    hand = hand.split("h").mkString("h-d")
	  if (!hand.contains("c"))
	    hand = hand + "-c"

	  // change 10 to T
	  hand = hand.map{ x => if (x == '1') 'T' else x}.filter(_ != '0')

	  // split by suit
	  val sepSuits = hand.split(Array('s', 'h', 'd', 'c')).map(_.trim)

	  // Create and return the card list
	  val cardL : List[Card] = (sepSuits(0).map{ x => Card(x,'s')}.toList
	                           ::: sepSuits(1).map{ x => Card(x,'h')}.toList
	                           ::: sepSuits(2).map{ x => Card(x,'d')}.toList
	                           ::: sepSuits(3).map{ x => Card(x,'c')}.toList)
	  Hand(cardL)
	}

	/**
	 * Creates an auction from a string with bids separated by any number of spaces
	 * Assumes that W is the first bid entered
	 */
	implicit def StringToAuction(value : String) : Auction =
	{
	  Auction(value.trim.split("\\s+").map{x => Bid(x)}.toList)
	}

	/**
	 * Creates a PlayDiagram from a string of cards played seperated by spaces
	 */
	implicit def StringToPlayDiagram(value : String) : PlayDiagram =
	{
	  PlayDiagram(value.trim.split("\\s+").map{StringToCard(_)}.toList)
	}

	/**
	 * Converts a string with annotations seperated by *'s to an Annotations
	 */
  implicit def StringToAnnotations(value : String) : Annotations =
	{
	  Annotations(value.trim.split('*').map(_.trim).tail.toList)
	}


}

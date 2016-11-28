case class InvalidVulnerabilityException(s : String) extends Exception

object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West = Value
}

import Direction._

case class Bid(a: String)
{
  override def toString : String = 
  {
    require(a.length <= 3, {println(s"$a is not a legal bid."
        +" Bids must be 3 characters or less. Use p for pass and x for double.")})
    var extraSpaces = ""
    for (i <- 1 to (3-a.length)) extraSpaces += " "
    " " + a.replace('s', '♠').replace('h', '♥').replace('d', '♦').replace('c', '♣') + extraSpaces
  }
}

case class Auction(a : List[Bid])
{
  def display(afterHowLong : Int = a.length, vulnerability: String = "") =
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
    for (i <- 1 to afterHowLong)
    {
      formattedBids += a(i-1)
      if (i % 4 == 0)
        formattedBids += "\n"
    }
    if (afterHowLong != a.length)
      formattedBids += " ?"
    println(s" W   N   E   S\n$formattedBids")
  }

  def display(vulnerability: String) : Unit = display(a.length, vulnerability)
}

case class Card(value : Char, suit: Char) // we T for 10 for now
{
  override def toString: String = (value.toString)
}
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
  def listStringHands: List[String] = 
  {
    val nSpades = "♠" + h.filter(_.suit == 's').mkString("")
    val nHearts = "♥" + h.filter(_.suit == 'h').mkString("")
    val nDiamonds = "♦" + h.filter(_.suit == 'd').mkString("")
    val nClubs = "♣" + h.filter(_.suit == 'c').mkString("")
    List(nSpades,nHearts,nDiamonds,nClubs)
  }
}
case class Deal(n: Hand, e: Hand, s: Hand, w: Hand)
{
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

  def displayAfter(numTricks : Int, played : PlayedCards) : Unit =
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

case class PlayedCards(cardL : List[Card])

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
	 * Creates a PlayedCards from a string of cards played seperated by spaces
	 */
	implicit def StringToPlayedCards(value : String) : PlayedCards =
	{
	  PlayedCards(value.trim.split("\\s+").map{StringToCard(_)}.toList)
	}


}

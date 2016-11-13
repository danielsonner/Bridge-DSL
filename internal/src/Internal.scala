
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
  def display(afterHowLong : Int = a.length) = 
  {
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
    val nSpades = "♠" + h.filter(_.suit == 's').mkString("")
    val nHearts = "♥" + h.filter(_.suit == 'h').mkString("")
    val nClubs = "♣" + h.filter(_.suit == 'c').mkString("")
    val nDiamonds = "♦" + h.filter(_.suit == 'd').mkString("")
    s"$SPACER$nSpades\n$SPACER$nHearts\n$SPACER$nDiamonds\n$SPACER$nClubs"
  }
  def listStringHands: List[String] = 
  {
    val nSpades = "♠" + h.filter(_.suit == 's').mkString("")
    val nHearts = "♥" + h.filter(_.suit == 'h').mkString("")
    val nDiamonds = "♣" + h.filter(_.suit == 'd').mkString("")
    val nClubs = "♦" + h.filter(_.suit == 'c').mkString("")
    List(nSpades,nHearts,nDiamonds,nClubs)
  }
}
case class Deal(n: Hand, e: Hand, s: Hand, w: Hand)
{
  def display (numTricks : Int) : Unit = 
  {
    val SPACER = "             "
    val el : List[String] = e.listStringHands
    val wl : List[String] = w.listStringHands
    var ewCombined = (wl zip el).map{ case (m, a) => {
      var extraSpaces : String = ""
      for (i <- 1 to (13-m.length)) {extraSpaces += " "}
      s"$m$SPACER$extraSpaces$a\n"}}.mkString("")
    println(s"$n\n$ewCombined\n$s")
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

	implicit def StringToAuction(value : String) : Auction =
	{
	  Auction(value.trim.split("\\s+").map{x => Bid(x)}.toList)
	}

}

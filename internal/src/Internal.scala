
case class Auction(a : String)
{
  def display(afterHowLong : Int = 0) = {println(s"N  E  S  W\n$a")}
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

	implicit def StringToCard(value : String) : Card =
		{
			var rank : Char = value(0)
			if (rank == '1') rank = 'T'
			val suit : Char = value.last
			Card(rank, suit)
		}
	
	implicit def StringToCardL(value : String) : Hand =
	{
	  val sepSuits = (value.map{ x => if (x == '1') 'T' else x}.filter(_ != '0') // change 10 to T
	                  .split(Array('s', 'h', 'd', 'c')).map(_.trim)) // split by suit
	  val cardL : List[Card] = (sepSuits(0).map{ x => Card(x,'s')}.toList
	                           ::: sepSuits(1).map{ x => Card(x,'h')}.toList
	                           ::: sepSuits(2).map{ x => Card(x,'d')}.toList
	                           ::: sepSuits(3).map{ x => Card(x,'c')}.toList)
	  Hand(cardL)
	}

	implicit def StringToAuction(value : String) : Auction =
	{
	  Auction(value)
	}

}

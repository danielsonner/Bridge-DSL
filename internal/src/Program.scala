import BridgeExtender._

object Program extends App{
  val auction =
    """
    - - 3d 3NT
    P P P P
    4c 4d 4h 4s
    6NT P 7NT P
    P   x P   P
    xx AP
    """
  
  
  val N =  
      """
      10653s
      KQ54h
      7d
      KJ85c
      """
  val E = 
      """
      103h
      KJ106432d
      Q943c
      """
  val S = 
      """
      AK94s
      86h
      AQ4d
      A762c
      """
  val W ="QJ872s AJ972h 98d 10c"
  
  // TODO: some literal extension and stuff to improve this
  val playDiagram = PlayedCards(List(
      "8d", "7d", "2d", "Qd",
      "6h", "2h", "Kh", "3h",
      "5c", "3c", "Ac", "10c",
      "2c","2s","Kc","4c",
      "3s", "3d", "As", "7s",
      "8h", "9h", "Qh", "10h",
      "5s", "4d", "Ks", "8s",
      "Ad", "9d", "4h", "6d",
      "5d", "7h", "5h", "10d"
      ))

  val fullDeal : Deal = Deal(N,E,S,W) // This line really shouldn't be required
  auction.display() // note that you can put in after how many bids you want the bidding displayed
  fullDeal.display()
  fullDeal.displayAfter(6, playDiagram)
}
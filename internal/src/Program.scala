import BridgeExtender._

object Program extends App{
  val auction =
    """
    - - 3d 3NT
    P P x P
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

  val fullDeal : Deal = Deal(N,E,S,W) // This line really shouldn't be required
  auction.display() // note that you can put in after how many tricks you want the bidding displayed
  fullDeal.display(0) // I'd prefer to say something like display deal after 0 tricks
}
import BridgeExtender._

object Program extends App{
  val auction : Auction = 
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
      """ // string could've been one line but I think multiline style nicer
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
  val W =
      """
      QJ872s
      AJ972h
      9d8d
      10c
      """
  val fullDeal : Deal = Deal(N,E,S,W) // This line really shouldn't be required
  auction.display(0) // I'd prefer to say something like display auction
  fullDeal.display(0) // I'd prefer to say something like display deal after 0 tricks
}
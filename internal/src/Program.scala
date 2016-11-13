import BridgeExtender._

object Program extends App{
  //shouldnt req -`  ` just -` ` should be fine. also \n shouldnt be req'd
  val auction : Auction = "-  3d 3N AP" // maybe specify dealer and skip -
  
  
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
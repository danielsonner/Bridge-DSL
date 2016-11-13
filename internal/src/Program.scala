import BridgeExtender._

object Program extends App{
  //shouldnt req -`  ` just -` ` should be fine. also \n shouldnt be req'd
  val auction : Auction = "-  3d 3N AP" // maybe specify dealer and skip -
  
  
  val N : Hand =  """
                  10653s
                  KQ54h
                  7d
                  KJ85c
                  """ // string could've been one line but I think multiline style nicer

  val E : Hand = 
      """
      - s
      103h
      KJ106432d
      Q943c
      """//TODO: Handle voids in Internal.scala and let user not write voided suits
  val S : Hand = """
      AK94s
      86h
      AQ4d
      A762c
      """
  val W : Hand = Hand(List(
      "Qs", "Js", "8s", "7s", "2s",
      "Ah", "Jh", "9h", "7h", "2h",
      "9d", "8d",
      "10c"
      ))
  val fullDeal : Deal = Deal(N,E,S,W) // This line really shouldn't be required
  auction.display(0) // I'd prefer to say something like display auction
  fullDeal.display(0) // I'd prefer to say something like display deal after 0 tricks
}
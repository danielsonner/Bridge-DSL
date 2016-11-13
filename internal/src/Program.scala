import BridgeExtender._

object Program extends App{
  //shouldnt req -`  ` just -` ` should be fine. also \n shouldnt be req'd
  val auction : Auction = "-  3d 3N AP" // maybe specify dealer and skip -
  
  
  val N : Hand = Hand(List(  // mildly annoying to have this much typing to write
      "10s", "6s", "5s", "3s",
      "Kh", "Qh", "5h", "4h",
      "7d",
      "Kc","Jc","8c","5c"
      ))
  val E : Hand = Hand(List(
      "-s", //TODO: Handle this in Internal.scala and let user not write this line at all
      "10h", "3h",
      "Kd","Jd","10d","6d","4d","3d","2d",
      "Qc","9c","4c","3c"
      ))
  val S : Hand = Hand(List(
      "As", "Ks", "9s", "4s",
      "8h", "6h",
      "Ad", "Qd", "4d",
      "Ac","7c","6c","2c"
      ))
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
import BridgeExtender._
import Direction._

/**
 * This is an example of showing an auction and a hand.
 */
object Hand2 extends App{
  val auction =
    """
    - - 1c P
    1h x xx 1s
    P 3s AP
    """

  val N =
      """
      KQ97s
      A7h
      A1052d
      Q106c
      """
  val E =
      """
      A8s
      KQ5h
      J96d
      A7542c
      """
  val S =
      """
      J652s
      J842h
      Q43d
      98c
      """
  val W =
      """
      1043s
      T963h
      K87d
      KJ3c
      """


  val fullDeal = Deal(N,E,S,W)
  auction.display("None Vul")
  fullDeal.display()
}
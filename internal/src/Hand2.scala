import BridgeExtender._
import Direction._

object Hand2 extends App{
  /**
   * Auctions are entered with bids separated by spaces.
   * Use - to indicate no ability to bid (P for pass). W always starts the bidding.
   * Hands are entered as strings with 's', 'h', 'd', and 'c' to indicate the end of a suit
   * To create a deal, the incantation Deal(N,E,S,W) must be used where N, E, S, W are hands.
   * Deals and auction have display functions.
   */
  val auction =
    """
    - - 1c P
    1h x xx 1s
    P 3s AP
    """

  val N =
      """
      7s
      A1052d
      Q106c
      """
  val E =
      """
      Kh
      J96d
      A754c
      """
  val S =
      """
      6s
      J8h
      Q43d
      98c
      """
  val W ="96h K87d KJ3c"


  val fullDeal = Deal(N,E,S,W) // This line really shouldn't be required
  auction.display("None Vul")
  fullDeal.display()
}
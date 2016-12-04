import BridgeExtender._
import Direction._

object Program extends App{
  /**
   * Auctions are entered with bids separated by spaces.
   * Use - to indicate no ability to bid (P for pass). W always starts the bidding.
   * Hands are entered as strings with 's', 'h', 'd', and 'c' to indicate the end of a suit
   * To create a deal, the incantation Deal(N,E,S,W) must be used where N, E, S, W are hands.
   * Deals and auction have display functions.
   */
  val auction =
    """
    - - 3d 3NT
    P P P P
    4c 4d 4h 4s
    6NT P 7NT P
    P   x P   P
    xx AP
    """
  
  val scientific : Auction=
    """
    - P 2d* P
    2NT* P 3h* P
    4NT* P 5h* P
    6c* P 6s* P
    7s P P P
    """
  val annotations =
    """
    Precision: 10-15 HCP; 4414, 4405, or (43)15 shape
    Asking
    4=3=1=5, non-minimum
    RKC for spades
    2 keycards without the spade Q
    Club suit ask
    AK or KQ in clubs
    """

  val N =  
      """
      10653s
      kQ54h
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
      AQ5d
      A762c
      """
  val W ="QJ872s AJ972h 98d 10c"
  
  val playDiagram : PlayDiagram=
      """
      8d 7d 2d Qd
      6h 2h Kh 3h
      5c 3c Ac 10c
      2c 2s Kc 4c
      3s 3d As 7s
      8h 9h Qh 10h
      5s 4d Ks 8s
      Ad 9d 4h 6d
      5d 7h 5h 10d
      """


  val fullDeal = Deal(N,E,S,W)
  scientific.display(ann=annotations)
  auction.display("None Vul")
  fullDeal.display()
  // fullDeal.display(South, North)
  fullDeal.displayAfter(6, playDiagram)
  playDiagram.display()
  fullDeal.displayAfter(9, playDiagram)
}
# Bridge-DSL
This is the repository for the bridge DSL that I am creating for DSLs.

## Describe your project in five words
Easy, powerful, bridge hand language

## Audience
_Who are you helping, with your language? What is that person's
experience like now? How would their experience be better if you could help 
them?_

I'm helping bridge players.  This would help bridge players who want to study
hands, share hands with other players, and just keep a database of hands. Also
could help bridge organizations that help teach bridge players by providing an
easy way to share hands with others.

## Why a language?
_Why is a **DSL** appropriate for your user(s)? How does it address the need?_

Bridge already has a specialized language to talk about hands. You might here a bridge
player say "I was red on white and held ace king fifth, four small, ace king queen
and a stiff jack." Additionally there is a lot of information to organize and
describe about a bridge hand so having a nice langauge to do so would be useful.

## Why you?
_What excites you about this idea? How did you come up with it?_

I've run into a situation where I want to share hands with friends and this is
never super easy.  If we are talking it person I have an ok time of describing it,
but in person they can ask questions real time if I've forgotten to describe
something or if they have questions about it.  When I want to share a hand with
people when we aren't talking in person, typically I will take a screenshot of
the hand and send it to them.  This is imperfect for a number of reasons and I
would really like an easy way to share hands with people so that I can either
brag about hands I played well, ask for help with hands I struggled to find the
right play for, or to help teach people. It would also be nice to have an easy
way to write down hands I've played so that I can look back later and find
interesting hands when I want to teach a lesson about bridge.

Also, I think bridge is a really exciting game, but it is a bit behind the times in
technology.  This would be a way to make a DSL for a domain I really care about
and if I make it well it's possible people might want to use it.


## Interface (syntax)
_What kinds of things can the user say in your language. How, generally, do they
say it? **Be as specific as possible.** You don't need to design syntax; but you
should be more specific than "The user describes properties of unicorns.", e.g.,
by describing which unicorn properties (horn color, tattoo, etc.) are most 
important to users and what kinds of values those properties might have._ 

In bridge there are 2 main phases. First there is an "auction" where players make
bids to determine who is trying to make the "contract" and who is trying to defend.
A contract is a claim that you can make a particular number of "tricks" with a
particular trump suit.  A trick is composed of each player playing one card and
therefor there are 13 total tricks (the entire 52 card deck gets dealt out to 
the 4 players).  There is some vulnerability assosciated with the auction that
can affect the bidding.  Each partnership can either be vulnerable or not vulnerable.
After the auction, there is then the play of the cards. A user
must be able to input and see all the information that happened during the auction and play.
For the play this is relatively straight forward -- it's the order in which the
card are played.  The only possible additional information to provide for the
play could be to annotate when a player takes a long time to think or when a
player plays a card very quickly, as sometimes this might influence how one
decides to play the hand.  The auction is a bit more complex.  At a basic level
players must be able to specify what bids were made and the order the bids were
made in.  However, in bridge people play different "systems" which assign artificial
meanings to the bids. There must be a way to communicate this information as well.
I would propose two methods for communicating this information.  The user can have
the ability to describe the systems played by both partnerships (bridge is a 4
player game where there are 2 partnerships playing against eachother).  In addition
or instead of this there should also be the ability to annotate particular bids
that have some unusual meaning.  The advantage of this two pronged approach is
that describing a system can be very succinct an easy. For instance the user might
just be fine saying the one partnership is playing "2/1" and the other partnership
is playing "precision" and not defining any of the particular bids but letting the
reader look up those systems to find the meanings of the bids.  But in the case
that people are playing a very strange system or a system that the person writing
about the hand doesn't think the readers will know about, they can go in and give
explanations of any unusual bids. A third type of information that could be useful
would be general information about thoughts going into the hand.  For example
players might play differently if they think they are way behind in a team game
than if they think they have a comfortable lead.

I'm currently thinking that the user will input the hand in a format that is
convenient to be inputted for them and then the program will handle displaying
the data in an easy to read manner for people wanting to analyze the hand. I think
a hand diagram in text format might make sense for the output. Something like
```
        xx
        xx
        xxxxx
        xxxx
xxxx           xxx
xxxx           xxxx
xxxx           x
x              xxxxx

        xxxx
        xxx
        xxx
        xxx
```
where x's are replaced with the actual cards.

## Operation (semantics)
_What might happen when a program runs? Is there a computational model that
corresponds with your domain? How does a program interact with the
user? What kinds of semantic (i.e., non-syntax) errors might occur, and how 
might they be communicated to the user?_

When a program runs it will take the input by the user and display it in a nice
manner.  This might consist of creating a bridge diagram based off the input. It
could also be showing some end position -- that is showing the state of the cards
after some number of cards have been played. Another useful thing a user could do
when running the program would be to show some of the hands but not others. This
would be useful for sharing a hand that they want to ask someone how they would
play it without giving away what the right answer is (you could determine the
right answer (for some definition of right) if you could see the opponents hand).
Another use would be to show some or all of the hands a part of the bidding sequence
(remember that bidding is what players do during the "auction" phase of the game)
in order to query other bridge players about what they would have bid without
revealing what they actually did. I think it would be very cool if you could write
in a hand in a way that is convenient for you and then run the program to show the
hand or whatever part of the hand you want to show in a way that is convenient for
others. I would like this format to be easily sharable. One place I wish I could
share bridge hands easily is on social media like Facebook, where currently I think
the best way to share a hand is by sharing an image of a hand, which seems a bit
clunky to me.

After everything above is done to satisfaction a nice operation to be able to do
would be to be able to do a "double dummy" play analysis.  That is to say, determine
the optimal result if people could play with perfect information (see the other
players cards).  I think this is not critical to the language, since a purely
descriptive language would be very useful on its own.  However, this would make
the language much more powerful and still keep it in the domain of bridge hand
review.  It would also be desirable to do this analysis at any point during the
hand. That is to say, to do this analysis after a certain number of tricks have
been played in a non optimal manner.

I believe this is data description computational model since it is mainly displaying
the information the user gave it or pieces of information the user gave it in
nice ways.

A common syntax error that you will find in bridge books is players not having
the correct number of cards.  It is not uncommon to accidently see a misprint
where a player will have a 14 card hand (should be 13). I think an error message
should be given that not all players have the same number of cards.

Another syntax error could be giving two people the same card. This could be minimized
by allowing the program to autocomplete the 4th hand after 3 have been entered. I
think this would reduce the frequency of this type of error because if the 4th hand
doesn't appear the way the writer expects, then they will know they mistyped one
of the three hands they entered.

Another possible syntax error could be a player playing the same card twice or
playing a card not in their hand.  This would probably occur from a typo and we
should just alert the error with a convenient error message.

## Expressiveness
_What should be easy to do in this language? What should be possible, but
difficult? What should be impossible or very difficult?_

Inputting and then viewing hands should be very easy with this langauge.  Being
able to do some basic analysis should also fairly easy.  Additionally, showing an
piece of a hand or showing the hand in multiple states (such as at the begining
and after the first 8 tricks have been played) should be easy.

The intention is to use this for bridge hands so if one wanted to use this language
to describe some other game that is similar, such as say hearts or spades, it
might be difficult or impossible.

## Related work
_Are there any other DSLs in this domain? If not, describe how you know there
aren't and conjecture why not. If so, describe them and provide links. How well 
do they address the need? Are there any particularly admirable qualities of the
language? Are there parts of the language you think could be improved?_

[BridgeBase](http://www.bridgebase.com) offers the ability to export hands in a
.lin format, however this is hard to read.  Here is an example of a hand I
played: 
> pn|guplex,~~M12054,~
> ~M12052,~~M12053|st||md|1S58KAH269KDTKC34Q,S26JQH4D246JAC267,S349H37QAD3789C5A
> ,|rh||ah|Board 11|sv|o|mb|1N|an|notrump opener. Could have 5M. -- 2-5
> !C|mb|p|mb|2C|an|Stayman --   |mb|p|mb|2H|an|2-5 !C; 2-5 !D; 4-5 !H; 2-4 !S;
> 15-17 HC|mb|p|mb|4H|an|4+ !H; 10-13 total points |mb|p|mb|p|mb|p|pg||pc|C2|pc|
> C5|pc|CK|pc|C3|pg||pc|S7|pc|SA|pc|S6|pc|S3|pg||pc|C4|pc|C6|pc|CA|pc|CJ|pg||pc|
> D3|pc|DQ|pc|DK|pc|DA|pg||pc|SQ|pc|S4|pc|ST|pc|SK|pg||pc|H2|pc|H4|pc|HA|pc|H5|p
> g||pc|H3|pc|HT|pc|HK|pc|D4|pg||pc|CQ|pc|C7|pc|S9|pc|C8|pg||pc|DT|pc|DJ|pc|D7|p
> c|D5|pg||pc|S2|pc|HQ|pc|C9|pc|S5|pg||pc|D8|pc|H8|pc|H9|pc|D6|pg||pc|S8|pc|SJ|p
> c|H7|pc|CT|pg||pc|D9|pc|HJ|pc|H6|pc|D2|pg||

I think this could be more readable to humans.  Also I have difficulty finding
a way to process this data without writing something myself. It is nice that
you can do double dummy analysis and click through the tricks in the order that
they were played if you upload a .lin file to bridgebase. The main thing that this
doesnt offer is an easy way to share this hand with friends unless they want to
be sent the file and then upload it to bridgebase.  I would like to have a way
to output in an easy to read manner the hand and send it via email or social
media to my friends without having to force them to make a bridgebase account
and figure out how it works and without going through the clunky procedure of
screenshotting a hand.

## Suitability
_What percentage of your time do you think will be spent directly engaging in
the **language** aspects of this project (e.g., making language design
decisions), as opposed to "systems" aspects of the project (e.g., implementing a
complicated semantics that doesn't require a lot of language design)?_

I think it will probably be something like 2/3 of my time on design and 1/3 on
implementation with the exception of the part of the project to do the analysis
of the bridge hand.  I think doing the double dummy analysis will most likely
require a large amount of implementation work.

## Scope
_How big an idea is this? How ambitious is this project?_

I think this project has a moderate scope. At it's simplest it is quite simple.
Just put in bids in cards in some order and output them in a nicer format. However,
there are many more features for outputting and a few more descriptions that can
be input that I have described already that I think would be very useful to implement.

## Challenges and opportunities
_Why is this a good project? What are some challenges you expect to face? How
might you overcome them? What are some warning signs that the project has gotten
off track, and how will you get the project back on track if needed?_


This is a good project because it is in a domain I care about, it lends itself to
a language as can be seen by the verbal DSL associated with describing bridge hands
and cardplay, and the only existing DSL that does something similar has some 
limitations that my proposed DSL would overcome. One challenge I expect to face is
communicating about bridge to my classmates so that they can give me meaningful and
useful feedback.  I will try to give a detailed explanation about the game to the
people I am working with and if possible show them online what a bridge hand looks
like so they can get a feel of what the game at least looks like. A warning sign
I've gotten off track is that it no longer is easy to input a hand or is no longer
easy to share a hand or piece of a hand. If this is the case I've probably focused
on the wrong direction of the project and I should reevaluate what is truly important
for this DSL.  At this time I think the most important thing about the DSL is that
one can easily save a hand (if this is too much of a hassle people wont' use this
DSL to save their hands) and easily share a hand in a way that is easy for others
to look at an "see" the hand (that is that others can analyze the hand without
suffering any issues due to the way the hand is being displayed to them. It should
feel like you've laid out the deck of cards in front of them.).
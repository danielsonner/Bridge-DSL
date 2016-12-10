# Bridge-DSL
This is a DSL for recording bridge hands and outputting them in nicely formatted
plain text. To get started download or clone this repository and make sure you
have scala installed since this is an internal Scala domain specific language.
If you are on a mac and have homebrew installed simply running `brew install scala`
should work. To run the simple example program `cd` to the directory containing the
.scala files and type:
```
scalac Internal.scala
scalac SimpleExample.scala
scala SimpleExample
```
The first command compiles the internal DSL. The second command compiles the
sample program and the final command runs the sample program. When the sample
program is run you should see:
```
None Vul

 W   N   E   S
 -   -   1♣  P
 1♥  X   XX  1♠
 P   3♠  AP
             ♠KQ97
             ♥A7
             ♦AT52
             ♣QT6
♠T43                      ♠A8
♥T963                     ♥KQ5
♦K87                      ♦J96
♣KJ3                      ♣A7542

             ♠J652
             ♥J842
             ♦Q43
             ♣98
```
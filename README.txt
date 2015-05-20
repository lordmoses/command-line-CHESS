
Author - Moses Ike    http://mosesike.org     http://utdallas.edu/~mji120030

Command-line CHESS playing agent, with an AI engine implemented using..
Alpha-Beta (Min - Max) Adversarial Search Algorithm

For Compilation and execution, see README.RUN.txt


Alonso is the name of my TEXT-BASED Chess Playing AI Agent. 

Alonso's Chess Board is represented visually by an 8 X 8 rectangle grid, and internally by an 8 X 8 2D array.

My default Alpha Beta Search depth is 5 which averages about 2 to 5 seconds between plays.
The User can specify a custom search depth at the command line. 
For Practical Purposes, the Maximum Search Depth for my program is 6.


The key to faster plays with increasing search depth is in the implementation of MOVE ORDERING.
A Search Depth of 7 was taking Alonso 1 to 2 minutes to play.
Perhaps my MOVE ORDERING implementation isn't so efficient, 
but even at that, my current MOVE ORDERING strategy reduced Alonso's 'time to play' by more than half !.

My evaluation function was a rating I got from
http://chessprogramming.wikispaces.com/Simplified+evaluation+function 


Below is a screenshot of Alonso's execution:


java ChessApp 5


       .___.
    ,-^     ^-.
   /           \
  /  __     __  \
  | />>\   />>\ |
  | \__/   \__/ |      ALONSO
   \    /|\    / 
    \   \_/   /
     |       | 
     |+H+H+H+|
     \       /
      ^-----^
        ||
       /  \
        ||

- Default AI Intelligence level  5 -

WELCOME TO MOSES CHESS AI aka Alonso 1.0
PLEASE ADHERE TO <MOVE ENTERING> FORMAT WHEN ENTERING MOVE, see README.txt


              ---------------------------------
          0   | r | k | b | q | a | b | k | r |
              ---------------------------------
          1   | p | p | p | p | p | p | p | p |
              ---------------------------------
          2   |   |   |   |   |   |   |   |   |
              ---------------------------------
          3   |   |   |   |   |   |   |   |   |
              ---------------------------------
          4   |   |   |   |   |   |   |   |   |
              ---------------------------------
          5   |   |   |   |   |   |   |   |   |
              ---------------------------------
          6   | P | P | P | P | P | P | P | P |
              ---------------------------------
          7   | R | K | B | Q | A | B | K | R |
              ---------------------------------

                0   1   2   3   4   5   6   7

Press Enter to Continue 


waiting for Alonso to play..

Alonso Played: KNIGHT in 2.949 s Your Turn
              ---------------------------------
          0   | r |   | b | a | q | b | k | r |
              ---------------------------------
          1   | p | p | p | p | p | p | p | p |
              ---------------------------------
          2   |   |   | k |   |   |   |   |   |
              ---------------------------------
          3   |   |   |   |   |   |   |   |   |
              ---------------------------------
          4   |   |   |   |   |   |   |   |   |
              ---------------------------------
          5   |   |   |   |   |   |   |   |   |
              ---------------------------------
          6   | P | P | P | P | P | P | P | P |
              ---------------------------------
          7   | R | K | B | A | Q | B | K | R |
              ---------------------------------

                0   1   2   3   4   5   6   7

Your Possible Moves: 
6050 6040 6151 6141 6252 6242 6353 6343 6454 6444 6555 6545 6656 6646 6757 6747 7150 7152 7655 7657 
Enter Move: 

6454
You Played:PAWN in 6.778 s 
              ---------------------------------
          0   | r |   | b | a | q | b | k | r |
              ---------------------------------
          1   | p | p | p | p | p | p | p | p |
              ---------------------------------
          2   |   |   | k |   |   |   |   |   |
              ---------------------------------
          3   |   |   |   |   |   |   |   |   |
              ---------------------------------
          4   |   |   |   |   |   |   |   |   |
              ---------------------------------
          5   |   |   |   |   | P |   |   |   |
              ---------------------------------
          6   | P | P | P | P |   | P | P | P |
              ---------------------------------
          7   | R | K | B | A | Q | B | K | R |
              ---------------------------------

                0   1   2   3   4   5   6   7

waiting for Alonso to play..

Alonso Played: PAWN in 2.762 s Your Turn
              ---------------------------------
          0   | r |   | b | a | q | b | k | r |
              ---------------------------------
          1   | p | p | p | p |   | p | p | p |
              ---------------------------------
          2   |   |   | k |   |   |   |   |   |
              ---------------------------------
          3   |   |   |   |   | p |   |   |   |
              ---------------------------------
          4   |   |   |   |   |   |   |   |   |
              ---------------------------------
          5   |   |   |   |   | P |   |   |   |
              ---------------------------------
          6   | P | P | P | P |   | P | P | P |
              ---------------------------------
          7   | R | K | B | A | Q | B | K | R |
              ---------------------------------

                0   1   2   3   4   5   6   7

Your Possible Moves: 
5444 6050 6040 6151 6141 6252 6242 6353 6343 6555 6545 6656 6646 6757 6747 7150 7152 7364 7464 7564 7553 7542 7531 7520 7664 7655 7657 
Enter Move: 


^c


Below is a basic procedure I used

1.	Create Internal Board Representation
2.	Moves_List = get Possible Moves
3.	Moves_List = Move-Ordering(Moves-List)
4.	My_Move = Alpha_Beta(Moves_List)
5.	Execute(My_Move)
6	Check if Checkmate or stalemate
7.	Present Opponent with Possible Moves   (if text-based) 
8.	Check if Opponent's Move is a legal Move
9.	Check if Checkmate or stalemate
10.	Go to 2.

Please share your experience. and ENJOY

May the strongest AI Agent win.

Thank you
Moses Ike

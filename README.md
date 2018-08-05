# RealTime Battleship

Realtime Battleship is a simple server implementation of the classic game battleship, played between two players over a standard telnet connection.
To start a game simply run the program and then connect two telnet sessions to the server on port 5000. The game is "real time" instead of turn based so may the fastest guesser win!

Note: once a game has ended you may reconnect to the server and a new game will begin automatically.

# Implementation details:
## Flexiblity:
The game is built such that simply adding a new shop to the "Ships" enum will automatically place it into play. A method has been written to generate a game without ships as well, which would allow in the future to create an interface asking the user for a board size and to place their ships manually.

## Simplicity/Understandability
The game attempts to keep the code as simple as possible, while still allowing for maximum flexibility.  Game states are tracked on each individual grid item allowing a single location to find the status of the game.

## Avoiding Duplicated Code
Duplicate code is avoid by using proper method design, pulling any repeated code into it's own method or class.

## Design Patterns
- State Design Pattern: The game is implemented using the State pattern on the Grid Items themselves, using four states, hit, miss, open water, and hasBoat. This allows us to display the state of the square without a ton of if..then...else statement
- Singleton Design Pattern: The "game" session itself utilizes the Singleton pattern to ensure there is only one game running on the server at a time.



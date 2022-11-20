import java.util.*;
import java.awt.Color;

public class Quoridor
{
     public static void main (String [] beans)
     {
          //create Scanner
          Scanner in = new Scanner(System.in);
          
          //used to store selection from Scanner
          String choice = new String();
          
          //Booleans to test whether game is over, 
          //  if a piece was moved, 
          //  or if a wall was placed
          boolean isGameOver = false;
          boolean moved = false;
          boolean placed = false;
          
          //Create Players for the game and add the first two 
          //  to the array list because they are always used
          //int numPlayers = 2;
          ArrayList<Player> players = new ArrayList<Player>();
          Player p1 = new Player(4, 8, Player.UP,    "Player 1", "1", 1, 10, Color.RED); 
          players.add(p1);
          Player p2 = new Player(4, 0, Player.DOWN,  "Player 2", "2", 2, 10, Color.BLUE); 
          players.add(p2);
          //Create an object that stores the current player
          //  taking a turn to simplify the code for each turn
          Player currentPlayer = p1;
          Player winner = new Player();
          int player = 1;         
          
          System.out.println("QUORIDOR");
          
          //Prompt for how many players
     
          //Get player names
          for(int i = 0;  i < players.size(); i++)
          {
               System.out.println("What's " + players.get(i).getName() + "'s name?");
               players.get(i).setName(in.next());
          }
          
          //Create the board (and add players)
          //Board qBoard = new Board(p1, p2);      
          Board qBoard = new Board(players); 
          
          System.out.println(qBoard);//the board.toString() method prints a board graphic 
          
          //this is necessary for proper game flow due to an issue with Scanner
          in.nextLine();
          
          //This is the main game loop
          do
          {        
               //pause the game
               System.out.print("Press enter to continue...");
               in.nextLine();
               
               String turnIntro = new String("|It's " + currentPlayer.getName() + "'s turn!|");
               String divider = new String("+");
               for(int i = 0; i < (turnIntro.length() - 2); i++)
                    divider += "-";
               divider += "+";
               
               System.out.println(divider + "\n" + turnIntro + "\n" + divider);
               System.out.println("Move or Place Wall [Move = m, Place Wall = p]");
               
               choice = in.nextLine();
               //si el jugador le dio a enter o introdujo algo incorrecto este bucle lo verifica
               while(choice.equals("") || !(choice.toUpperCase().charAt(0) == 'M' 
                                                 || choice.toUpperCase().charAt(0) == 'P'))
               {
                    System.out.println("Error: Nothing Entered\n" + 
                                       "Please enter a choice [Move = m, Place Wall = p]");
                    choice = in.nextLine();
               }//end while
               
               //aqui efectuamos la eleccion de moverse o colocar el muro
               if(choice.toUpperCase().charAt(0) == 'M') //moverse
               {
                    System.out.println("ENTER:[UP, DOWN, LEFT, RIGHT]");
                    moved = currentPlayer.movePiece(in.nextLine(), qBoard);//calls move piece for current player
               }//end if
               else if(choice.toUpperCase().charAt(0) == 'P') //colocar muro
               {
                    if(currentPlayer.getNumWalls() > 0)                  
                    {                  
                         int x1, y1, x2, y2;
                         
                         //coordenadas del muro
                         System.out.println("ENTER:[Point (x1, y1); Point (x2, y2)]");
                         System.out.print("x1:");
                         x1 = in.nextInt();
                         System.out.print("y1:");
                         y1 = in.nextInt();
                         System.out.print("x2:");
                         x2 = in.nextInt();
                         System.out.print("y2:");
                         y2 = in.nextInt();                
                         
                         placed = currentPlayer.placeWall(new Wall(x1, y1, x2, y2), qBoard);
                         
                         //this is necessary for proper game flow due to an issue with Scanner
                         in.nextLine();
                    }
                    else
                    {
                         System.out.println("No hay mas muros!");
                         placed = false;
                    }
               }//end else if
               
               //This will be removed (or at least commented out when)
               //  the game is completed.  It prints out useful data 
               //  about the player who just took a turn and the board.
               System.out.println(qBoard.reDraw());
               System.out.println("Player: " + currentPlayer 
                                      + "\nx: " + currentPlayer.getX() 
                                       + "\ny: " + currentPlayer.getY() 
                                       + "\nPiece Moved: " + moved
                                      + "\nWall Placed: " + placed 
                                       + "\nWalls on board: " + qBoard.getWalls());          
               
               //verifica si el turno del jugador fue correcto
               if(moved || placed)
               {
                    //verificamos si el jugador gano
                    if(currentPlayer.checkWin())
                    {
                         isGameOver = true;
                         winner = currentPlayer;
                    }
                    //pasamos hacia el otro jugador
                    player++;
                    if (player > players.size())
                         player = 1;
                   // establecemos el actual jugador
                    for(int i = 0; i < players.size(); i++)
                         if(player == players.get(i).getNumber())
                         currentPlayer = players.get(i);
               }//end if              
               else//si el jugador no ha movido su pieza, como tampoco un muro 
                    System.out.println("Error: " +
                                       "Player Move/ Wall Placement Falied");
               
               //restablecemos los valores para la proxima ronda
               moved = false;
               placed = false;
          }
          while(!isGameOver);
          //end  do while(!isGameOver)
          
          System.out.println("Congratulations " + winner.getName() + "!\n\nYOU WIN!");
          
          System.out.println(System.nanoTime());
          
          
     }//end main    
     
}//end Quoridor class

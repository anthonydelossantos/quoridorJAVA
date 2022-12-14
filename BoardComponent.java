import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class BoardComponent extends JComponent implements MouseListener, MouseMotionListener
{
     private ArrayList<Player> players;
     private ArrayList<Wall> walls;
     private Line2D.Double wall;
     private SidePanel side;
     //private JFrame frame;
     
     private boolean drawing;
     
     private final int SIZE_CONST = 50;
     
     public BoardComponent(ArrayList<Player> p)//, SidePanel s)//, JFrame j)
     {
    	  this.addMouseListener(this);
    	  this.addMouseMotionListener(this);
    	  drawing = false;
    	 
          players = new ArrayList<Player>();
          walls   = new ArrayList<Wall>();
          wall = new Line2D.Double(0,0,0,0);
          
          //frame = j;
          
          for(Player i : p)
          {
               players.add(i);
          }
     }
     
     public void add(SidePanel s)
     {
    	  side = s;
     }
     
     
     public void paintComponent(Graphics g)
     {
          Graphics2D g2 = (Graphics2D) g;
          
          g2.setColor(Color.BLACK);
          g2.fill(new Rectangle(0, 0, SIZE_CONST * 9 + 15, SIZE_CONST * 9 + 15));
          g2.setColor(new Color(212, 125, 27));
          g2.fill(new Rectangle(0, 0, SIZE_CONST * 9, SIZE_CONST * 9));
          
          for(int i = 0; i < 9; i++)
          {
               for(int j = 0; j < 9; j++)
               {                    
                    for(int k = 0; k < players.size(); k++)
                    {
                         if(players.get(k).getX() == j && players.get(k).getY() == i)
                         {
                              g2.setColor(players.get(k).getColor());
                              g2.fill(new Ellipse2D.Double(SIZE_CONST * j + (SIZE_CONST / 4),
                            		  SIZE_CONST * i + (SIZE_CONST / 4), 
                            		  SIZE_CONST / 2, 
                            		  SIZE_CONST / 2));
                              g2.setColor(Color.BLACK);
                              g2.draw(new Ellipse2D.Double(SIZE_CONST * j + (SIZE_CONST / 4),
                            		  SIZE_CONST * i + (SIZE_CONST / 4), 
                            		  SIZE_CONST / 2, 
                            		  SIZE_CONST / 2));
                         }
                    }
                    
                    g2.setColor(Color.BLACK);
                    g2.draw(new Rectangle(SIZE_CONST * j, SIZE_CONST * i, SIZE_CONST, SIZE_CONST));
                    
               }
               
          }
          
          g2.setColor(Color.WHITE);
          
          for(int i = 0; i < walls.size(); i++)
          {
               g2.draw(new Line2D.Double(walls.get(i).getX1() * SIZE_CONST, 
                                         walls.get(i).getY1() * SIZE_CONST, 
                                         walls.get(i).getX2() * SIZE_CONST, 
                                         walls.get(i).getY2() * SIZE_CONST));
          }
          
          for(int i = 0; i < 10; i++)
        	  g2.drawString(Integer.toString(i), SIZE_CONST * i, SIZE_CONST * 9 + 10);
          
          for(int i = 0; i < 9; i++)
        	  g2.drawString(Integer.toString(i), SIZE_CONST * 9, SIZE_CONST * i + 10);
          
          g2.draw(wall);
          
          if(drawing)
          {
        	  //System.out.println("Drawing");
        	  g2.draw(new Wall(side.getX1(), side.getY1(),
			 			side.getX2(),side.getY2()));        	  
          }
          
          side.setVals(this);
          
          //this is how I figured out what size was optimal for the frame:
          //System.out.println("Height: " + frame.getHeight() + "\nWidth: " + frame.getWidth());
     }
     
     //GET AND SET METHODS
     
     public void addWall(Wall w)
     {
          walls.add(w);
     }
     
     public void addWall(int i, Wall w)
     {
          walls.add(i, w);
     }
     
     public Wall getWall(int i)
     {
          return walls.get(i);
     }
     
     public int getNumWalls()
     {
          return walls.size();
     }
     
     public Player getPlayer(int i)
     {
          return players.get(i); 
     }     
     
     public int getNumPlayers()
     {
          return players.size();
     }
     
     public String getWalls()
     {
          String w = new String();
          
          for(int i = 0; i < walls.size(); i++)
               w += walls.get(i) + "; ";
          
          if(w.equals(null))
               w = "<null>";
          
          return w;
     }
     
     
     public void mouseExited(MouseEvent e) {}
     public void mouseEntered(MouseEvent e) {}
     public void mouseClicked(MouseEvent e) 
     {
    	 wall.setLine(0,0,0,0);
     }
     public void mousePressed(MouseEvent e) 
     {
    	 wall.setLine(e.getX(), e.getY(), e.getX(), e.getY());
    	 drawing = true;
    	 this.repaint();
     }
     public void mouseReleased(MouseEvent e) 
     {
    	 drawing = false;
    	 
    	 boolean placed = players.get(side.getPlayer() - 1).placeWall(new Wall(
    			 			side.getX1(), side.getY1(),
    			 			side.getX2(),side.getY2()), this);
         
         //System.out.println(placed);
         //System.out.println((int)Math.round(wall.getX1() / 50));
         //System.out.println((int)Math.round(wall.getY1() / 50));
         //System.out.println((int)Math.round(wall.getX2() / 50));
         //System.out.println((int)Math.round(wall.getY2() / 50));
         
         if(placed)
         {
        	  side.incPlayer();
              if (side.getPlayer() > players.size())
            	   side.setPlayer(1);
              side.refresh();
         }
         
         this.repaint();
         
         wall.setLine(0,0,0,0);
     }
     
     public void mouseDragged(MouseEvent e) 
     {
    	 double x = wall.getX1();
    	 double y = wall.getY1();
    	 wall.setLine(x, y, e.getX(), e.getY());
    	 
    	 drawing = true;
    	 this.repaint();
     }
     public void mouseMoved(MouseEvent e) {}
     
     public Line2D.Double getNewWall()
     {
    	 return wall;
     }
}


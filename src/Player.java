   import java.awt.Color;
   public class Player
   {
      private Color color;
      public Player(Color c)
      {
         color = c;
      }
      public Color getColor()
      {
         return color;
      }
      public Player getEnemy()
      {
         if(Chess.instance.player1 == this)
            return Chess.instance.player2;
         if(Chess.instance.player2 == this)
            return Chess.instance.player1;
         return null;
      }
      public String getName()
      {
         return (color.equals(Color.WHITE) ? "White" : "Black");
      }
   }
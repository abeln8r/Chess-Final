   import java.awt.*;
   import java.util.*;
   import util.*;
   public class Knight extends ChessPiece
   {
      public Knight(Color c, int row, int col)
      {
         super(c, row, col);
      }
      public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean check)
      {
         ArrayList<Location> locs = new ArrayList();
         Location[] testLocs = {
               getLocation().add(1,2),getLocation().add(2,1), getLocation().add(-1,2),//all possible knight moves
               getLocation().add(2,-1), getLocation().add(-2,1), getLocation().add(1,-2),
               getLocation().add(-1,-2),getLocation().add(-2,-1)
               };
         for(int i =0; i< testLocs.length; i++)
         {
            if(matrix.isValid(testLocs[i]) && (matrix.get(testLocs[i]) == null || isEnemy(matrix.get(testLocs[i]))))
            {
               if(check || !willMoveCauseCheck(testLocs[i], matrix))
                  locs.add(testLocs[i]);
            }
         }
         return locs;
      }
      public int getRank()
      {
         return 3;
      }
   }

   import java.awt.*;
   import java.util.*;
   import util.*;

   public class Bishop extends ChessPiece {
   
      public Bishop(Color c, int row, int col) {
         super(c, row, col);
      }
   
      public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean fix) {
         ArrayList<Location> list = new ArrayList();
         int i = 1;//4 directions
         while(true)
         {
            Location loc = getLocation().add(i, i);
            if(!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc))))
               break;
            if(fix || !willMoveCauseCheck(loc, matrix))
               list.add(loc);
            if(matrix.get(loc) != null && isEnemy(matrix.get(loc)))
               break;
            i++;
         }
         i = 1;
         while(true)
         {
            Location loc = getLocation().add(i, -i);
            if(!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc))))
               break;
            if(fix || !willMoveCauseCheck(loc, matrix))
               list.add(loc);
            if(matrix.get(loc) != null && isEnemy(matrix.get(loc)))
               break;
            i++;
         }
         i = 1;
         while(true)
         {
            Location loc = getLocation().add(-i, i);
            if(!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc))))
               break;
            if(fix || !willMoveCauseCheck(loc, matrix))
               list.add(loc);
            if(matrix.get(loc) != null && isEnemy(matrix.get(loc)))
               break;
            i++;
         }
         i = 1;
         while(true)
         {
            Location loc = getLocation().add(-i, -i);
            if(!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc))))
               break;
            if(fix || !willMoveCauseCheck(loc, matrix))
               list.add(loc);
            if(matrix.get(loc) != null && isEnemy(matrix.get(loc)))
               break;
            i++;
         }
         return list;
      }
		public int getRank()
	 {
	 	return 3;
	 }
   }

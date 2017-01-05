
   import java.awt.*;
   import java.util.*;
   import util.*;

   public class Rook extends ChessPiece {
   
      public Rook(Color c, int row, int col) {
         super(c, row, col);
      }
   
      public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean check) {
         ArrayList<Location> list = new ArrayList();
         if (!hasMoved()) {
            King king = getKing(matrix);
            if(king != null)//weird...
            {
               ArrayList<Location> kingLocs = king.getMoveLocations(matrix, check);//check if can castle with king
               if (!king.hasMoved() && kingLocs != null && kingLocs.contains(getLocation())) {
                  list.add(king.getLocation());//if possible add king loc to move locs
               }
            }
         }
         int i = 1;//4 directions
         while (true) {
            Location loc = getLocation().add(i, 0);
            if (!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc)))) {
               break;
            }
            if (check || !willMoveCauseCheck(loc, matrix)) {
               list.add(loc);
            }
            if (matrix.get(loc) != null && isEnemy(matrix.get(loc))) {
               break;
            }
            i++;
         }
         i = 1;
         while (true) {
            Location loc = getLocation().add(-i, 0);
            if (!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc)))) {
               break;
            }
            if (check || !willMoveCauseCheck(loc, matrix)) {
               list.add(loc);
            }
            if (matrix.get(loc) != null && isEnemy(matrix.get(loc))) {
               break;
            }
            i++;
         }
         i = 1;
         while (true) {
            Location loc = getLocation().add(0, i);
            if (!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc)))) {
               break;
            }
            if (check || !willMoveCauseCheck(loc, matrix)) {
               list.add(loc);
            }
            if (matrix.get(loc) != null && isEnemy(matrix.get(loc))) {
               break;
            }
            i++;
         }
         i = 1;
         while (true) {
            Location loc = getLocation().add(0, -i);
            if (!matrix.isValid(loc) || (matrix.get(loc) != null && !isEnemy(matrix.get(loc)))) {
               break;
            }
            if (check || !willMoveCauseCheck(loc, matrix)) {
               list.add(loc);
            }
            if (matrix.get(loc) != null && isEnemy(matrix.get(loc))) {
               break;
            }
            i++;
         }
         return list;
      }
      public int getRank()
      {
         return 5;
      }
   }

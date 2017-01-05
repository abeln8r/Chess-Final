
   import java.awt.*;
   import java.util.*;
   import util.*;

   public class Queen extends ChessPiece {
   
      public Queen(Color c, int row, int col) {
         super(c, row, col);
      }
   
      public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean fix) {
         ArrayList<Location> list = new ArrayList();
         Bishop bishop = new Bishop(getColor(), getLocation().getRow(), getLocation().getCol());
         Rook rook = new Rook(getColor(), getLocation().getRow(), getLocation().getCol());
         for (Location l : bishop.getMoveLocations(matrix, fix)) {
            list.add(l);
         }
         for (Location l : rook.getMoveLocations(matrix, fix)) {
            list.add(l);
         }
         return list;
      }
      public int getRank()
      {
         return 9;
      }
   }

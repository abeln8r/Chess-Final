
   import java.awt.*;
   import java.util.*;
   import util.*;

   public class King extends ChessPiece {
   
      public King(Color c, int row, int col) {
         super(c, row, col);
      }
   
      public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean check) {
         ArrayList<Location> list = new ArrayList();
         if (!hasMoved()) {
            Location rook1 = getLocation().add(0, 3);
            Location rook2 = getLocation().add(0, -4);
            //checks if rook is not null, has not moved and there are no pieces between it and the king
            if (matrix.get(rook1) != null && !matrix.get(rook1).hasMoved() && !matrix.isColImpeded(getLocation(), rook1) && (check || !willMoveCauseCheck(rook1, matrix))) {
               list.add(rook1);
            }
            if (matrix.get(rook2) != null && !matrix.get(rook2).hasMoved() && !matrix.isColImpeded(getLocation(), rook2) && (check || !willMoveCauseCheck(rook2, matrix))) {
               list.add(rook2);
            }
         }
         for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
               Location temp = getLocation().add(i, j);
               if (matrix.isValid(temp) && (matrix.get(temp) == null || isEnemy(matrix.get(temp)))) {
                  if (check || !willMoveCauseCheck(temp, matrix)) {
                     list.add(temp);
                  }
               }
            }
         }
         return list;
      }
   
      public boolean inCheck(SparseMatrix<ChessPiece> matrix) {
         for (int row = 0; row < matrix.numRows(); row++) {
            for (int col = 0; col < matrix.numCols(); col++) {
               ChessPiece temp = matrix.get(row, col);
               if (temp != null && isEnemy(temp)) {
                  if (temp.getMoveLocations(matrix, true).contains(getLocation())) {
                     return true;
                  }
               }
            }
         }
         return false;
      }
   
      public boolean inCheckmate(SparseMatrix<ChessPiece> matrix) {
         if (!inCheck(matrix)) {
            return false;
         }
         for (int row = 0; row < matrix.numRows(); row++) {
            for (int col = 0; col < matrix.numCols(); col++) {
               ChessPiece temp = matrix.get(row, col);
               if (temp != null && !isEnemy(temp) && temp.getMoveLocations(matrix, false).size() > 0) {
                  return false;
               }
            }
         }
         return true;
      }
      
      public boolean inStalemate(SparseMatrix<ChessPiece> matrix) {
         if(matrix.size() == 2)//only two kings are left on the board
            return true;    
         if (inCheck(matrix)) {
            return false;
         }
         for (int row = 0; row < matrix.numRows(); row++) {
            for (int col = 0; col < matrix.numCols(); col++) {
               ChessPiece temp = matrix.get(row, col);
               if (temp != null && !isEnemy(temp) && temp.getMoveLocations(matrix, false).size() > 0) {
                  return false;
               }
            }
         }
         return true;
      }

      public int getRank()
      {
         return -1;
      }
   }

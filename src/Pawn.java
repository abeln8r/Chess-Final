
import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;
import util.*;

public class Pawn extends ChessPiece {

    public Pawn(Color c, int row, int col) {
        super(c, row, col);
    }
    /** 
     * Returns whether this pawn can be promoted
     * @return whether it can be promoted
    */
    public boolean canBePromoted() {
        return (getColor().equals(Color.BLACK) && getLocation().getRow() == 7) || (getColor().equals(Color.WHITE) && getLocation().getRow() == 0);
    }
    /** 
     * Replaces pawn with specified piece
     
     * @param matrix current instance of matrix
     * @param piece piece to be promoted to (Q0,B1,K2,R3)
    */
    public void promote(SparseMatrix<ChessPiece> matrix, int piece) {
        ChessPiece upgrade = null;
        switch (piece) {
            case 0:
                upgrade = new Queen(getColor(), getLocation().getRow(), getLocation().getCol());
                break;
            case 1:
                upgrade = new Bishop(getColor(), getLocation().getRow(), getLocation().getCol());
                break;
            case 2:
                upgrade = new Knight(getColor(), getLocation().getRow(), getLocation().getCol());
                break;
            case 3:
                upgrade = new Rook(getColor(), getLocation().getRow(), getLocation().getCol());
                break;
        }
        matrix.set(getLocation().getRow(), getLocation().getCol(), upgrade);
    }
    /** 
     * Replaces pawn with specified piece, asks for input
     
     * @param matrix current instance of matrix
    */
    public void promote(SparseMatrix<ChessPiece> matrix) {
        String[] text = {"Queen", "Bishop", "Knight", "Rook"};   
        int returnValue = -1;
        while(returnValue == -1)
            returnValue = JOptionPane.showOptionDialog(Chess.instance, "Promote your pawn", "Promote your pawn", 0, JOptionPane.INFORMATION_MESSAGE, null, text, null);
        promote(matrix,returnValue);
    }

    public ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean check) {
        ArrayList<Location> locs = new ArrayList();
        Location temp = getLocationAheadOrToTheRight(2, 0);//foreward 2
        if (matrix.isValid(temp) && !hasMoved() && matrix.get(temp) == null && matrix.get(getLocationAheadOrToTheRight(1, 0)) == null) {
            if (check || !willMoveCauseCheck(temp, matrix)) {
                locs.add(temp);
            }
        }
        temp = getLocationAheadOrToTheRight(1, 0);//foreward 1
        if (matrix.isValid(temp) && matrix.get(temp) == null) {
            if (check || !willMoveCauseCheck(temp, matrix)) {
                locs.add(temp);
            }
        }
        temp = getLocationAheadOrToTheRight(1, 1);//foreward right
        if (matrix.isValid(temp) && matrix.get(temp) != null && isEnemy(matrix.get(temp))) {
            if (check || !willMoveCauseCheck(temp, matrix)) {
                locs.add(temp);
            }
        }
        temp = getLocationAheadOrToTheRight(1, -1);//foreward left
        if (matrix.isValid(temp) && matrix.get(temp) != null && isEnemy(matrix.get(temp))) {
            if (check || !willMoveCauseCheck(temp, matrix)) {
                locs.add(temp);
            }
        }
        return locs;
    }
	 public int getRank()
	 {
	 	return 1;
	 }
}

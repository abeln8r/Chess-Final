
import java.awt.*;
import util.*;
import java.util.*;

public class AIPlayer extends Player {

    private int difficulty;

    public AIPlayer(Color c, int difficulty) {
        super(c);
        if(difficulty != 1 && difficulty != 2)
          throw new IllegalArgumentException("Invaild difficulty");
        this.difficulty = difficulty;
    }
    /** 
     * Gets random piece that can move
     * @param matrix current instance of matrix
     * @return piece that can move
    */
    private Location getMovableRandomPiece(SparseMatrix<ChessPiece> matrix) {
        Location rtr = null;
        while (rtr == null) {
            int row = (int) (Math.random() * 8);
            int col = (int) (Math.random() * 8);
            ChessPiece temp = matrix.get(row, col);
            if (temp != null && temp.getColor() == getColor() && temp.getMoveLocations(matrix, false).size() > 0) {
                rtr = new Location(row, col);
                break;
            }
        }
        return rtr;
    }
    /** 
     * Returns piece that can the most valuable enemy piece
     * @param matrix current instance of matrix
     * @return array of piece that can kill and location of enemy
     */
    private Object[] findKillAbleEnemies(SparseMatrix<ChessPiece> matrix)
    {
        ChessPiece ret = null;
        Location retL = null;
        for (int r = 0; r < matrix.numRows(); r++) {
            for (int c = 0; c < matrix.numCols(); c++) {
                ChessPiece piece = matrix.get(r, c);
                if (piece != null) {
                    ArrayList<Location> moveLocs = piece.getMoveLocations(matrix, false);
                    for (int i = 0; i < moveLocs.size(); i++) {
                        ChessPiece enemy = matrix.get(moveLocs.get(i));
                        if (enemy != null && enemy.getColor() != getColor() && (retL == null || enemy.compareTo(matrix.get(retL)) > 0)) {
                            ret = piece;
                            retL = enemy.getLocation();
                        }
                    }
                }
            }
        }
        return new Object[]{ret, retL};
    }
    /** 
     * Gets move method from difficulty level
     * @param matrix current instance of matrix
     * @return piece that moved
    */
    public ChessPiece getAIMove(SparseMatrix<ChessPiece> matrix)
    {
        switch(difficulty)
        {
            case 1:
                return difficulty1(matrix);
            case 2:
                return difficulty2(matrix);
            default:
                return null;
        }
    }
    /** 
     * Moves a random piece to a random location
     * @param matrix current instance of matrix
     * @return piece that moved
    */
    public ChessPiece difficulty1(SparseMatrix<ChessPiece> matrix)
    {
        ChessPiece piece = matrix.get(getMovableRandomPiece(matrix));
        ArrayList<Location> locs = piece.getMoveLocations(matrix, false);
        piece.move(locs.get((int) (Math.random() * locs.size())), matrix);
        return piece;
    }
    /** 
     * Kills enemy most valuable piece if possible
     * @param matrix current instance of matrix
     * @return piece that moved
    */
    public ChessPiece difficulty2(SparseMatrix<ChessPiece> matrix)
    {
        Object[] kl = findKillAbleEnemies(matrix);
        if(kl[0] == null || kl[1] == null)//no piece can be taken
            return difficulty1(matrix);//so select random move
        ChessPiece piece = (ChessPiece)kl[0];
        Location other = (Location)kl[1];
        piece.move(other, matrix);
        return piece;
    }
    public void makeMove(SparseMatrix<ChessPiece> matrix) {
        ChessPiece moved = getAIMove(matrix);
        if (moved instanceof Pawn && ((Pawn) moved).canBePromoted()) {
            ((Pawn) moved).promote(matrix, 0);//promote pawn to queen if possible
        }
    }
}

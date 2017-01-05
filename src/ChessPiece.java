
import java.awt.*;
import java.util.ArrayList;
import util.*;
import javax.swing.ImageIcon;

public abstract class ChessPiece implements Cloneable, Comparable {

    private Color color;
    private Location location;
    private Image image;
    private boolean hasMoved;

    public ChessPiece(Color c, int row, int col) {
        if (!c.equals(Color.BLACK) && !c.equals(Color.WHITE)) {
            throw new IllegalArgumentException("Invaild color specified");
        }
        color = c;
        if (row > 7 || row < 0) {
            throw new IllegalArgumentException("Bad row");
        }
        if (col > 7 || col < 0) {
            throw new IllegalArgumentException("Bad col");
        }
        location = new Location(row, col);
        hasMoved = false;
    }

    public Color getColor() {
        return color;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    /** 
     * Moves to a new location. 
     * If target is enemy, remove it. 
     * If target is friend, castle.
     * @param other location to move to
     * @param matrix current instance of matrix
     */ 
    public void move(Location other, SparseMatrix<ChessPiece> matrix) {
        if (matrix.get(other) != null && !isEnemy(matrix.get(other))) {//castle
            ChessPiece piece = matrix.get(other);
            piece.setLocation(getLocation());
            matrix.set(piece.getLocation().getRow(), piece.getLocation().getCol(), piece);

            location = other;
            matrix.set(location.getRow(), location.getCol(), this);
            hasMoved = true;
            return;
        }
        matrix.remove(other.getRow(), other.getCol());
        matrix.remove(location.getRow(), location.getCol());
        matrix.add(other.getRow(), other.getCol(), this);
        location = other;//update location
        hasMoved = true;
    }
    /** 
     * Checks if a move will put yourself in check
     * @param other location to move to
     * @param matrix current instance of matrix
     * @return whether the move will cause check
     */
    public boolean willMoveCauseCheck(Location other, SparseMatrix<ChessPiece> matrix) {
        try {
            ChessPiece piece = (ChessPiece) clone();
            SparseMatrix<ChessPiece> copy = copy(matrix);
            piece.move(other, copy);
            return piece.getKing(copy).inCheck(copy);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /** 
     * Copies the matrix and all of its pieces
     * @param matrix current instance of matrix to be copied
     * @return the new copy
     */
    private SparseMatrix<ChessPiece> copy(SparseMatrix<ChessPiece> matrix) {
        SparseMatrix<ChessPiece> copy = new SparseMatrix(matrix.numRows(), matrix.numCols());
        for (int row = 0; row < matrix.numRows(); row++) {
            for (int col = 0; col < matrix.numCols(); col++) {
                ChessPiece item = matrix.get(row, col);
                if (item != null) {
                    try {
                        copy.add(row, col, (ChessPiece) item.clone());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return copy;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    /** 
     * Gets king
     * @param matrix current instance of matrix
     * @return this piece's king
     */
    public King getKing(SparseMatrix<ChessPiece> matrix) {
        King k = null;
        for (int r = 0; r < matrix.numRows(); r++) {
            for (int c = 0; c < matrix.numCols(); c++) {
                ChessPiece temp = matrix.get(r, c);
                if (temp instanceof King && !isEnemy(temp)) {
                    k = (King) temp;
                }
            }
        }
        return k;
    }
   /** 
     * Gets a location ahead or to the right
     * @param ahead spaces ahead
     * @param right spaces to the right
     * @return location ahead or to the right
     */
    public Location getLocationAheadOrToTheRight(int ahead, int right) {
        int delta = 0;
        if (getColor().equals(Color.WHITE)) {
            delta = -1;
        }
        if (getColor().equals(Color.BLACK)) {
            delta = 1;
        }
        return getLocation().add(delta * ahead, -delta * right);
    }
    
    public boolean isEnemy(ChessPiece other) {
        return !color.equals(other.getColor());
    }
    /** 
     * Returns piece's image, if null creates the image
     * @return chess piece's image
     */
    public Image getImage() {
        if (image == null) {
            if (color.equals(Color.BLACK)) {
                image = new ImageIcon(getClass().getResource("/img/" + getClass().getSimpleName() + "Black.png")).getImage();
            }
            if (color.equals(Color.WHITE)) {
                image = new ImageIcon(getClass().getResource("/img/" + getClass().getSimpleName() + "White.png")).getImage();
            }
        }
        return image;
    }

    public String toString() {
        if (color.equals(Color.BLACK)) {
            return "" + getClass().getName().toLowerCase().charAt(0);
        }
        return "" + getClass().getName().charAt(0);
    }
   
    public int compareTo(Object o) {
        if(!(o instanceof ChessPiece))
           throw new IllegalArgumentException();
        ChessPiece piece = (ChessPiece) o;
        return getRank() - piece.getRank();
    }
    /** 
     * Value of chess piece, used by AI to determine best piece to take
     * @return value or rank of piece
     */
    public abstract int getRank();
    /** 
     * Returns move locations
     * @param matrix current instance of matrix
     * @param check determines whether the locations should not be checked for causing check
     * @return ArrayList of locations that are valid move locations
     */
    public abstract ArrayList<Location> getMoveLocations(SparseMatrix<ChessPiece> matrix, boolean check);
}

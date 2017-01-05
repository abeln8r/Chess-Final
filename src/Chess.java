
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import util.*;

public class Chess extends JPanel implements MouseListener, ActionListener {

   public static final int TILE_SIZE = 75;
   public static int SCREEN = TILE_SIZE * 8;
   public static final int SIDE = 30;
   public static Chess instance;
   private SparseMatrix<ChessPiece> matrix;
   /** 
     ChessPiece that was clicked on
   */ 
   private Location selected;
   private Player currentPlayer;
   public Player player1;
   public Player player2;
   private JFrame frame;

   public static void main(String[] args) {
      instance = new Chess();
   }

   public Chess() {
      frame = new JFrame("Chess");
      frame.setSize(SCREEN + SIDE + 6, SCREEN + SIDE + 28);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(this);
      frame.setResizable(false);
      addMouseListener(this);
      matrix = new SparseMatrix(8, 8);
      setupBoard(Color.WHITE);
      setupBoard(Color.BLACK);
      int returnValue = -1;
      while (returnValue == -1) {
         returnValue = JOptionPane.showConfirmDialog(this, "Do you want to play against the AI?", "Do you want to play against the AI?", 0);
      }
      player1 = new Player(Color.WHITE);
      if (returnValue == 0) {//ai true
         returnValue = -1;
         while(returnValue == -1)
            returnValue = JOptionPane.showOptionDialog(Chess.instance, "Select AI difficulty", "Select AI difficulty", 0, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Super Easy","Easy"}, null);
         player2 = new AIPlayer(Color.BLACK, returnValue+1);
      } 
      else {
         player2 = new Player(Color.BLACK);
      }
      startGame();
   }

   private void startGame() {
      currentPlayer = player1;
      frame.setVisible(true);
      Timer timer = new Timer(20, this);//slight delay
      timer.start();
   }
   /** 
     * Creates and places the pieces for the argument color
     *
     * @param color the color of the pieces to be initalized
   */
   private void setupBoard(Color color) {
      int row = (color.equals(Color.BLACK)) ? 0 : 7;//king's row
      int row1 = (color.equals(Color.BLACK)) ? 1 : 6;//pawn's row
      matrix.add(row, 0, new Rook(color, row, 0));
      matrix.add(row, 1, new Knight(color, row, 1));
      matrix.add(row, 2, new Bishop(color, row, 2));
      matrix.add(row, 3, new Queen(color, row, 3));
      matrix.add(row, 4, new King(color, row, 4));
      matrix.add(row, 5, new Bishop(color, row, 5));
      matrix.add(row, 6, new Knight(color, row, 6));
      matrix.add(row, 7, new Rook(color, row, 7));
      for (int i = 0; i < 8; i++) {
         matrix.add(row1, i, new Pawn(color, row1, i));
      }
   }
   /** 
     * Draws only the board
     *
     * @param g current instance of Graphics
   */
   private void paintBoard(Graphics g)
   {
      g.setColor(Color.GRAY);
      g.fillRect(0, 0, SCREEN + SIDE + 6, SCREEN + SIDE + 28);
      Color col;
      for (int r = 0; r < matrix.numRows(); r++) {
         for (int c = 0; c < matrix.numCols(); c++) {
            if (r % 2 == 0) {
               if (c % 2 == 1) {
                  col = Color.BLACK;
               } 
               else {
                  col = Color.WHITE;
               }
            } 
            else if (c % 2 == 0) {
               col = Color.BLACK;
            } 
            else {
               col = Color.WHITE;
            }
            g.setColor(col);
            g.fillRect(r * TILE_SIZE + SIDE, c * TILE_SIZE, TILE_SIZE, TILE_SIZE);
         }
      }
   }
   /** 
     * Main draw method, calls paintBoard and draws everything else
     *
     * @param g current instance of Graphics
   */
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      paintBoard(g);
      if (selected != null && matrix.get(selected) != null) {
         g.setColor(Color.BLUE);
         g.fillRect(selected.getCol() * TILE_SIZE + SIDE, selected.getRow() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
         for (Location loc : matrix.get(selected).getMoveLocations(matrix, false)) {
            if (matrix.get(loc) != null && !matrix.get(loc).getColor().equals(currentPlayer.getColor())) {
               g.setColor(Color.RED);//enemy
            } 
            else {
               g.setColor(Color.YELLOW);//empty or friend
            }
            g.fillRect(loc.getCol() * TILE_SIZE + SIDE, loc.getRow() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
         }
      }
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      for (int r = 0; r < matrix.numRows(); r++) {
         g.drawString("" + (8 - r), 20, r * TILE_SIZE + (TILE_SIZE / 2));//side nums
         for (int c = 0; c < matrix.numCols(); c++) {
            if (r == 0) {
               g.drawString("" + (char) (c + 97), c * TILE_SIZE + SIDE + (TILE_SIZE / 2), SCREEN + 20);//side nums
            }
            ChessPiece piece = matrix.get(r, c);
            if (piece instanceof King && ((King) piece).inCheck(matrix)) {//paint red for check
               g.setColor(Color.RED.darker().darker());
               g.fillRect(c * TILE_SIZE + SIDE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
               g.setColor(Color.BLACK);
            }
            if (piece != null) {//draw image
               g.drawImage(piece.getImage(), c * TILE_SIZE + SIDE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
         }
      }
   }
   /** 
     * Returns an instance of king with the color specified
     *
     * @param color color of the king to be returned
     * @return a king with the color
   */
   public King getKing(Color color) {
      King k = null;
      for (int r = 0; r < matrix.numRows(); r++) {
         for (int c = 0; c < matrix.numCols(); c++) {
            ChessPiece temp = matrix.get(r, c);
            if (temp instanceof King && temp.getColor().equals(color)) {
               k = (King) temp;
            }
         }
      }
      return k;
   }

   public void mousePressed(MouseEvent e) {
      if (e.getY() < SCREEN && e.getX() >= SIDE) {
         int row = (e.getY()) / TILE_SIZE;
         int col = (e.getX() - SIDE) / TILE_SIZE;
         Location loc = new Location(row, col);
         if (selected != null && matrix.get(selected).getMoveLocations(matrix, false).contains(loc)) {//move valid and piece selected
            ChessPiece piece = matrix.get(selected);
            piece.move(loc, matrix);
            if (piece instanceof Pawn && ((Pawn) piece).canBePromoted()) {
               ((Pawn) piece).promote(matrix);
            }
            currentPlayer = currentPlayer.getEnemy();
            selected = null;
         } 
         else {
            if (matrix.get(loc) != null && !(currentPlayer instanceof AIPlayer) && matrix.get(loc).getColor().equals(currentPlayer.getColor())) {
               selected = loc;//piece not null, not ai turn and check current turn
            } 
            else {
               selected = null;
            }
         }
      } 
      else {
         selected = null;
      }
      repaint();
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
   }
   
   /** 
     * Game logic thread, called by the timer defined in the constructor
     * <p>
     * Handles AI and checks for checkmate and stalemate
     * @param event event that called this
   */
   public void actionPerformed(ActionEvent event) {
      King king = getKing(currentPlayer.getColor());
      if (king.inCheckmate(matrix)) {
         repaint();
         JOptionPane.showMessageDialog(this, "CHECKMATE, " + currentPlayer.getEnemy().getName() + " Wins");
         System.exit(0);
      } 
      else if (king.inStalemate(matrix)) {
         repaint();
         JOptionPane.showMessageDialog(this, "STALEMATE, DRAW");
         System.exit(0);
      }
      if (currentPlayer instanceof AIPlayer) {
         AIPlayer ai = (AIPlayer) currentPlayer;
         ai.makeMove(matrix);
         currentPlayer = currentPlayer.getEnemy();
         repaint();
      }
   }
}

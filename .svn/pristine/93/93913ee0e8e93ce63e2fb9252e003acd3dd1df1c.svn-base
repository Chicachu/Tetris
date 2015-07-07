/*
 * NextPiecePanel.java
 * TCSS 305 - Spring 2013
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import model.Board;
import model.MutablePiece;

/** 
 * Panel that displays the next piece. 
 * @author Erica Putsche
 * @version 05/31/2013
 */
@SuppressWarnings("serial")
public class NextPiecePanel extends AbstractPanel {
  
  /**
   * THe grid.
   */
  private final GridPanel my_grid;
  
  /**
   *  A constructor for the next piece panel. 
   *  @param the_board The board.
   *  @param the_grid The visual representation of the board.
   */
  public NextPiecePanel(final Board the_board, final GridPanel the_grid) {
    super(the_board); 
    my_grid = the_grid;
  }
  
  /** 
   * Draws the next piece to the next piece panel.
   * @param the_graphics The graphics.
   */
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;  
    
    final MutablePiece next_piece = getBoard().getNextPiece();
    final String representation = next_piece.toString();
    
    int y_coord = 1;
    double x_coord = 0;
    
    for (int i = 0; i < representation.length(); i++) {
      if (representation.charAt(i) == '[') {
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int) (x_coord * my_grid.getSquareWidth()), 
                              y_coord * my_grid.getSquareHeight(), 
                              my_grid.getSquareWidth(), my_grid.getSquareHeight());
      } else if (representation.charAt(i) == ']') {
        x_coord++;
      } else if (representation.charAt(i) == ' ' && representation.charAt(i + 1) == ' ') {
        if (!"LPiece".equals(next_piece.getClass().getSimpleName())) {
          x_coord++;
        } else if (representation.charAt(i + 2) == ' ') { 
          x_coord++;
        }
      } else if (representation.charAt(i) == '\n') {
        y_coord++;
        x_coord = 0;
      }
    }
  }

  @Override
  public void update(final Observable the_o, final Object the_arg) {
    repaint();    
  }
}

/*
 * StatisticsPanel.java
 * TCSS 305 - Spring 2013
 */

package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import model.Board;

/** 
 * Keeps track of and displays the rows cleared, level, and score for Tetris.
 * @author Erica Putsche
 * @version 06/07/2013
 */
@SuppressWarnings("serial")
public class StatisticsPanel extends AbstractPanel implements Observer {
  
  /** Number of rows to be cleared before the level goes up. */
  private static final int ROWS_TO_LEVEL = 10;
  
  /** 
   * The y coordinate. 
   */
  private static final int Y_COORD = 15; 
  
  /** Score factor. */
  private static final int SCORE_FACTOR = 120;
  
  private static final int NUM_TOP_SCORES = 10;
  
  /** The current level. */
  private int my_level;
  
  /** Number of rows cleared. */
  private int my_rows_cleared;
  
  /** The score. */
  private int my_score;
  
  private Map<String, Integer> my_high_scores;
  
  /** 
   * The visual representation of the board.
   */
  private final GridPanel my_grid;
  
  /**
   * A constructor for the statistics panel.
   * @param the_board The board.
   * @param the_grid The visual representation of the board.
   */
  public StatisticsPanel(final Board the_board, final GridPanel the_grid) {
    super(the_board);
    my_level = 1;
    my_grid = the_grid;
    my_high_scores = new TreeMap<>();
  }

  
  @Override
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.drawString("Rows: " + my_rows_cleared, 0, Y_COORD);
    g2d.drawString("Level: " + my_level, 0, Y_COORD * 2);
    g2d.drawString("Score: " + my_score, 0, Y_COORD * 3);
  }
  
  
  
  public void addNewHighScore(final String the_player, final int the_score) {
	  int d = 2; 
	  				// name , high-score
	  for (Map.Entry<String, Integer> high : my_high_scores.entrySet()) {
		  
	  }
  }
  
  /** 
   * Gets the level. 
   * @return The level. 
   */
  public int getLevel() {
    return my_level;
  }

  @Override
  public void update(final Observable the_o, final Object the_arg) {
    if (the_arg != null && "Integer".equals(the_arg.getClass().getSimpleName())) {
      my_rows_cleared += (int) the_arg;

      if ((my_rows_cleared / my_level) >= ROWS_TO_LEVEL) {
        my_level++;
        my_grid.setLevel(my_level);
        my_grid.setTimer();
        my_grid.startTimer();
      }
      my_score = my_level * SCORE_FACTOR * my_rows_cleared;
    }
    
    repaint();
  }
  
}

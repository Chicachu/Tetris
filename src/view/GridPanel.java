/*
 * GridPanel.java
 * TCSS 305 - Spring 2013
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.Timer;

import model.Block;
import model.Board;

/** 
 * A visual representation of the Tetris game board.
 * @author Erica Putsche
 * @version 06/07/2013
 */
@SuppressWarnings("serial")
public class GridPanel extends AbstractPanel {
  /** The speed of each "tick" in tetris. */
  private static final int DEFAULT_SPEED = 750;
  
  /** Minimum speed. */
  private static final int MINIMUM_SPEED = 200;

  /** Number of rows on the board. */
  private static final int NUM_OF_ROWS = 20;
  
  /** Number of columns on the board. */
  private static final int NUM_OF_COLUMNS = 10;
  
  /** Number of rows representing just the next piece. */
  private static final int NUM_OF_ROWS_FOR_NEXT_PIECE = 4; 
  
  /** Number of rows to loop through to draw shapes. */
  private static final int NUM_OF_ROWS_TO_DRAW = 23;
  
      /** The timer. */
  private Timer my_timer; 
  
  /** The level. */
  private int my_level;
  /** The action that represents the game timer. */
  private final Action my_game_timer;
  
  /** 
   * A constructor for the grid panel that represents the tetris board game. 
   * @param the_board The board.
   * @param the_game_timer The action listener that makes the board step. 
   */
  public GridPanel(final Board the_board, final Action the_game_timer) {
    super(the_board);
    my_level = 1;
    my_game_timer = the_game_timer;
    setTimer();
  }
  
  /** 
   * Draws the tetris board game.
   * @param the_graphics The graphics.
   */
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    //drawGrid(g2d);
    drawBoardState(g2d);
  }
  
  /** 
   * Draws the pieces on the board game. 
   * @param the_graphics The graphics. 
   */
  private void drawBoardState(final Graphics2D the_graphics) {
    drawPieceInProgress(the_graphics);
    drawFrozenPieces(the_graphics);
  }
  
  /**
   * Draws the frozen blocks on the board game. 
   * @param the_graphics The graphics.
   */
  private void drawFrozenPieces(final Graphics2D the_graphics) {
    final List<Block[]> frozen_blocks = getBoard().getFrozenBlocks();
    for (int i = 0; i < frozen_blocks.size(); i++) {
      for (int j = 0; j < frozen_blocks.get(i).length; j++) {
        if (frozen_blocks.get(i)[j] != Block.EMPTY && frozen_blocks.get(i)[j] != null) {
          final int x_coord = j * getSquareWidth();
          final int y_coord = (NUM_OF_ROWS - (i + 1)) * getSquareHeight();
          the_graphics.setColor(Color.GREEN);
          the_graphics.fillRect(x_coord, y_coord, getSquareWidth(), getSquareHeight());
        }
      }
    }
  }
  
  /** 
   * Draws the piece in progress to the game board. 
   * @param the_graphics The graphics.
   */
  private void drawPieceInProgress(final Graphics2D the_graphics) {
    final String board_rep = getBoard().toString();
    final String[] lines = board_rep.split("\\r?\\n");
    
    for (int i = NUM_OF_ROWS_FOR_NEXT_PIECE; i <= NUM_OF_ROWS_TO_DRAW; i++) {
      final char[] row = lines[i].toCharArray();
      
      for (int j = 0; j < row.length; j++) {
        if (row[j] == '*') {
          final int x_coord = (j - 1) * getSquareWidth();
          final int y_coord = (i - NUM_OF_ROWS_FOR_NEXT_PIECE) * getSquareHeight();
          the_graphics.setColor(Color.GREEN);
          the_graphics.fillRect(x_coord, y_coord, getSquareWidth(), getSquareHeight());
        }
      }
    }
  }
  
  /** 
   * Draws the grid on the game board.
   * @param the_graphics The graphics. 
   */
  private void drawGrid(final Graphics2D the_graphics) {
    final double width_spacing = getSquareWidth(); 
    final double height_spacing = getSquareHeight();
    
    for (double i = width_spacing; i < getWidth(); i += width_spacing) {
      final Line2D.Double line = new Line2D.Double(i, 0, i, getHeight());
      the_graphics.draw(line);
    }
    for (double i = height_spacing; i < getHeight(); i += height_spacing) {
      final Line2D.Double line = new Line2D.Double(0, i, getWidth(), i);
      the_graphics.draw(line);
    }
  }
  
  /**
   * Gets 1/10 of the board's width to represent the width of a block on the panel.
   * @return Width of each square on the panel.
   */
  public int getSquareWidth() {
    return getWidth() / NUM_OF_COLUMNS;
  }
  
  /**
   * Gets 1/20 of the board's height to represent the width of a block on the panel.
   * @return Width of each square on the panel.
   */
  public int getSquareHeight() {
    return getHeight() / NUM_OF_ROWS;
  }
  
  /** 
   * Sets the level.
   * @param the_level The level.
   */
  public void setLevel(final int the_level) {
    my_level = the_level;
  }
  
  /** Sets up the timer. */
  public final void setTimer() {
    final int milliseconds_subtracted = (int) (my_level - 1) * 150;
    // no faster than .2 seconds
    if (milliseconds_subtracted <= DEFAULT_SPEED - MINIMUM_SPEED) { 
      my_timer = new Timer(DEFAULT_SPEED - milliseconds_subtracted, my_game_timer);
    } else {
      my_timer = new Timer(MINIMUM_SPEED, my_game_timer);
    }
    
    my_timer.setRepeats(true);
  }
  
  /** Starts the timer. */
  public void startTimer() {
    my_timer.start();
  }

  @Override
  public void update(final Observable the_arg, final Object the_arg1) {
    repaint();
  }
  
  /** 
   * Stops the timer.
   */
  public void stopTimer() {
    my_timer.stop();
  }
}

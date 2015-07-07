/* 
 * Controls.java
 * TCSS 305 - Assignment 6 Part A
 */

package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;

/** 
 * The controls used to play Tetris.
 * @author Erica Putsche
 * @version 05/31/2013
 */
public class Controls implements KeyListener {
  
  /** The default left control. */
  private static final int DEFAULT_LEFT = KeyEvent.VK_LEFT;
  
  /** The default right control. */
  private static final int DEFAULT_RIGHT = KeyEvent.VK_RIGHT;
  
  /** The default down control. */
  private static final int DEFAULT_DOWN = KeyEvent.VK_DOWN;
  
  /** The default rotate control. */
  private static final int DEFAULT_ROTATE = KeyEvent.VK_UP;
  
  /** The default hard drop control. */
  private static final int DEFAULT_HARD_DROP = KeyEvent.VK_SPACE;
  
  /** The board. */
  private Board my_board;
  
  /** The number representing the key event for the left control. */
  private int my_left; 
  
  /** The number representing the key event for the right control. */
  private int my_right;
  
  /** The number representing the key event for the down control. */
  private int my_down;
  
  /** The number representing the key event for the rotate control. */
  private int my_rotate;
  
  /** The number representing the key event for the hard drop control. */
  private int my_hard_drop;
  
  /**
   * A constructor for the controls.
   * @param the_board The game board.
   */
  public Controls(final Board the_board) {
    super();
    my_board = the_board;
    restoreDefaultControls();
  }
  
  /** 
   * Sets the board. 
   * @param the_board The board.
   */
  public void setBoard(final Board the_board) {
    my_board = the_board;
  }

  @Override
  public void keyPressed(final KeyEvent the_event) {
    final int keycode = the_event.getKeyCode();
    
    if (keycode == my_left) {
      my_board.moveLeft();
    } else if (keycode == my_right) {
      my_board.moveRight();
    } else if (keycode == my_down) {
      my_board.moveDown();
    } else if (keycode == my_rotate) {
      my_board.rotate();
    } else if (keycode == my_hard_drop) {
      my_board.hardDrop();
    }
  }

  @Override
  public void keyReleased(final KeyEvent the_event) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyTyped(final KeyEvent the_event) {
    // TODO Auto-generated method stub
  }
  
  /** 
   * Sets the key event for move left.
   * @param the_keyevent The key event.
   */
  public void setMoveLeft(final int the_keyevent) {
    my_left = the_keyevent;
  }
  
  /** 
   * Gets the key event associated with the move left control.
   * @return The key event associated with the move left control.
   */
  public int getMoveLeft() {
    return my_left;
  }

  /** 
   * Sets the key event for move right.
   * @param the_keyevent The key event.
   */
  public void setMoveRight(final int the_keyevent) {
    my_right = the_keyevent;
  }
  /** 
   * Gets the key event associated with the move right control.
   * @return The key event associated with the move right control.
   */
  public int getMoveRight() {
    return my_right;
  }
  
  /** 
   * Sets the key event for move down.
   * @param the_keyevent The key event.
   */
  public void setMoveDown(final int the_keyevent) {
    my_down = the_keyevent;
  }
  
  /** 
   * Gets the key event associated with the move down control.
   * @return The key event associated with the move down control.
   */
  public int getMoveDown() {
    return my_down;
  }
  
  /** 
   * Sets the key event for rotate.
   * @param the_keyevent The key event.
   */
  public void setRotate(final int the_keyevent) {
    my_rotate = the_keyevent;
  }
  
  /** 
   * Gets the key event associated with the rotate control.
   * @return The key event associated with the rotate control.
   */
  public int getRotate() {
    return my_rotate;
  }
  
  /** 
   * Sets the key event for hard drop.
   * @param the_keyevent The key event.
   */
  public void setHardDrop(final int the_keyevent) {
    my_hard_drop = the_keyevent;
  }
  
  /** 
   * Gets the key event associated with the hard drop control.
   * @return The key event associated with the hard drop control.
   */
  public int getHardDrop() {
    return my_hard_drop;
  }
  
  /**
   * Restores the default controls.
   */
  public final void restoreDefaultControls() {
    my_left = DEFAULT_LEFT;
    my_right = DEFAULT_RIGHT; 
    my_down = DEFAULT_DOWN;
    my_rotate = DEFAULT_ROTATE; 
    my_hard_drop = DEFAULT_HARD_DROP;
  }
  
  /**
   * Allows the controls in the control class to be modified by the user.
   * @author Erica Putsche
   * @version 05/31/2013
   */
  @SuppressWarnings("serial")
  public class EditControls extends JDialog {
    
    /** The controls. */
    private final Controls my_controls;
    
    /** The control to be modified. */
    private final String my_control;
    
    /** The panel. */
    private final JPanel my_panel;
    
    /**
     * A constructor for this EditControls dialog box.
     * @param the_frame The parent control.
     * @param the_control The control to be changed: LEFT, RIGHT, DOWN, ROTATE, or HARD DROP
     * @param the_controls The controls.
     */
    public EditControls(final JFrame the_frame, final String the_control, 
                        final Controls the_controls) {
      super(the_frame);
      my_panel = new JPanel();
      getContentPane().add(my_panel);
      my_control = the_control;
      my_panel.add(new JLabel("Press any key to change the control for the " + my_control 
                              + " control."));
      my_controls = the_controls;
      this.addKeyListener(new EditControlAction());
      pack();
      setLocationRelativeTo(the_frame);
    }
    
    /**
     * Makes this dialog box visible.
     */
    public void showBox() {
      this.setVisible(true);
    }
    
    /**
     * Listens for key presses to set the controls in the Controls class.
     * @author Erica Putsche
     * @version 05/31/2013
     */
    public class EditControlAction implements KeyListener {
      @Override
      public void keyPressed(final KeyEvent the_event) {
        switch (my_control) {
          case "Left":
            my_controls.setMoveLeft(the_event.getKeyCode());
            break;
          case "Right":
            my_controls.setMoveRight(the_event.getKeyCode());
            break;
          case "Down":
            my_controls.setMoveDown(the_event.getKeyCode());
            break;
          case "Rotate":
            my_controls.setRotate(the_event.getKeyCode());
            break;
          case "Hard Drop":
            my_controls.setHardDrop(the_event.getKeyCode());
            break;
          default: 
            break;
        }
        dispose();     
      }

      @Override
      public void keyReleased(final KeyEvent the_e) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void keyTyped(final KeyEvent the_e) {
        // TODO Auto-generated method stub
        
      }
    }
  }
}

/*
 * TetrisGUI.java
 * TCSS 305 - Assignment 6 Part A
 */

package view;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Board;

/**
 * Represents a game of tetris, displaying the blocks, the piece in motion, the next piece,  
 * and the statistics of the current game in progress. 
 * @author Erica Putsche
 * @version 05/31/2013
 */
public class TetrisGUI implements Observer {
  /** The Dimension of the screen. */
  private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
  
  /** The width of the screen. */ 
  private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
  
  /** The height of the screen. */ 
  private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
  
  /** A constant for the string "Exit". */
  private static final String EXIT_STRING = "Exit";
  
  /** A constant for the string "Small". */
  private static final String SMALL_STRING = "Small"; 
  
  /** A constant for the string "Medium". */
  private static final String MEDIUM_STRING = "Medium"; 
  
  /** A constant for the string "Large". */
  private static final String LARGE_STRING = "Large";
  
  /** A constant for the string "Controls". */
  private static final String CONTROL_STRING = "Controls";
  
  /** A constant for the string "Edit Controls". */
  private static final String EDIT_STRING = "Edit Controls";
  
  /** A constant for the string "Save". */
  private static final String SAVE_STRING = "Save";
  
  /** A constant for the string "Open". */
  private static final String OPEN_STRING = "Open";
  
  /** A constant for the string "End Game". */
  private static final String END_GAME_STRING = "End Game";
  
  /** A constant for the string "New Game". */
  private static final String NEW_GAME_STRING = "New Game";
  
  /** A constant for the string "Restore Default Controls". */
  private static final String RESTORE_STRING = "Restore Default Controls";
  
  /** The size of a small window. */
  private static final Dimension SMALL_WINDOW_SIZE = new Dimension(367, 492);
  
  /** The size of a medium window. */
  private static final Dimension MEDIUM_WINDOW_SIZE = new Dimension(442, 592);
  
  /** The size of a large window. */ 
  private static final Dimension LARGE_WINDOW_SIZE = new Dimension(529, 692);
  
  /** An array of strings for the controls used in Tetris. */
  private static final Object[] EDIT_OPTIONS = {"Left", "Right", "Down", "Rotate", 
                                                "Hard Drop"};
  
  /** A value for the insets in the grid bag layout. */
  private static final int INSET = 20;
  
  /** A constant for a relative grid width of 3. */
  private static final int GRID_WIDTH_3 = 3;
  
  /** A constant for a relative grid width of 4. */
  private static final int GRID_WIDTH_4 = 4;
  
  /** A constant for a relative grid width of 5. */
  private static final int GRID_WIDTH_5 = 5;
  
  /** A constant for a small weight. */
  private static final double SMALL_WEIGHT = .25; 
  
  /** A constant for a large weight. */
  private static final double LARGE_WEIGHT = .25;
  
  
  /** The JFrame. */
  private JFrame my_jframe;
  
  /** The game board. */
  private Board my_board;
  
  /** The grid that represents the game board. */
  private GridPanel my_grid;
  
  /** The actions used for the menu items. */
  private Map<String, Action> my_actions;
  
  /** The panel to display the next piece. */
  private NextPiecePanel my_nextpiece;
  
  /** The panel to display the statistics. */
  private StatisticsPanel my_statistics;
  
  /** The controls. */
  private Controls my_controls;
  
  /** The restore option menu item. */
  private JMenuItem my_restore_option;
  
  /** The JFileChooser. */
  private JFileChooser my_chooser;
  
  /** The timer to control the game board. */
  private Action my_game_timer;
  
  /** Responsible for creating and displaying the GUI for Tetris. */
  public void start() {
    initializePanels();
    addPanels(); 
    setupActions();
    createMenuBar();
    
    my_jframe.setVisible(true);
    my_jframe.setPreferredSize(SMALL_WINDOW_SIZE);
    my_jframe.setResizable(false);
    my_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    my_jframe.pack();
    setLocation();
  }
  
  /** Creates a menu bar. */
  private void createMenuBar() {
    final JMenuBar menu_bar = new JMenuBar();
    
    menu_bar.add(createFileMenu());
    menu_bar.add(createOptionsMenu());
    menu_bar.add(createHelpMenu());
    
    my_jframe.setJMenuBar(menu_bar);
  }
  
  /** 
   * Creates a file menu. 
   * @return The file menu.
   */
  private JMenu createFileMenu() {
    final JMenu file_menu = new JMenu("File");
    file_menu.setMnemonic(KeyEvent.VK_F);
    file_menu.add(my_actions.get(END_GAME_STRING));
    file_menu.add(my_actions.get(NEW_GAME_STRING));
    file_menu.addSeparator();
    file_menu.add(my_actions.get(SAVE_STRING));
    file_menu.add(my_actions.get(OPEN_STRING));
    file_menu.addSeparator();
    file_menu.add(my_actions.get(EXIT_STRING));
    
    return file_menu;
  }
  
  /** 
   * Creates an options menu. 
   * @return The options menu.
   */
  private JMenu createOptionsMenu() {
    final JMenu options_menu = new JMenu("Options");
    options_menu.setMnemonic(KeyEvent.VK_O);
    options_menu.add(createResizeMenu());
    options_menu.add(my_actions.get(EDIT_STRING));
    
    my_restore_option = new JMenuItem(my_actions.get(RESTORE_STRING));
    my_restore_option.setEnabled(false);
    options_menu.add(my_restore_option);
    
    return options_menu;
  }
  
  /** 
   * Creates a resize menu. 
   * @return The resize menu.
   */
  private JMenu createResizeMenu() {
    final JMenu resize_menu = new JMenu("Resize Window");
    final ButtonGroup group = new ButtonGroup();
    
    final JRadioButtonMenuItem size_small = new JRadioButtonMenuItem(SMALL_STRING);
    size_small.addActionListener(my_actions.get(SMALL_STRING));
    final JRadioButtonMenuItem size_medium = new JRadioButtonMenuItem(MEDIUM_STRING);
    size_medium.addActionListener(my_actions.get(MEDIUM_STRING));
    final JRadioButtonMenuItem size_large = new JRadioButtonMenuItem(LARGE_STRING);
    size_large.addActionListener(my_actions.get(LARGE_STRING));
    
    size_small.setSelected(true);
    
    group.add(size_small);
    group.add(size_medium);
    group.add(size_large);
    
    resize_menu.add(size_small);
    resize_menu.add(size_medium);
    resize_menu.add(size_large);
    
    return resize_menu;
  }
  
  /**
   * Creates the help menu.
   * @return The help menu.
   */
  private JMenu createHelpMenu() {
    final JMenu help_menu = new JMenu("Help"); 
    help_menu.add(my_actions.get(CONTROL_STRING));
    
    return help_menu;
  }
  
  /** 
   * Sets up the actions listeners for all of the menu items.
   */
  @SuppressWarnings("serial")
  private void setupActions() {
    my_actions = new HashMap<>();
    // save
    final Action save_action = new AbstractAction(SAVE_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_chooser.showSaveDialog(my_jframe);
        if (result == JFileChooser.APPROVE_OPTION)  {
          final File f = my_chooser.getSelectedFile(); 
          try {
            final FileOutputStream file_out_stream = new FileOutputStream(f);
            final ObjectOutputStream out = new ObjectOutputStream(file_out_stream);
            out.writeObject(my_board);
            out.close();
          } catch (final FileNotFoundException e) {
            
          } catch (final IOException the_other_e) {
            
          }
        }
      }
    };
    
    // load
    final Action open_action = new AbstractAction(OPEN_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_chooser.showOpenDialog(my_jframe);   
        if (result == JFileChooser.APPROVE_OPTION) {
          final File f = my_chooser.getSelectedFile();
          try {
            final FileInputStream file_input = new FileInputStream(f);
            final ObjectInputStream in = new ObjectInputStream(file_input);
            my_board = (Board) in.readObject();
            my_grid.setBoard(my_board);
            my_nextpiece.setBoard(my_board);
            my_statistics.setBoard(my_board);
            addObservers();
            endGame();
            my_controls = new Controls(my_board);
            my_jframe.addKeyListener(my_controls);
            my_grid.setTimer();
            my_grid.startTimer();
            in.close();
          } catch (final FileNotFoundException e) {
          } catch (final IOException the_other_e) {
            System.out.println(the_other_e.getMessage());
          } catch (final ClassNotFoundException the_other_other_e) {
            
          } 
        }
      }
    };
    // new game
    
    // end game
    final Action end_game_action = new AbstractAction(END_GAME_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        endGame();
      }
    };
    
    final Action new_game_action = new AbstractAction(NEW_GAME_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        startNewGame();
      }
    };
    // exit
    final Action exit_action = new AbstractAction(EXIT_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        my_grid.stopTimer();
        my_jframe.dispose();
      }
    };
    // toggle sound
    // edit controls
    final Action edit_controls_action = new AbstractAction(EDIT_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        final String s = (String) JOptionPane.showInputDialog(my_jframe, 
                                                              "Choose a control to edit: ", 
                                               EDIT_STRING, JOptionPane.PLAIN_MESSAGE,
                                               null, EDIT_OPTIONS, EDIT_OPTIONS[0]);
        if (s != null) { // if user didn't cancel
          final Controls.EditControls edit_controls = 
              my_controls.new EditControls(my_jframe, s, my_controls);
          my_restore_option.setEnabled(true);
          edit_controls.showBox();
        }
      }
    };
    
    final Action restore_action = new AbstractAction(RESTORE_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        my_controls.restoreDefaultControls();
        my_restore_option.setEnabled(false);
      }
    };
    
    // resize window
    final Action resize_action_small = new AbstractAction(SMALL_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        my_jframe.setSize(SMALL_WINDOW_SIZE);
        setLocation();
      }
    };
    final Action resize_action_medium = new AbstractAction(MEDIUM_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        my_jframe.setSize(MEDIUM_WINDOW_SIZE);
        setLocation();
      }
    };
    final Action resize_action_large = new AbstractAction(LARGE_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        my_jframe.setSize(LARGE_WINDOW_SIZE);
        setLocation();
      }
    };
    
    // display controls
    final Action display_controls_action = new AbstractAction(CONTROL_STRING) {
      public void actionPerformed(final ActionEvent the_event) {
        JOptionPane.showMessageDialog(my_jframe, controlString());
      }
    };
    
    my_actions.put(EXIT_STRING,  exit_action);
    my_actions.put(SMALL_STRING, resize_action_small);
    my_actions.put(MEDIUM_STRING, resize_action_medium);
    my_actions.put(LARGE_STRING, resize_action_large);
    my_actions.put(CONTROL_STRING, display_controls_action);
    my_actions.put(EDIT_STRING, edit_controls_action);
    my_actions.put(RESTORE_STRING, restore_action);
    my_actions.put(END_GAME_STRING, end_game_action);
    my_actions.put(NEW_GAME_STRING, new_game_action);
    my_actions.put(SAVE_STRING, save_action);
    my_actions.put(OPEN_STRING, open_action);
  }
  
  
  /**
   * Sets the center of the frame to the center of the screen.
   */
  private void setLocation() {
    my_jframe.setLocation(SCREEN_WIDTH / 2 - my_jframe.getWidth() / 2,
                SCREEN_HEIGHT / 2 - my_jframe.getHeight() / 2);
  }
  
  /**
   * Builds a string representing the control instructions.
   * @return A string representing the control instructions.
   */
  private String controlString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Controls: ");
    sb.append("\n\tLeft: " + KeyEvent.getKeyText(my_controls.getMoveLeft()));
    sb.append("\n\tRight: " + KeyEvent.getKeyText(my_controls.getMoveRight()));
    sb.append("\n\tDown: " + KeyEvent.getKeyText(my_controls.getMoveDown()));
    sb.append("\n\tRotate: " + KeyEvent.getKeyText(my_controls.getRotate()));
    sb.append("\n\tHard Drop: " + KeyEvent.getKeyText(my_controls.getHardDrop()));
    
    return sb.toString();
  }
  
  /**
   * Initializes the frame and it's components.
   */
  @SuppressWarnings("serial")
  private void initializePanels() {
    my_jframe = new JFrame();
    my_jframe.setLayout(new GridBagLayout());
    my_game_timer = new AbstractAction() {
      public void actionPerformed(final ActionEvent the_event) {
        my_board.step();
      }
    };

    my_board = new Board();
    my_grid = new GridPanel(my_board, my_game_timer);
    my_grid.startTimer();
    
    my_nextpiece = new NextPiecePanel(my_board, my_grid);
    my_statistics = new StatisticsPanel(my_board, my_grid);
    my_controls = new Controls(my_board);
    
    my_chooser = new JFileChooser();
    final FileNameExtensionFilter filter = new FileNameExtensionFilter("Tetris File", "dat");
    my_chooser.setFileFilter(filter);
    
    addObservers();
    
    my_jframe.addKeyListener(my_controls);
    my_jframe.addKeyListener(new Pause());
  }
  
  /** 
   * Adds observers to the board.
   */
  private void addObservers() {
    my_board.addObserver(my_grid);
    my_board.addObserver(my_nextpiece);
    my_board.addObserver(my_statistics);
    my_board.addObserver(this);
  }
  
  /**
   * Adds the game grid, next piece panel, and statistics panel to the frame.
   */
  private void addPanels() {
    final GridBagConstraints constraint = new GridBagConstraints();
    constraint.gridx = 0;
    constraint.gridy = 0;
    constraint.gridwidth = GRID_WIDTH_3;
    constraint.gridheight = GRID_WIDTH_5;
    constraint.weightx = 1; 
    constraint.weighty = 1;
    constraint.insets = new Insets(INSET, INSET, INSET, INSET);
    constraint.fill = GridBagConstraints.BOTH;
    my_jframe.add(my_grid, constraint);
    constraint.gridx = GRID_WIDTH_4;
    constraint.gridwidth = 1; 
    constraint.gridheight = 1; 
    constraint.insets = new Insets(INSET, INSET * 2, INSET, INSET);
    constraint.weightx = LARGE_WEIGHT;
    constraint.weighty = SMALL_WEIGHT;
    my_jframe.add(my_nextpiece, constraint);
    constraint.gridy = 1;
    my_jframe.add(my_statistics, constraint);
  }


  @Override
  public void update(final Observable the_arg0, final Object the_arg1) {
    if (my_board.gameIsOver()) {
      gameOver();
    }
  }
  
  /** Marks the end of a game. */
  private void gameOver() {
    endGame();
    final int result = JOptionPane.showConfirmDialog(my_jframe, "You have lost. " 
        + "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      startNewGame();
    } else {
      JOptionPane.showMessageDialog(my_jframe,  "Thank you for playing!");
      my_jframe.dispose();
    }
  }
  
  /** 
   * Ends the current game.
   */
  private void endGame() {
    my_grid.stopTimer();
    my_jframe.removeKeyListener(my_controls);
  }
  
  /** 
   * Starts a new game.
   */
  private void startNewGame() {
    my_board = new Board();
    my_grid.setBoard(my_board);
    my_nextpiece.setBoard(my_board);
    my_statistics.setBoard(my_board);
    addObservers();
    endGame();
    my_controls = new Controls(my_board);
    my_jframe.addKeyListener(my_controls);
    
    my_grid.setTimer();
    my_grid.startTimer();
  }
  
  /** Allows Tetris to pause. */
  public class Pause implements KeyListener {
    /** Is the game paused? */
    private boolean my_is_paused;

    @Override
    public void keyPressed(final KeyEvent the_arg0) {
      
    }

    @Override
    public void keyReleased(final KeyEvent the_arg0) {
      
    }

    @Override
    public void keyTyped(final KeyEvent the_event) {
      final char keycode = the_event.getKeyChar();
      
      if (keycode == 'p') { 
        if (!my_is_paused) {
          my_jframe.removeKeyListener(my_controls);
          my_grid.stopTimer();
        } else {
          my_jframe.addKeyListener(my_controls);
          my_grid.startTimer();
        }
        my_is_paused = !my_is_paused;
      }
    }

  }
  
}

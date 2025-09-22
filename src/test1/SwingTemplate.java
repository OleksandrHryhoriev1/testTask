package test1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SwingTemplate extends JFrame implements KeyListener {

   private static final int WINDOW_WIDTH = 600;
   private static final int WINDOW_HEIGHT = 600;
   private boolean darkMode = false;

   private final MyContainer container;

   public SwingTemplate() {
      super("Interactive Grid");

      container = new MyContainer(WINDOW_WIDTH, WINDOW_HEIGHT, this, new LightTheme());
      add(container);

      JMenuBar menuBar = new JMenuBar();
      JMenu themeMenu = new JMenu("Theme");
      JMenuItem switchTheme = new JMenuItem("Light/Dark");
      switchTheme.addActionListener(e -> toggleTheme());
      themeMenu.add(switchTheme);
      menuBar.add(themeMenu);
      setJMenuBar(menuBar);

      JMenu gridMenu = new JMenu("Grid Size");

      JMenuItem size4x4 = new JMenuItem("4 x 4");
      size4x4.addActionListener(e -> container.resizeGrid(4, 4));

      JMenuItem size5x5 = new JMenuItem("5 x 5");
      size5x5.addActionListener(e -> container.resizeGrid(5, 5));

      JMenuItem size6x6 = new JMenuItem("6 x 6");
      size6x6.addActionListener(e -> container.resizeGrid(6, 6));

      gridMenu.add(size4x4);
      gridMenu.add(size5x5);
      gridMenu.add(size6x6);
      menuBar.add(gridMenu);

      addKeyListener(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
      requestFocusInWindow();
   }

   @Override
   public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
         case KeyEvent.VK_LEFT -> container.moveSelection(MyContainer.Direction.LEFT);
         case KeyEvent.VK_RIGHT -> container.moveSelection(MyContainer.Direction.RIGHT);
         case KeyEvent.VK_UP -> container.moveSelection(MyContainer.Direction.UP);
         case KeyEvent.VK_DOWN -> container.moveSelection(MyContainer.Direction.DOWN);
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   private void toggleTheme() {
      darkMode = !darkMode;
      container.setTheme(darkMode ? new DarkTheme() : new LightTheme());
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(SwingTemplate::new);
   }
}

package test1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class MyContainer extends JPanel {

  private int rows = 4;
  private int cols = 4;
  private final SwingTemplate parentFrame;
  private Theme theme;

  private String[] model;
  private JLabel[] cells;

  private int highlightIndex = 6;
  private int draggingIndex = -1;

  private static final int FONT_SIZE = 30;

  public MyContainer(int width, int height, SwingTemplate parentFrame, Theme theme) {
    this.parentFrame = parentFrame;
    this.theme = theme;
    setPreferredSize(new Dimension(width, height));
    setLayout(new GridLayout(rows, cols));

    initModel();
    initCells();
    applyTheme();
    updateUIState();
  }

  private void initModel() {
    model = new String[rows * cols];
    for (int i = 0; i < model.length; i++)
      model[i] = String.valueOf(i);
  }

  private void initCells() {
    removeAll();
    cells = new JLabel[rows * cols];
    for (int i = 0; i < cells.length; i++)
      add(cells[i] = createCell(i));
  }

  private JLabel createCell(int index) {
    JLabel cell = new JLabel(model[index], SwingConstants.CENTER);
    cell.setFont(new Font(getFont().getName(), Font.PLAIN, FONT_SIZE));

    cell.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        highlightIndex = index;
        updateUIState();
      }

      @Override
      public void mousePressed(MouseEvent e) {
        draggingIndex = index;
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (draggingIndex == -1)
          return;

        Component comp = getComponentAt(
            SwingUtilities.convertPoint(cell, e.getPoint(), MyContainer.this));
        if (comp instanceof JLabel target && target != cell) {
          int targetIndex = indexOf(target);
          swapModel(draggingIndex, targetIndex);
          syncViewFromModel();
          highlightIndex = targetIndex;
        }
        draggingIndex = -1;
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        removeCellWithDownShift(index);
      }
    });

    cell.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        Component c = getComponentAt(SwingUtilities.convertPoint(cell, e.getPoint(), MyContainer.this));
        if (c instanceof JLabel targetCell && targetCell != cell) {
          targetCell.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
      }
    });

    return cell;
  }

  private int indexOf(Component c) {
    for (int i = 0; i < cells.length; i++)
      if (cells[i] == c)
        return i;
    return -1;
  }

  private void swapModel(int a, int b) {
    String tmp = model[a];
    model[a] = model[b];
    model[b] = tmp;
  }

  private void syncViewFromModel() {
    for (int i = 0; i < model.length; i++)
      cells[i].setText(model[i]);
    applyTheme();
  }

  public void resizeGrid(int newRows, int newCols) {
    String[] old = model;
    int oldCount = old.length;

    rows = newRows;
    cols = newCols;
    setLayout(new GridLayout(rows, cols));

    model = new String[rows * cols];
    for (int i = 0; i < model.length; i++)
      model[i] = i < oldCount ? old[i] : String.valueOf(i);

    initCells();
    highlightIndex = Math.min(highlightIndex, model.length - 1);
    revalidate();
    repaint();
  }

  private void applyTheme() {
    if (theme == null)
      return;

    setBackground(theme.getBackgroundColor());
    Border normal = BorderFactory.createLineBorder(theme.getBorderColor());
    Border highlight = BorderFactory.createLineBorder(theme.getHighlightColor(), 3);
    Color textColor = theme.getTextColor();

    if (cells == null)
      return;
    for (int i = 0; i < cells.length; i++) {
      cells[i].setForeground(textColor);
      cells[i].setBorder(i == highlightIndex ? highlight : normal);
    }
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
    applyTheme();
    repaint();
  }

  private void updateUIState() {
    applyTheme();
    parentFrame.setTitle("Selected index: " + highlightIndex);
  }

  public void moveSelection(Direction dir) {
    int row = highlightIndex / cols;
    int col = highlightIndex % cols;

    switch (dir) {
      case LEFT -> col = Math.max(0, col - 1);
      case RIGHT -> col = Math.min(cols - 1, col + 1);
      case UP -> row = Math.max(0, row - 1);
      case DOWN -> row = Math.min(rows - 1, row + 1);
    }

    highlightIndex = row * cols + col;
    updateUIState();
  }

  private void removeCellWithDownShift(int index) {
    int col = index % cols;
    int row = index / cols;
    for (int r = row; r < rows - 1; r++)
      model[r * cols + col] = model[(r + 1) * cols + col];
    model[(rows - 1) * cols + col] = "";
    syncViewFromModel();
  }

  public enum Direction {
    LEFT, RIGHT, UP, DOWN
  }
}

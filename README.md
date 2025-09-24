# Interactive Grid Swing Application

This is a simple Java Swing application that demonstrates an
**interactive, resizable grid** with theme support, drag-and-drop
functionality, and keyboard navigation. The project is structured using
modular classes for clean design and maintainability.

## **Features**

-   **Resizable Grid:**
    > The grid can be resized dynamically via the menu (4x4, 5x5, 6x6).

-   **Keyboard Navigation:**
    > Use arrow keys to move the highlighted selection across the grid.

-   **Mouse Interaction:**

    -   **Hover:** Highlights the cell under the mouse cursor.

    -   **Click:** Removes a cell and shifts the remaining cells down.

    -   **Drag-and-Drop:** Swap values between cells by dragging and
        > releasing.

-   **Themes:**
    > Supports Light and Dark themes. Themes affect background color,
    > borders, highlight color, and text color. Can be switched via the
    > menu.

-   **Minimalist, Maintainable Code:**
    > Uses a clear separation between model (data) and view (JLabel
    > cells).

## **Project Structure**
```
test1/
├── MyContainer.java
├── SwingTemplate.java
├── Theme.java
├── LightTheme.java
└── DarkTheme.java
```


-   **MyContainer.java**

    -   Stores grid data in model\[\] and view in cells\[\].

    -   Handles mouse events for drag-drop, hover, and click removal.

    -   Updates UI dynamically when grid is resized or theme is changed.

-   **SwingTemplate.java**

    -   Main window (JFrame) with menus for theme switching and grid
        > resizing.

    -   Listens to keyboard events and delegates selection movement to
        > MyContainer.

-   **Theme.java / LightTheme.java / DarkTheme.java**

    -   Defines the interface and implementations for different visual
        > themes.

## **Usage**

1.  **Run the application:**

> javac -d out src\\test1\\\*.java
>
> java -cp out test1.SwingTemplate

2.  **Navigate with keyboard:** Arrow keys to move selection.

3.  **Use mouse:** Hover to highlight, click to remove, drag to swap
    > cells.

4.  **Switch theme:** Menu → Theme → Light/Dark.

5.  **Resize grid:** Menu → Grid Size → 4x4 / 5x5 / 6x6.

## **Key Design Decisions**

-   **Model-View Separation:**
    > The grid data (model\[\]) is separate from the visual
    > representation (JLabel\[\]). This allows easy updates and clean
    > synchronization.

-   **Minimal Drag-and-Drop:**
    > Drag highlights the target cell temporarily using a red border;
    > releasing swaps values.

-   **Dynamic Themes:**
    > Theme changes propagate to all cells immediately without
    > recreating components.

-   **Keyboard Navigation:**
    > Arrow keys change highlightIndex efficiently, updating only the
    > necessary visual states.

## **Dependencies**

-   Java 8+

-   No external libraries; pure Swing implementation.

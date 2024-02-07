/**
 * COMP 6481 (Winter 2024) Assignment #1
 * Due Date: Sunday, February 11, 2024 by 11:59PM
 * 
 * © Oluwamosope Adeyemi. ID: 40293741
 * © Jerome Kithinji. ID: 
 * 
 * Written by: 
 * Jerome Kithinji. ID:
 * Oluwamosope Adeyemi. ID: 40293741
 */

package battleship;

/**
 * The Cell class represents a single cell in the battleship game grid.
 * Each cell has a type, owner, coordinates, and a boolean indicating whether it has been called.
 */
public class Cell {
    private char type; // Type of the cell ('s' for human's ship, 'S' for computer's ship, 'g' human's grenade, 'G' computer's grenade)
    private String owner; // The owner of the cell (Human or Computer)
    private boolean hasBeenCalled; // Boolean indicating whether this cell has been called or not.
    private int[] coordinate = new int[2]; // Position of the cell in the grid (row, column)

    /**
     * Constructs a Cell object with the specified type, owner, row, and column.
     *
     * @param type    The type of element in the cell ('s' for human's ship, 'S' for computer's ship, 'g' human's grenade, 'G' computer's grenade)
     * @param owner   The owner of the cell (Human or Computer)
     * @param row     The row position of the cell in the grid
     * @param column  The column position of the cell in the grid
     */
    public Cell(char type, String owner, int row, int column) {
        this.type = type;
        this.owner = owner;
        this.coordinate[0] = row;
        this.coordinate[1] = column;
        this.hasBeenCalled = false;
    }

    /**
     * Returns the type of the cell.
     *
     * @return The type of element in the cell ('s' for human's ship, 'S' for computer's ship, 'g' human's grenade, 'G' computer's grenade)
     */
    public char getType() {
        return type;
    }

    /**
     * Sets the type of the cell.
     *
     * @param type The type of element in the cell
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Retrieves the owner of the cell.
     *
     * @return The owner of the cell. Either Human or Computer.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the cell.
     *
     * @param owner The owner of the cell. Either Human or Computer.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Retrieves the boolean indicating whether the cell has been called.
     *
     * @return true if the cell has been called, false otherwise.
     */
    public boolean getHasBeenCalled() {
        return hasBeenCalled;
    }

    /**
     * Sets the boolean indicating whether the cell has been called.
     *
     * @param hasBeenCalled The new value for the hasBeenCalled flag.
     */
    public void setHasBeenCalled(boolean hasBeenCalled) {
        this.hasBeenCalled = hasBeenCalled;
    }

    /**
     * Sets the coordinates (row and column index) of the cell on the battleship board/grid.
     *
     * @param coordinate An array containing the new row and column coordinates
     */
    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }
}

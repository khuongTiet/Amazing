import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;

public class MazeCell extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;
	private boolean visited = false;
	private boolean path = false;
	private boolean checked = false;
	private boolean NorthWall = true;
	private boolean SouthWall = true;
	private boolean EastWall = true;
	private boolean WestWall = true;
	
	public MazeCell() {
		
	}
	
	public MazeCell(int row, int column, boolean walled) {
		this.row = row;
		this.column = column;
		this.visited = false;
		this.path = false;
		this.checked = false;
		if (walled) {
			this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
		}
		//JLabel colrow = new JLabel(String.format("Row: %d, Col: %d", this.row, this.column));
		//this.add(colrow);
	}
	
	public MazeCell(int row, int column, boolean V, boolean N, boolean S, boolean E, boolean W) {
		this.row = row;
		this.column = column;
		this.visited = V;
		this.NorthWall = N;
		this.SouthWall = S;
		this.EastWall = E;
		this.WestWall = W;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public boolean isVisited() {
		return this.visited;
	}
	
	public void visitCell() {
		this.visited = true;
	}
	
	public boolean visitable() {
		return this.NorthWall || this.SouthWall || this.EastWall || this.WestWall;
	}
	
	public boolean checkWall(String wall) {
		switch(wall) {
			case "N":
				return this.NorthWall;
			case "S":
				return this.SouthWall;
			case "E":
				return this.EastWall;
			case "W":
				return this.WestWall;
			default:
				return true;
		}
	}
	
	public boolean removeWall(String wall) {
		switch(wall) {
			case "N":
				this.NorthWall = false;
				break;
			case "S":
				this.SouthWall = false;
				break;
			case "E":
				this.EastWall = false;
				break;
			case "W":
				this. WestWall = false;
				break;
			default:
				return false;
		}
		this.setBorder(updateCellWalls());
		this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
		return true;
	}
	
	public Border updateCellWalls() {
		int north = this.NorthWall ? 2 : 0;
		int south = this.SouthWall ? 2 : 0;
		int east = this.EastWall ? 2 : 0;
		int west = this.WestWall ? 2 : 0;
		return BorderFactory.createMatteBorder(north, west, south, east, Color.RED);
	}
	
	public String getNeighborDirection(MazeCell neighbor) {
		if (this.column > neighbor.getColumn()) {
			return "W";
		}
		if (this.column < neighbor.getColumn()) {
			return "E";
		}
		if (this.row < neighbor.getRow()) {
			return "S";
		}
		if (this.row > neighbor.getRow()) {
			return "N";
		}
		return "";
	}
	
	public void addPath() {
		this.path = true;
		this.checked = true;
		this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void removePath() {
		this.path = false;
		this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}
	
	public boolean hasPath() {
		return this.path == true;
	}
	
	public boolean isChecked() {
		return this.checked;
	}

	@Override
	public String toString() {
		return "MazeCell [row=" + row + ", column=" + column + ", visited=" + visited + ", NorthWall=" + NorthWall
				+ ", SouthWall=" + SouthWall + ", EastWall=" + EastWall + ", WestWall=" + WestWall + "]";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.path) {
			int width = (int) this.getSize().getWidth();
			int height = (int) this.getSize().getHeight();
			g.setColor(Color.BLUE);
			g.fillRect(width / 2 - 6, height / 2 - 6, 12, 12);
		}
	}
}

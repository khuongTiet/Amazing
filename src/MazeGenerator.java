import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MazeGenerator extends JPanel {
	private MazeCell[][] maze;
	private int size;
	private Stack<MazeCell> branchCells = new Stack<MazeCell>();
	private boolean slow = false;
	private Random rand = new Random();
	
	public MazeGenerator() {
		this.maze = new MazeCell[10][10];
	}
	
	public MazeGenerator(int size, boolean walled, boolean slow) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.maze = new MazeCell[size][size];
		this.size = size;
		this.slow = slow;
		this.setLayout(new GridLayout(size, size));
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.maze[i][j] = new MazeCell(i, j, walled);
				this.add(this.maze[i][j]);
			}
		}
	}
	
	public MazeCell[][] generateMaze() {
		int startRow = rand.nextInt(this.getMazeSize());
		int startCol = rand.nextInt(this.getMazeSize());
		MazeCell currentCell = this.maze[startRow][startCol];
		currentCell.visitCell();
		this.branchCells.push(this.maze[startRow][startCol]);
		while (!this.branchCells.isEmpty()) {
			currentCell = this.branchCells.peek();
			MazeCell currentNeighbor = selectNeighbor();
			if (currentNeighbor == currentCell) {
				this.branchCells.pop();
			}
			else {
				if (this.slow) {
					try {
						Thread.sleep(800 / this.size);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
				this.branchCells.push(currentNeighbor);
				currentNeighbor.visitCell();
				currentNeighbor.removeWall(currentNeighbor.getNeighborDirection(currentCell));
				currentCell.removeWall(currentCell.getNeighborDirection(currentNeighbor));
				this.repaint();
				currentCell = currentNeighbor;
			}
		}
		System.out.println("Returning generated maze");
		return this.maze;
	}
	
	public MazeCell selectNeighbor() {
		ArrayList<Integer> availableDirections = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
		MazeCell neighbor = this.branchCells.peek();
		int currentCol = neighbor.getColumn();
		int currentRow = neighbor.getRow();
		while (availableDirections.size() != 0 && neighbor == this.branchCells.peek()) {
			int direction = rand.nextInt(availableDirections.size());
			if (availableDirections.get(direction) == 0) {
				if (currentRow - 1 >= 0 && !this.maze[currentRow - 1][currentCol].isVisited()) {
					neighbor = this.maze[currentRow - 1][currentCol];
				}
				availableDirections.remove(direction);
			}
			else if (availableDirections.get(direction) == 1) {
				if (currentRow + 1 < this.getMazeSize() && !this.maze[currentRow + 1][currentCol].isVisited()) {
					neighbor = this.maze[currentRow + 1][currentCol];
				}
				availableDirections.remove(direction);
			}
			else if (availableDirections.get(direction) == 2) {
				if (currentCol - 1 >= 0 && !this.maze[currentRow][currentCol - 1].isVisited()) {
					neighbor = this.maze[currentRow][currentCol - 1];
				}
				availableDirections.remove(direction);
			}
			else {
				if (currentCol + 1 < this.getMazeSize() && !this.maze[currentRow][currentCol + 1].isVisited()) {
					neighbor = this.maze[currentRow][currentCol + 1];
				}
				availableDirections.remove(direction);
			}
		}
		return neighbor;
	}
	
	public MazeCell[][] getMaze() {
		return this.maze;
	}
	
	public int getMazeSize() {
		return this.size;
	}
	
	public void printMaze() {
		
	}
}

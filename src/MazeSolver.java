import java.util.Stack;

public class MazeSolver {
	private MazeCell[][] maze;
	private int mazeSize;
	private Stack<MazeCell> currentPath = new Stack<MazeCell>();
	
	public MazeSolver(MazeCell[][] maze, int size) {
		this.maze = maze;
		this.mazeSize = size;
	}
	
	public void solveMaze() {
		MazeCell currentCell = this.maze[0][0];
		MazeCell end = this.maze[this.mazeSize - 1][this.mazeSize - 1];
		MazeCell toVisit = cellToVisit(currentCell);
		currentCell.addPath();
		currentPath.push(currentCell);
		while (toVisit != end) {
			currentCell = currentPath.peek();
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			toVisit = cellToVisit(currentCell);
			if (toVisit == currentCell) {
				currentPath.pop();
			} else {
				currentPath.push(toVisit);
				toVisit.addPath();
			}
			if (toVisit == end) {
				System.out.println("END GAME");
				break;
			}
		}
	}
	
	public MazeCell cellToVisit(MazeCell from) {
		if (!from.checkWall("E") && !this.maze[from.getRow()][from.getColumn() + 1].isChecked()) {
			return this.maze[from.getRow()][from.getColumn() + 1];
		} else if (!from.checkWall("S") && !this.maze[from.getRow() + 1][from.getColumn()].isChecked()) {
			return this.maze[from.getRow() + 1][from.getColumn()];
		} else if (!from.checkWall("W") && !this.maze[from.getRow()][from.getColumn() - 1].isChecked()) {
			return this.maze[from.getRow()][from.getColumn() - 1];
		} else if (!from.checkWall("N") && !this.maze[from.getRow() - 1][from.getColumn()].isChecked()){
			return this.maze[from.getRow() - 1][from.getColumn()];
		}
		from.removePath();
		return from;
	}
	
	public boolean visitable(MazeCell from, MazeCell to) {
		return (from.checkWall("N") && to.checkWall("S") ||
				from.checkWall("S") && to.checkWall("N") ||
				from.checkWall("W") && to.checkWall("E") || 
				from.checkWall("E") && to.checkWall("W") );
	}
}

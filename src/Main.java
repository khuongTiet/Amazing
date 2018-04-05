import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		MazeGenerator maze = new MazeGenerator(20, true, true);
		JFrame frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mazeGUI = new JPanel();
		JPanel dataPanel = new JPanel();
		JButton startMaze = new JButton("START MAZE");
		startMaze.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		        maze.generateMaze();
		        frame.validate();
		    } 
		});
		mazeGUI.setLayout(new BoxLayout(mazeGUI, BoxLayout.X_AXIS));
		mazeGUI.add(maze);
		mazeGUI.add(dataPanel);
		frame.add(mazeGUI);
	    frame.setSize(800, 640);
	    frame.setVisible(true);
	    
	    try {
	    	Thread.sleep(1000);
	    } catch (InterruptedException ex){
	    	Thread.currentThread().interrupt();
	    }
	    MazeSolver solver = new MazeSolver(maze.getMaze(), 20);
	    JButton solveMaze = new JButton("SOLVE MAZE");
	    solveMaze.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		        solver.solveMaze();
		    } 
		});
	    dataPanel.add(startMaze);
	    dataPanel.add(solveMaze);
	}
}

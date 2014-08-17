// Travis Brundage
// 9/16/13
// CAP 5636
// 3x3 Puzzle AI

import java.io.*;
import java.util.*;

public class puzzle {
	// Puzzle's dimensions
	public static final int N = 3;
	public static final int MAX_DEPTH = 31;
	public static boolean SPEED;
	
	// Perform a best first search to solve puzzle
	public static void BestFirstSearch(State IS, State Goal) {
		// Keep track of what states have already been visited
		HashMap<State, Integer> closed = new HashMap<State, Integer>();
		// Keep track of what states to visit
		ArrayList<State> open = new ArrayList<State>();
		// Keep track of current state
		State current;
		// Holds a list of the children
		List<State> children;
		
		// Add the initial state
		open.add(IS);
		
		int i = 0;
		boolean added = false;
		
		// Go through all states until we find the solution
		while (!open.isEmpty()) {
			// Grab the next state
			current = open.remove(0);
			// Add it to closed
			closed.put(current, current.hval);
			
			// If it's our solution, we're done
			if (current.equals(Goal)) {
				printAll(current);
				System.out.println("Solution found");
				return;
			}
			
			// Otherwise, generate all the children and add them to the queue
			children = current.children(Goal);
			for (State c : children) {
				if (c.depth <= MAX_DEPTH) {	// All puzzles can be solved in 31 moves or less, so don't make more
					if (!(closed.containsKey(c))) { // Make sure it's not in closed
						if (!(open.contains(c))) { // Make sure it's not in open
							i = 0; added = false;
							while (i < open.size()) {
								if (c.hval < open.get(i).hval) { open.add(i, c); added = true; }
								if (added == true) { break; }
								i++;
							}
							if (added != true) { open.add(c); }
						}
					}
				}
			}
		}
		
		// If we went through all states, there is no solution
		System.out.println("No solution");
	}
	
	
	// Modification for speed
	// Perform a best first search to solve puzzle
	public static void SpeedBestFirstSearch(State IS, State Goal) {
		// Keep track of what states have already been visited
		HashMap<State, Integer> closed = new HashMap<State, Integer>();
		// Keep track of what states to visit
		ArrayList<State> open = new ArrayList<State>();
		// Keep track of current state
		State current;
		// Holds a list of the children
		List<State> children;
		
		// Add the initial state
		open.add(IS);
		
		int i = 0;
		boolean added = false;
		
		// Go through all states until we find the solution
		while (!open.isEmpty()) {
			// Grab the next state
			current = open.remove(0);
			// Add it to closed
			closed.put(current, current.hval);
			
			// If it's our solution, we're done
			if (current.equals(Goal)) {
				printAll(current);
				System.out.println("Solution found");
				return;
			}
			
			// Otherwise, generate all the children and add them to the queue
			children = current.children(Goal);
			for (State c : children) {
				if (c.depth <= MAX_DEPTH) {	// All puzzles can be solved in 31 moves or less, so don't make more
					// No sane way to do this formula, essentially this is pruning nodes
					if ((c.depth < 23) || (c.depth == 23 && c.hval <= 12) || (c.depth == 24 && c.hval <= 10) || (c.depth == 25 && c.hval <= 8)
							|| (c.depth == 26 && c.hval <= 6) || (c.depth == 27 && c.hval <= 6) || (c.depth == 28 && c.hval <= 5) || 
							(c.depth == 29 && c.hval <= 4) || (c.depth == 30 && c.hval <= 2) || (c.depth == 31 && c.hval == 0)) {
						if (!(closed.containsKey(c))) { // Make sure it's not in closed
							if (!(open.contains(c))) { // Make sure it's not in open
								i = 0; added = false;
								while (i < open.size()) {
									if (c.hval < open.get(i).hval) { open.add(i, c); added = true; }
									if (added == true) { break; }
									i++;
								}
								if (added != true) { open.add(c); }
							}
						}
					}
				}
			}
		}
		
		// If we went through all states, there is no solution
		System.out.println("No solution");
	}
	
	
	// Print all the parents of state s and itself
	public static void printAll(State s) {
		for (State node : s.parents()) {
			node.print();
		}
	}
	
	public static void main(String[] args) throws IOException {
		// Test using pruning --- not 100% sure this works
		Scanner stdin = new Scanner(System.in);
		System.out.println("Test using tree pruning? Y/N");
		String prune = stdin.next();
		if (prune.compareTo("Y") == 0) { SPEED = true; System.out.println("Using pruning..."); } else { SPEED = false; }
		
		
		Scanner fin = new Scanner(new File("puzzle.in"));
		int tests = fin.nextInt();
		
		// Input to receive: InitialState and Goal
		char[] ISin = new char[N*N];
		char[] GOALin = new char[N*N];
		String in;
		
		for (int i = 1; i <= tests; i++) {
			System.out.println("Test case #" + i + ":");
			
			// For each test case, read in the IS and Goal
			for (int j = 0; j < Math.pow(N, 2); j++) {
				in = fin.next();
				ISin[j] = in.charAt(0);
			}
			for (int j = 0; j < Math.pow(N, 2); j++) {
				in = fin.next();
				GOALin[j] = in.charAt(0);
			}
			
			// Create the States
			State IS = new State(ISin);
			State Goal = new State(GOALin);
			
			
			// Find the shortest path to the goal using Best First Search and Manhattan Distance algorithm heuristic
			// If there's a solution, print the path. Otherwise, print no solution.
			if (SPEED == true) { SpeedBestFirstSearch(IS, Goal); }
			else { BestFirstSearch(IS, Goal); }
		}
	}
}
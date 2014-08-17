import java.util.*;

public class State {
	private char[] numbers;
	private State parent;
	public int hval;
	public int depth;
	
	// Create a terminal state
	public State(char[] numbers) {
		this.numbers = numbers;
		this.parent = null;
		this.hval = 0;	// Doesn't need hval
		this.depth = 0;
	}
	
	// Create a child of the parent state
	public State(char[] numbers, State parent, State Goal) {
		this.numbers = numbers;
		this.parent = parent;
		this.hval = ManhattanDistance(Goal);
		this.depth = parent.depth + 1;
	}
	
	
	// Return a list of all the children of this state
	public List<State> children(State Goal) {
		List<State> c = new ArrayList<State>();
		State s;
		int i;
		
		// Somewhat inefficient to save lines:
		// create 4 new arrays and copy the contents of this state's numbers to swap later
		char[] s1, s2, s3, s4;
		s1 = new char[puzzle.N*puzzle.N];
		s2 = new char[puzzle.N*puzzle.N];
		s3 = new char[puzzle.N*puzzle.N];
		s4 = new char[puzzle.N*puzzle.N];
		
		// Find the star and copy arrays
		int star = 0;
		for (int j = 0; j < this.numbers.length; j++) {
			if (this.numbers[j] == '*') {
				star = j;
			}
			s1[j] = s2[j] = s3[j] = s4[j] = this.numbers[j];
		}
		

		// Nasty switch statement to determine states to generate
		switch(star) {
			// Switch with 1 and 3
			case 0:
				s1[0] = this.numbers[1];
				s1[1] = this.numbers[0];
				s2[0] = this.numbers[3];
				s2[3] = this.numbers[0];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				break;
			// Switch with 0, 2, and 4
			case 1:
				s1[0] = this.numbers[1];
				s1[1] = this.numbers[0];
				s2[1] = this.numbers[2];
				s2[2] = this.numbers[1];
				s3[1] = this.numbers[4];
				s3[4] = this.numbers[1];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				c.add(new State(s3, this, Goal));
				break;
			// Switch with 1 and 5
			case 2:
				s1[2] = this.numbers[1];
				s1[1] = this.numbers[2];
				s2[2] = this.numbers[5];
				s2[5] = this.numbers[2];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				break;
			// Switch with 0, 4, and 6
			case 3:
				s1[3] = this.numbers[0];
				s1[0] = this.numbers[3];
				s2[3] = this.numbers[4];
				s2[4] = this.numbers[3];
				s3[3] = this.numbers[6];
				s3[6] = this.numbers[3];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				c.add(new State(s3, this, Goal));
				break;
			// Switch with 1, 3, 5, and 7
			case 4:
				s1[4] = this.numbers[1];
				s1[1] = this.numbers[4];
				s2[4] = this.numbers[3];
				s2[3] = this.numbers[4];
				s3[4] = this.numbers[5];
				s3[5] = this.numbers[4];
				s4[4] = this.numbers[7];
				s4[7] = this.numbers[4];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				c.add(new State(s3, this, Goal));
				c.add(new State(s4, this, Goal));
				break;
			// Switch with 2, 4, and 8
			case 5:
				s1[5] = this.numbers[2];
				s1[2] = this.numbers[5];
				s2[5] = this.numbers[4];
				s2[4] = this.numbers[5];
				s3[5] = this.numbers[8];
				s3[8] = this.numbers[5];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				c.add(new State(s3, this, Goal));
				break;
			// Switch with 3 and 7
			case 6:
				s1[6] = this.numbers[3];
				s1[3] = this.numbers[6];
				s2[6] = this.numbers[7];
				s2[7] = this.numbers[6];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				break;
			// Switch with 4, 6 and 8
			case 7:
				s1[7] = this.numbers[4];
				s1[4] = this.numbers[7];
				s2[7] = this.numbers[6];
				s2[6] = this.numbers[7];
				s3[7] = this.numbers[8];
				s3[8] = this.numbers[7];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				c.add(new State(s3, this, Goal));
				break;
			// Swith with 5 and 7
			case 8:
				s1[8] = this.numbers[5];
				s1[5] = this.numbers[8];
				s2[8] = this.numbers[7];
				s2[7] = this.numbers[8];
				
				// Add the new states in proper order
				c.add(new State(s1, this, Goal));
				c.add(new State(s2, this, Goal));
				break;
			default:
				System.out.println("Something weird happened. Aborting program...");
				System.exit(1);
		}
		
		
		
		return c;
	}
	
	// Return a list of all the parents of this state, starting at the deepest ancestor and ending with this state
	public List<State> parents() {
		State current = this;
		List<State> p = new ArrayList<State>();
		
		// Add every parent until the terminal node
		p.add(this);
		while (current.parent != null) {
			current = current.parent;
			p.add(current);
		}
		
		// Reverse this list
		for (int i = 0; i < p.size()/2; i++) {
			current = p.get(i);
			p.set(i, p.get(p.size() - i - 1));
			p.set(p.size() - i - 1, current);
		}
		
		return p;
	}
	
	// Print this state
	public void print() {
		for (int i = 0; i < this.numbers.length; i += this.numbers.length/puzzle.N) {
			System.out.println(this.numbers[i] + " " + this.numbers[i+1] + " " + this.numbers[i+2]);
		}
	}
	
	// Checks if this state is equal to another state, rhs
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (!(other instanceof State)) { return false; }
		
		// Cast to state for specific checking
		State rhs = (State)other;
		
		for (int i = 0; i < this.numbers.length; i++) {
			if (this.numbers[i] != rhs.numbers[i]) { return false; }
		}
		

		// No false conditions so it is equal
		return true;
	}
	
	// Make States hashmap-able
	public int hashCode() {
		int hash = 7919;	// Random prime number
		// Using Josh Bloch's "Effective Java" hash function
		for (int i = 0; i < this.numbers.length; i++) {		// Slight modification because I know all arrays contain the same elements,
			hash = hash * 37 + (int)this.numbers[i] * i;	// but not in the same order, so use the position as part of the function
		}
		hash = hash * 37 + this.hval;
		
		return hash;
	}
	
	// Determines the heuristic value of this state using the Goal and the Manhattan Distance algorithm
	public int ManhattanDistance(State Goal) {
		int heuristic = 0;
		
		for (int i = 0; i < this.numbers.length; i++) {
			heuristic += Distance(i, Goal);
		}
		
		return heuristic;
	}
	
	// Determines the distance of the number at i from its location in another state, rhs
	public int Distance(int i, State rhs) {
		// Find the same number in the other state
		for (int j = 0; j < rhs.numbers.length; j++) {
			if (this.numbers[i] == rhs.numbers[j]) {
				// If they're at the same location, no distance
				if (i == j) { return 0; } else {
					// Otherwise we have to do this nasty nested switch statement
					// This could be done in a much more elegant way but I ran out of time
					switch(i) {
						case 0:
							switch(j) {
								case 1:
								case 3:
									return 1;
								case 2:
								case 4:
								case 6:
									return 2;
								case 5:
								case 7:
									return 3;
								case 8:
									return 4;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 1:
							switch(j) {
								case 0:
								case 2:
								case 4:
									return 1;
								case 3:
								case 5:
								case 7:
									return 2;
								case 6:
								case 8:
									return 3;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 2:
							switch(j) {
								case 1:
								case 5:
									return 1;
								case 0:
								case 4:
								case 8:
									return 2;
								case 3:
								case 7:
									return 3;
								case 6:
									return 4;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 3:
							switch(j) {
								case 0:
								case 4:
								case 6:
									return 1;
								case 1:
								case 5:
								case 7:
									return 2;
								case 2:
								case 8:
									return 3;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 4:
							switch(j) {
								case 1:
								case 3:
								case 5:
								case 7:
									return 1;
								case 0:
								case 2:
								case 6:
								case 8:
									return 2;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 5:
							switch(j) {
								case 2:
								case 4:
								case 8:
									return 1;
								case 1:
								case 3:
								case 7:
									return 2;
								case 0:
								case 6:
									return 3;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 6:
							switch(j) {
								case 3:
								case 7:
									return 1;
								case 0:
								case 4:
								case 8:
									return 2;
								case 1:
								case 5:
									return 3;
								case 2:
									return 4;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 7:
							switch(j) {
								case 6:
								case 4:
								case 8:
									return 1;
								case 3:
								case 5:
								case 1:
									return 2;
								case 0:
								case 2:
									return 3;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						case 8:
							switch(j) {
								case 5:
								case 7:
									return 1;
								case 2:
								case 4:
								case 6:
									return 2;
								case 1:
								case 3:
									return 3;
								case 0:
									return 4;
								default:
									System.out.println("Something weird happened. Aborting program...");
									System.exit(1);
							}
						default:
							System.out.println("Not a 3x3 puzzle; code needs to be updated.");
							System.exit(1);
					}
				}
			}
		}
		// This should never happen
		return 0;
	}
}
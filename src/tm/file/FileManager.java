package tm.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tm.part.Direction;
import tm.part.State;
import tm.part.Symbol;
import tm.part.Transition;

public class FileManager {
	
	private File file;
	private FileReader fileReader;
	private BufferedReader br;
	
	private ArrayList<Transition> transitions;
	private State initial;
	private State accept;
	private State reject;
	private Symbol blank;
	
	public FileManager(String fileName) throws FileNotFoundException {

		file = new File(fileName);
		fileReader = new FileReader(file);
		br = new BufferedReader(fileReader);
	}

	public void readData() {
		
		transitions = new ArrayList<Transition>();
		String line = "";
		
		reading:
		while(line != null) {
			
			if(!line.trim().isEmpty() && line.trim().toUpperCase().startsWith("INITIAL")) {
				line = line
						.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll(":", "")
						.substring("INITIAL".length());
				try {
					initial = new State(line.charAt(0));
				} catch(Exception e) {
					initial = null;
				}
				line = readLine();
				continue;
			}
			
			if(!line.trim().isEmpty() && line.trim().toUpperCase().startsWith("ACCEPT")) {
				line = line
						.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll(":", "")
						.substring("ACCEPT".length());
				try {
					accept = new State(line.charAt(0));
				} catch(Exception e) {
					accept = null;
				}
				line = readLine();
				continue;
			}
			
			if(!line.trim().isEmpty() && line.trim().toUpperCase().startsWith("REJECT")) {
				line = line
						.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll(":", "")
						.substring("REJECT".length());
				try {
					reject = new State(line.charAt(0));
				} catch(Exception e) {
					reject = null;
				}
				line = readLine();
				continue;
			}

			if(!line.trim().isEmpty() && line.trim().toUpperCase().startsWith("BLANK")) {
				line = line
						.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll(":", "")
						.substring("BLANK".length());
				try {
					blank = new Symbol(line.charAt(0));
				} catch(Exception e) {
					blank = null;
				}
				line = readLine();
				continue;
			}
			
			if(!line.trim().isEmpty() && !line.startsWith("//")) {
				String[] tmpItems = line
						.replaceAll("-", ",")
						.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll(">", "")
						.replaceAll("\\(", "")		// brackets must be escaped
						.replaceAll("\\)", "")
						.split(",");
				
				if(tmpItems.length != 5) {
					line = readLine();
					continue reading;
				}
				
				Direction dir;
				switch(tmpItems[4].toUpperCase()) {
				case "LEFT":
					dir = Direction.LEFT;
					break;
				case "RIGHT":
					dir = Direction.RIGHT;
					break;
				case "STAY":
					dir = Direction.STAY;
					break;
				default:
					line = readLine();
					continue reading;
				}

				try {
					transitions.add(new Transition(
							new State(tmpItems[0].charAt(0)),
							new Symbol(tmpItems[1].charAt(0)),
							new State(tmpItems[2].charAt(0)),
							new Symbol(tmpItems[3].charAt(0)),
							dir
							));
				} catch(Exception e) {
					transitions = null;
					return;
				}
			}
			
			line = readLine();
		}
	}
	
	private String readLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void printFileNotFound(String tmFileName) {
		System.out.println("ERROR: File \"" + tmFileName + "\" not found.\n");
		System.out.println("       Please create this file and define the turing machine in it similar to this:");
		System.out.println();
		System.out.println("          // This is a comment.");
		System.out.println();
		System.out.println("          blank: 0");
		System.out.println("          initial: 0");
		System.out.println("          accept: +");
		System.out.println("          reject: -");
		System.out.println();
		System.out.println("          // There are several ways to define the transitions");
		System.out.println();
		System.out.println("          (A, a) -> (B, b, right)");
		System.out.println("          A, a - B, b, stay");
		System.out.println("          A,a,B,b,left");
	}
	
	//======================== GETTERS AND SETTERS ========================//

	public State getInitial() {
		return initial;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public State getAccept() {
		return accept;
	}

	public State getReject() {
		return reject;
	}

	public Symbol getBlank() {
		return blank;
	}
	
	public void setInitial(State initial) {
		this.initial = initial;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public void setAccept(State accept) {
		this.accept = accept;
	}

	public void setReject(State reject) {
		this.reject = reject;
	}

	public void setBlank(Symbol blank) {
		this.blank = blank;
	}
}

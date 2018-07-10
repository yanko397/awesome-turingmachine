
import java.util.ArrayList;

public class Main {

	private static TuringMachine tm;

	private static ArrayList<State> states;
	private static ArrayList<Symbol> inputSymbols;
	private static ArrayList<Symbol> workSymbols;
	private static ArrayList<Transition> transitions;
	private static Symbol blank;
	private static State initialState;
	private static State accept;
	private static State reject;
	
	public static void main(String[] args) {
		
		exampleFromTheLecture();
		
		FileManager fm = new FileManager("transitions.txt");
		transitions = new ArrayList<Transition>();
		transitions = fm.readTransitions();
		print();
		
		tm = new TuringMachine(states, inputSymbols, workSymbols, transitions, blank, initialState, accept, reject);
		tm.init("abbaabba");
		boolean accepted = tm.run();

		String output = tm.getContent().replaceAll("" + blank.getName(), "");
		System.out.println("\nAccepted: " + accepted);
		System.out.println("\nOutput: " + (output.isEmpty() ? "[Empty]" : output));
	}
	
	
	
	
	
	
	
	
	
	
	public static void print() {
		
		System.out.println("States:");
		for(State x : states) System.out.print("  " + x.getName());
		System.out.println("\n\n" + "Input Symbols:");
		for(Symbol x : inputSymbols) System.out.print("  " + x.getName());
		System.out.println("\n\n" + "Working Symbols:");
		for(Symbol x : workSymbols) System.out.print("  " + x.getName());
		System.out.println("\n\n" + "Transitions:");
		for(Transition x : transitions) System.out.print("  (" + x.getS1().getName() + ", " + x.getRead().getName() + ") -> (" + x.getS2().getName() + ", " + x.getWrite().getName() + ", " + x.getDir() + ")\n");
	}
	
	public static void exampleFromTheLecture() {
		
		states = new ArrayList<State>();
		states.add(new State('0'));		//0
		states.add(new State('N'));		//1
		states.add(new State('A'));		//2
		states.add(new State('Z'));		//3
		states.add(new State('B'));		//4
		states.add(new State('Y'));		//5
		states.add(new State('+'));		//6
		states.add(new State('-'));		//7

		inputSymbols = new ArrayList<Symbol>();
		inputSymbols.add(new Symbol('a'));		//0
		inputSymbols.add(new Symbol('b'));		//1
		
		workSymbols = new ArrayList<Symbol>(inputSymbols);
		workSymbols.add(new Symbol('0'));	//2

		blank = workSymbols.get(workSymbols.size()-1);
		
		initialState = states.get(0);
		accept = states.get(states.size()-2);
		reject = states.get(states.size()-1);
	}
}

package tm;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import tm.part.State;
import tm.part.Symbol;
import tm.part.Tape;
import tm.part.Transition;

public class TuringMachine {
	
	private int steps;

	private ArrayList<Transition> transitions;
	private Symbol blank;
	private State initial;
	private State accept;
	private State reject;
	private Tape tape;

	private State currentState;

	public TuringMachine(ArrayList<Transition> transitions, Symbol blank, State initialState, State accept, State reject) {

		this.transitions = transitions;
		this.blank = blank;
		this.initial = initialState;
		this.accept = accept;
		this.reject = reject;
	}

	public void init() {
		init("");
	}
	
	public void init(String word) {
		char[] tmpChars = word.toCharArray();
		ArrayList<Symbol> tmpList = new ArrayList<Symbol>();
		for (int i = 0; i < tmpChars.length; i++)
			tmpList.add(new Symbol(tmpChars[i]));

		init(tmpList);
	}

	public void init(ArrayList<Symbol> word) {
		tape = new Tape(word, blank);
		currentState = initial;
		steps = 0;
	}
	
	public boolean run() {
		return run(0, true, true);
	}
	
	public boolean run(boolean print) {
		return run(0, print, print);
	}

	public boolean run(long timeoutMillis, boolean printSteps, boolean printErrors) {
		
		if(!readyToRun(printErrors)) return false;

		if(printSteps) System.out.println("\n DURCHLAUF:\n");

		while (true) {
			if (currentState.getName() == accept.getName())
				return true;
			else if (reject != null && currentState.getName() == reject.getName())
				return false;

			for (Transition t : transitions) {
				if (t.getS1().getName() == currentState.getName() && t.getRead().getName() == tape.getCurrentSymbol().getName()) {
					steps++;
					
					if(timeoutMillis != 0) {
						try {TimeUnit.MILLISECONDS.sleep(timeoutMillis);} catch (InterruptedException e)					//for slow execution
						{e.printStackTrace();}
					}

					tape.step(t);
					currentState = t.getS2();
					
					if(printSteps) printStep(t);
				}
			}
		}
	}
	
	private boolean readyToRun(boolean printErrors) {
		
		if(initial == null) {
			if(printErrors) System.out.println(
					  " ERROR: No initial state found.\n"
					+ "        You can define it like this:\n"
					+ "           initial: 0");
			return false;
		}
		if(accept == null) {
			if(printErrors) System.out.println(
					  " ERROR: No accepting state found.\n"
					+ "        You can define it like this:\n"
					+ "           accept: +");
			return false;
		}
		if(blank == null) {
			if(printErrors) System.out.println(
					  " ERROR: No blank symbol defined.\n"
					+ "        You can define it like this:\n"
					+ "           blank: 0");
			return false;
		}
		if(transitions == null) {
			if(printErrors) System.out.println(
					  " ERROR: There is propably something wrong with the transitions.\n"
					+ "        Try a format similar to one of these:\n"
					+ "           (A, a) -> (B, b, right)\n"
					+ "           A,a-B,b,left\n"
					+ "           A, a, B, b, stay");
			return false;
		}
		if(reject == null) {
			if(printErrors) System.out.println("\n WARNING: No rejecting state has been set.");
		}
		return true;
	}
	
	public String getTapeContent() {
		StringBuilder sb = new StringBuilder();
		for (Symbol s : tape.getContent())
			sb.append(s.getName());
		return sb.toString();
	}
	
	public String getOutput() {
		String tmp = getTapeContent().replaceAll("" + blank.getName(), "").trim();
		if(tmp.isEmpty()) return "[Empty]";
		else return tmp;
	}

	public void printContents() {

		if(!readyToRun(false)) return;
		System.out.println("\n Initial State:");
		System.out.println("   " + initial.getName());
		System.out.println("\n Accepting State:");
		System.out.println("   " + accept.getName());
		if(reject != null) {
			System.out.println("\n Rejecting State:");
			System.out.println("   " + reject.getName());
		}
		System.out.println("\n Blank Symbol:");
		System.out.println("   " + blank.getName());
		System.out.println("\n Transitions:");
		for(Transition t : transitions) printTransition(t);
	}
	
	private void printStep(Transition t) {
		if(steps % 1000000 == 0) System.out.println(" " + steps);
		System.out.print("  " + getTapeContent());
		printTransition(t);
	}

	private void printTransition(Transition t) {
		System.out.println("  (" + t.getS1().getName() + ", " + t.getRead().getName()
				+ ") -> (" + t.getS2().getName() + ", " + t.getWrite().getName() + ", "
				+ t.getDir() + ")");
	}
	
	public int getSteps() {
		return steps;
	}
}

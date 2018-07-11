package tm;
import java.util.ArrayList;

import tm.part.State;
import tm.part.Symbol;
import tm.part.Tape;
import tm.part.Transition;

public class TuringMachine {

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
	}

	public boolean run() {
		
		if(!readyToRun(true)) return false;

		System.out.println("\n DURCHLAUF:\n");

		while (true) {
			if (currentState.getName() == accept.getName())
				return true;
			else if (currentState.getName() == reject.getName())
				return false;

			for (Transition t : transitions) {
				if (t.getS1().getName() == currentState.getName()
						&& t.getRead().getName() == tape.getCurrentSymbol().getName()) {
					// try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e)					//for slow execution
					// {e.printStackTrace();}

					tape.step(t);
					currentState = t.getS2();
					System.out.print("  " + getTapeContent());
					System.out.println("  (" + t.getS1().getName() + ", " + t.getRead().getName()
							+ ") -> (" + t.getS2().getName() + ", " + t.getWrite().getName() + ", "
							+ t.getDir() + ")\n");
				}
			}
		}
	}
	
	private boolean readyToRun(boolean print) {
		
		if(initial == null) {
			if(print) System.out.println(
					  " ERROR: No initial state found.\n"
					+ "        You can define it like this:\n"
					+ "           initial: 0");
			return false;
		}
		if(accept == null) {
			if(print) System.out.println(
					  " ERROR: No accepting state found.\n"
					+ "        You can define it like this:\n"
					+ "           accept: +");
			return false;
		}
		if(blank == null) {
			if(print) System.out.println(
					  " ERROR: No blank symbol defined.\n"
					+ "        You can define it like this:\n"
					+ "           blank: 0");
			return false;
		}
		if(transitions == null) {
			if(print) System.out.println(
					  " ERROR: There is propably something wrong with the transitions.\n"
					+ "        Try a format similar to one of these:\n"
					+ "           (A, a) -> (B, b, right)\n"
					+ "           A,a-B,b,left\n"
					+ "           A, a, B, b, stay");
			return false;
		}
		return true;
	}
	
	public String getTapeContent() {
		StringBuilder sb = new StringBuilder();
		for (Symbol s : tape.getContent())
			sb.append(s.getName());

		String content = sb.toString();
		if (content.isEmpty())
			return "[empty]";
		else
			return content;
	}

	public void printContent() {

		if(!readyToRun(false)) return;
//		System.out.println("States:");
//		for(State x : states) System.out.print("  " + x.getName());
//		System.out.println("\n\n" + "Input Symbols:");
//		for(Symbol x : inputSymbols) System.out.print("  " + x.getName());
//		System.out.println("\n\n" + "Working Symbols:");
//		for(Symbol x : workSymbols) System.out.print("  " + x.getName());
		System.out.println("\n Initial State:");
		System.out.println("   " + initial.getName());
		System.out.println("\n Accepting State:");
		System.out.println("   " + accept.getName());
		System.out.println("\n Rejecting State:");
		System.out.println("   " + reject.getName());
		System.out.println("\n Transitions:");
		for(Transition t : transitions) System.out.print("  (" + t.getS1().getName() + ", " + t.getRead().getName() + ") -> (" + t.getS2().getName() + ", " + t.getWrite().getName() + ", " + t.getDir() + ")\n");
	}
}

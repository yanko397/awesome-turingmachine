import java.util.ArrayList;

public class TuringMachine {

	private ArrayList<State> states;
	private ArrayList<Symbol> inputSymbols;
	private ArrayList<Symbol> workingSymbols;
	private ArrayList<Transition> transitions;
	private Symbol blank;
	private State initialState;
	private State accept;
	private State reject;
	private Tape tape;

	private State currentState;

	public TuringMachine(ArrayList<State> states, ArrayList<Symbol> inputSymbols, ArrayList<Symbol> workingSymbols,
			ArrayList<Transition> transitions, Symbol blank, State initialState, State accept, State reject) {

		this.states = states;
		this.inputSymbols = inputSymbols;
		this.workingSymbols = workingSymbols;
		this.transitions = transitions;
		this.blank = blank;
		this.initialState = initialState;
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
		currentState = initialState;
	}

	public boolean run() {

		System.out.println("\nDURCHLAUF:\n");

		while (true) {
			if (currentState.getName() == accept.getName())
				return true;
			else if (currentState.getName() == reject.getName())
				return false;

			for (Transition transition : transitions) {
				if (transition.getS1().getName() == currentState.getName()
						&& transition.getRead().getName() == tape.getCurrentSymbol().getName()) {
					// try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e)
					// {e.printStackTrace();}

					tape.step(transition);
					currentState = transition.getS2();
					System.out.print("  " + getContent());
					System.out.println("  (" + transition.getS1().getName() + ", " + transition.getRead().getName()
							+ ") -> (" + transition.getS2().getName() + ", " + transition.getWrite().getName() + ", "
							+ transition.getDir() + ")\n");
				}
			}
		}
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		for (Symbol s : tape.getContent())
			sb.append(s.getName());

		String content = sb.toString();
		if (content.isEmpty())
			return "[empty]";
		else
			return content;
	}
}

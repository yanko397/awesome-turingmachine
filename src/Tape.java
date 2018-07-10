import java.util.ArrayList;

public class Tape {

	private ArrayList<Symbol> posTape;
	private ArrayList<Symbol> negTape;
	private int head;
	private Symbol blank;
	
	public Tape(ArrayList<Symbol> input, Symbol blank) {
		
		posTape = new ArrayList<Symbol>(input);
		negTape = new ArrayList<Symbol>();
		head = 0;
		this.blank = blank;
	}
	
	public void step(Transition transition) {
		
		if(head >= 0) {
			
			if(head > posTape.size()-1)
				posTape.add(transition.getWrite());
			else
				posTape.set(head, transition.getWrite());
			
		} else {
			
			if(Math.abs(head) > negTape.size())
				negTape.add(transition.getWrite());
			else
				negTape.set(Math.abs(head)-1, transition.getWrite());
		}
		
		if(transition.getDir() == Direction.LEFT) {
			head--;
		}
		else if(transition.getDir() == Direction.RIGHT) {
			head++;
		}
	}
	
	public Symbol getCurrentSymbol() {

		checkForErr();
		if(head >= 0)
			return posTape.get(head);
		else
			return negTape.get(Math.abs(head)-1);
	}

	private void checkForErr() {
		
		if(head < 0 && Math.abs(head) > negTape.size())
			negTape.add(blank);
		if(head > posTape.size()-1)
			posTape.add(blank);
	}
	
	public ArrayList<Symbol> getContent() {
		
		ArrayList<Symbol> content = new ArrayList<Symbol>();
		
		for(int i = negTape.size()-1; i > 0; i--)
			content.add(negTape.get(i));
		
		for(Symbol x : posTape)
			content.add(x);
		
		return content;
	}
}

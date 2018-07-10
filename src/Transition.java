
public class Transition {

	private State s1;
	private Symbol read;
	private State s2;
	private Symbol write;
	private Direction dir;
	
	public Transition(State s1, Symbol read, State s2, Symbol write, Direction dir) {
		this.s1 = s1;
		this.read = read;
		this.s2 = s2;
		this.write = write;
		this.dir = dir;
	}

	//============================== GETTER UND SETTER ==============================//

	public State getS1() {
		return s1;
	}

	public void setS1(State s1) {
		this.s1 = s1;
	}

	public Symbol getRead() {
		return read;
	}

	public void setRead(Symbol read) {
		this.read = read;
	}

	public State getS2() {
		return s2;
	}

	public void setS2(State s2) {
		this.s2 = s2;
	}

	public Symbol getWrite() {
		return write;
	}

	public void setWrite(Symbol write) {
		this.write = write;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}
}

package tm;

import tm.file.FileManager;

public class Main {

	public static void main(String[] args) {
		
		FileManager fm = new FileManager("res/tm.txt");
		fm.readData();
		
		TuringMachine tm = new TuringMachine(fm.getTransitions(), fm.getBlank(), fm.getInitial(), fm.getAccept(), fm.getReject());
		tm.printContent();
		if(args.length > 0)
			tm.init(args[0]);
		else
			tm.init("abbabaaaaa");
		
		boolean accepted = tm.run();

		String output = tm.getTapeContent().replaceAll("" + fm.getBlank().getName(), "");
		System.out.println("\n Accepted: " + accepted);
		System.out.println("\n Output: " + (output.isEmpty() ? "[Empty]" : output));
	}
}

package tm;

import java.io.FileNotFoundException;

import tm.file.FileManager;

public class Main {

	public static void main(String[] args) {
		
		String tmFileName = "tml.txt";
		
		FileManager fm;
		try {
			fm = new FileManager(tmFileName);
		} catch (FileNotFoundException e) {
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
			return;
		}
		fm.readData();
		
		TuringMachine tm = new TuringMachine(fm.getTransitions(), fm.getBlank(), fm.getInitial(), fm.getAccept(), fm.getReject());
		tm.printContent();
		if(args.length > 0)
			tm.init(args[0]);
		else
			tm.init("bbaaa");
		
		boolean accepted = tm.run();

		String output = tm.getTapeContent().replaceAll("" + fm.getBlank().getName(), "");
		System.out.println("\n Accepted: " + accepted);
		System.out.println("\n Output: " + (output.isEmpty() ? "[Empty]" : output));
	}
}

package tm;

import java.io.FileNotFoundException;

import tm.file.FileManager;

public class Main {

	public static void main(String[] args) {
		
		String tmFileName = "busybeaver.txt";
		String word = "";
		long timeoutMillis = 0;
		boolean printContents = true;
		boolean printOutput = false;
		boolean printErrors = true;
		
		FileManager fm;
		try {
			fm = new FileManager(tmFileName);
		} catch (FileNotFoundException e) {
			FileManager.printFileNotFound(tmFileName);
			return;
		}
		fm.readData();
		
		TuringMachine tm = new TuringMachine(fm.getTransitions(), fm.getBlank(), fm.getInitial(), fm.getAccept(), fm.getReject());
		if(printContents) tm.printContents();
		if(args.length > 0)
			tm.init(args[0]);
		else
			tm.init(word);
		
		boolean accepted = tm.run(timeoutMillis, printOutput, printErrors);

		System.out.println("\n Accepted:      " + accepted);
		System.out.println("\n Steps:         " + tm.getSteps());
		System.out.println("\n Output:        " + tm.getOutput());
		System.out.println("\n Output length: " + tm.getOutput().length());
	}
}

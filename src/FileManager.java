import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	
	private BufferedReader br;
	
	public FileManager(String fileName) {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Transition> readTransitions() {
		
		ArrayList<Transition> list = new ArrayList<Transition>();
		String tmpLine = "";
		
		reading:
		while(tmpLine != null) {
			
			if(!tmpLine.trim().isEmpty()) {
				String[] tmpItems = tmpLine.trim().replaceAll("-", ",").replaceAll("\t", "").replaceAll(" ", "").split(",");
				
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
					continue reading;
				}

				list.add(new Transition(new State(tmpItems[0].charAt(0)), new Symbol(tmpItems[1].charAt(0)), new State(tmpItems[2].charAt(0)), new Symbol(tmpItems[3].charAt(0)), dir));
			}
			
			try {
				tmpLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}

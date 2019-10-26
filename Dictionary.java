import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Dictionary {
	static HashMap<String, String> myMap = new HashMap<String, String>();
	String filename = "dictionary.txt";
	Scanner scan = new Scanner(System.in);
	
	private void readFile() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				if(line.length()>1) {
					String second = in.readLine();
					myMap.put(line.toLowerCase(), second);
				}
					
			}
			in.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found :( make sure file is in the project (not source code) and "
					+ "has the correct name");
		} 
		catch (IOException e) {}
		
	}
	
	public static int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
	private void translate() {
		while(true) {
			System.out.println("Type a word to translate or type q to quit");
			String answer = scan.nextLine().toLowerCase();
			if(answer.equals("q")) {
				System.out.println("bye");
				break;
			}
			if(!myMap.containsKey(answer)) {
				for(String k : myMap.values()) {
					int accuracy = minDistance(answer, k);
					if(accuracy>1)
						break;
				}
				System.out.println("Not Available");
			}
			else {
				String x = myMap.get(answer);
				System.out.println(x);
			}
		}
	}
	
	public static void main(String[] args) {
		Dictionary test = new Dictionary();
		test.readFile();
		test.translate();
		
	}
}

import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Scanner input;
		int lines;
		while(true){
			input = new Scanner(System.in);
			try {
				lines = input.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("Please input a valid Integer");
			}

		}
		questions qu = new questions();
		qu.init(lines);
		qu.generate();
	}
}

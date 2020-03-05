import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) {
		//int[] vals = {0,2,0,3,4,5,0,0,7,0,5,0,0,0,6,3,0,0,4,0,0,0,0,1,2,6,5,7,3,0,1,0,0,0,0,9,6,0,5,0,7,9,0,0,3,2,0,9,0,0,8,7,0,0,0,4,2,0,0,7,9,5,1,0,0,0,0,5,0,0,3,8,0,6,0,0,0,0,4,0,2};
		//int[] vals = {6,0,0,3,0,0,0,0,8,0,4,0,0,0,0,0,0,0,0,0,8,9,6,2,0,0,0,0,8,0,4,9,6,0,0,7,0,7,5,2,1,0,3,9,0,0,0,6,7,5,0,0,0,0,8,9,0,0,3,1,6,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,4,9};
		int[] vals = {8,0,0,6,0,0,0,1,0,
				0,2,7,0,0,0,0,9,0,
				0,0,0,5,4,0,0,0,2,
				0,0,0,0,0,5,9,0,0,
				6,0,1,0,2,0,0,0,0,
				0,4,0,7,0,0,0,0,0,
				0,0,0,1,0,0,0,6,0,
				4,0,0,0,9,0,0,8,0,
				0,0,6,0,0,0,7,0,0};
//		System.out.println(vals.length);
		Sudoku game = new Sudoku(vals);
		int[] solution = game.solve();
		System.out.print("\n");
		for (int i = 0; i < 81; i++) {
			if (i % 27 == 0) {
				for (int j = 0; j < 13; j++) {
					if (j % 4 == 0)
						System.out.print("+");
					else
						System.out.print("-");
				}
				System.out.println();
			}
			if (i % 9 == 0)
				System.out.print("|");
			System.out.print(solution[i]);
			if (i % 3 == 2) {
				System.out.print("|");
			}
			if (i % 9 == 8) {
				System.out.println();
			}
		}
		for (int j = 0; j < 13; j++) {
			if (j % 4 == 0)
				System.out.print("+");
			else
				System.out.print("-");
		}
		System.out.println();
	}
}


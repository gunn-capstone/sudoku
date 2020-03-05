import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku {
	
	int[] startingVals;
    
    public Sudoku(int[] a) {
    	startingVals = a;
    }

    //helper function; call this one from main
    public int[] solve() {
    	//initialize arr; stores values of squares
    	int[] arr = startingVals;
    	//initialize emptySquares; stores indices of empty squares 
    	ArrayList<Integer> emptySquares = new ArrayList<Integer>();
    	emptySquares = (ArrayList<Integer>) IntStream.rangeClosed(0, 80).boxed().collect(Collectors.toList());
    	//initialize squares
    	Square[] squares = new Square[81];
    	for (int i = 0; i < 81; i++) {
    		if (arr[i] == 0) {
    			squares[i] = new Square(i);
    		} else {
    			squares[i] = new Square(arr[i], i);
    		}
    	}
    	//initialize posNums
    	Square cur;
    	for (int i = 0; i < 81; i++) {
    		cur = squares[i];
    		if (cur.isFilled()) {
    			arr[i] = cur.getValue();
    			updateSquares(i, squares);
    			emptySquares.remove(Integer.valueOf(i));
    		}
    	}
    	return solve(arr, emptySquares, squares);
    }
    
    //recursive function
	public int[] solve(int[] arr, ArrayList<Integer> emptySquares, Square[] squares) {
    	//while remaining squares:
		Square cur;
    	while (emptySquares.size() > 0) {
    		boolean same = true;
    		for (int i = 0; i < 81; i++) {
    			cur = squares[i];
    			if (!cur.isFilled() && cur.getPosNums().size() == 1) {
    				cur.fill();
    				arr[i] = cur.getValue();
    				updateSquares(i, squares);
    				emptySquares.remove(Integer.valueOf(i));
    				same = false;
    			} else if (cur.isFilled() && cur.getValue() == 0) {
    				int[] err = {};
    				System.out.println("Wrong guess");
    				return err;
    			}
    		}
    		if (same) {
    			System.out.println("Made a guess for " + emptySquares.get(0));
    			int[] storedArr = arr.clone();
    			ArrayList<Integer> storedES = (ArrayList<Integer>) emptySquares.clone();
    			Square[] storedSquares = new Square[81];
    			for(int i = 0; i < 81; i++) {
    				storedSquares[i] = new Square(squares[i].getValue(), squares[i].getIndex(), (ArrayList<Integer>) squares[i].getPosNums().clone());
    			}
    			Square firstES = squares[emptySquares.get(0)];
    			firstES.fill();
    			arr[emptySquares.get(0)] = firstES.getValue();
    			updateSquares(emptySquares.get(0), squares);
    			emptySquares.remove(0);   			
    			int[] sol = solve(arr, emptySquares, squares);
    			if (sol.length == 81) {
    				return sol;
    			} else {
    				arr = storedArr;
    				emptySquares = storedES;
    				squares = storedSquares;
    				squares[emptySquares.get(0)].getPosNums().remove(0);
    			}
    		}
    	}
		return arr;
    	
    }
    
    public void updateSquares(int index, Square[] sqs) {
    	//for all unfilled squares in row, col, and bigsquare --> remove # of given index from posnums
    	//row
    	for (int i = index/9*9; i < index/9*9+9; i++) {
    		if (!sqs[i].isFilled()) {
    			sqs[i].rmFromPosNums(sqs[index].getValue());
    		}
    	}
    	//col
    	for (int i = index % 9; i < 81; i += 9) {
    		if (!sqs[i].isFilled()) {
    			sqs[i].rmFromPosNums(sqs[index].getValue());
    		}
    	}
    	//bsquare
    	int start = index - (((index / 9) % 3) * 9) - (index % 3);
    	int[] nums = {start, start + 1, start + 2, start + 9, start + 10, start + 11, start + 18, start + 19, start + 20};
    	for (int i : nums) {
    		if (!sqs[i].isFilled()) {
    			sqs[i].rmFromPosNums(sqs[index].getValue());
    		}
    	}
    }
}

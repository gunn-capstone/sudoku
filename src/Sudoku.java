import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku {
	int[] startingVals;
	int sudokuSize, boxSize, gridSize;

	public Sudoku(int size, int[] startingVals) {
		this.sudokuSize = size; // sudoku size, default 3
		this.boxSize = size * size; // size of box, default 9
		this.gridSize = boxSize * boxSize; // size of whole grid, default 81
		this.startingVals = startingVals;
	}

	public int[] solve() {
		int[] grid = startingVals; // store value of all cells
		ArrayList<Integer> emptyCells = (ArrayList<Integer>) IntStream.rangeClosed(0, gridSize - 1).boxed()
				.collect(Collectors.toList()); // store indices of empty cells
		Cell[] cells = new Cell[gridSize]; // initialize cells

		for (int i = 0; i < gridSize; i++) {
			if (grid[i] == 0)
				cells[i] = new Cell(i);
			else
				cells[i] = new Cell(grid[i], i);
		}

		Cell current;
		for (int i = 0; i < gridSize; i++) {
			current = cells[i];
			if (current.isFilled()) {
				grid[i] = current.getValue();
				updateCells(i, cells);
				emptyCells.remove(Integer.valueOf(i));
			}
		}
		return solve(grid, emptyCells, cells);
	}

	public int[] solve(int[] grid, ArrayList<Integer> emptyCells, Cell[] cells) { // recursive while remaining cells
		Cell current;
		while (emptyCells.size() > 0) {
			boolean same = true;
			for (int i = 0; i < cells.length; i++) {
				current = cells[i];
				if (!current.isFilled() && current.getPosNums().size() == 1) {
					current.fill();
					grid[i] = current.getValue();
					updateCells(i, cells);
					emptyCells.remove(Integer.valueOf(i));
					same = false;
				} else if (current.isFilled() && current.getValue() == 0) {
					int[] err = {};
					System.out.println("Wrong guess");
					return err;
				}
			}
			if (same) {
				System.out.println("Made a guess for " + emptyCells.get(0));
				int[] storedArr = grid.clone();
				ArrayList<Integer> storedES = (ArrayList<Integer>) emptyCells.clone();
				Cell[] storedCells = new Cell[gridSize];
				for (int i = 0; i < gridSize; i++) {
					storedCells[i] = new Cell(cells[i].getValue(), cells[i].getIndex(),
							(ArrayList<Integer>) cells[i].getPosNums().clone());
				}
				cells[emptyCells.get(0)].fill();
				grid[emptyCells.get(0)] = cells[emptyCells.get(0)].getValue();
				updateCells(emptyCells.get(0), cells);
				emptyCells.remove(0);
				int[] sol = solve(grid, emptyCells, cells);
				if (sol.length == gridSize) {
					return sol;
				} else {
					grid = storedArr;
					emptyCells = storedES;
					cells = storedCells;
					cells[emptyCells.get(0)].getPosNums().remove(0);
				}
			}
		}
		return grid;
	}

	public void updateCells(int index, Cell[] sqs) {
		// for all unfilled cells in row, col, and bigCell --> remove # of given
		// index from posnums
		for (int i = index / boxSize * boxSize; i < index / boxSize * boxSize + boxSize; i++) { // row
			if (!sqs[i].isFilled())
				sqs[i].rmFromPosNums(sqs[index].getValue());
		}
		for (int i = index % boxSize; i < gridSize; i += boxSize) { // col
			if (!sqs[i].isFilled())
				sqs[i].rmFromPosNums(sqs[index].getValue());
		}
		int start = index - (((index / boxSize) % sudokuSize) * boxSize) - (index % sudokuSize); // bsquare
		int[] nums = { start, start + 1, start + 2, start + 9, start + 10, start + 11, start + 18, start + 19,
				start + 20 };
		for (int i : nums) {
			if (!sqs[i].isFilled())
				sqs[i].rmFromPosNums(sqs[index].getValue());
		}
	}
}

import java.util.ArrayList;

public class Square {
	
	int value;
	int index;
	ArrayList<Integer> posNums = new ArrayList<Integer>();
	
	public Square(int value, int index){
		this.value = value;
		this.index = index;
	}
	
	public Square(int index) {
		this.index = index;
		for (int i = 1; i <= 9; i++) {
			posNums.add(i);
		}
	}
	
	public Square(int value, int index, ArrayList<Integer> posNums){
		this.value = value;
		this.index = index;
		this.posNums = posNums;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getIndex() {
		return index;
	}

	public ArrayList<Integer> getPosNums() {
		return posNums;
	}
	
	public void rmFromPosNums(int n) {
		posNums.remove(Integer.valueOf(n));
	}
	
	public boolean isFilled() {
		return posNums.size() == 0;
	}
	
	public void fill() {
		value = posNums.get(0);
		posNums.clear();
	}
}

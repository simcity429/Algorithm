package assignment01;

public class Cell {
	private int id;
	private int row;
	private int col;
	private int status;
	private int mine;	//-1 : don't know yet 0: doesn't exist 1: exist
	private int ans;	//answer, -2: can't be known -1: don't know yet 0: doesn't exist 1: exist
	
	public Cell(int row, int col, int id, int status) {
		this.row = row;
		this.col = col;
		this.id = id;
		this.status = status;
		this.mine = 0;
		this.ans = -1;
	}
	
	public void printClass() {
		System.out.println(this.row + " " + this.col + " " + this.id + " " + this.status + " " + this.mine + " " + this.ans);
	}
	
	public void simplePrint() {
		System.out.println("id " + id + " mine: " + mine + " ans: " + ans);
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public int getMine() {
		return this.mine;
	}
	
	public int getAnswer() {
		return this.ans;
	}
	
	public void setMine(int mine) {
		this.mine = mine;
	}
	
	public void setAnswer() {
		if (ans == -1) {
			ans = mine;
		} else {
			if (ans != mine && ans != -1) {
				ans = -2;
			}
		}
	}
	public boolean check(int id, int maxId) {
		if (id >= 0 && id <= maxId) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean satisfy(int rowSize, int colSize, Cell[] cellList) {
		if (status < 0) {
			return true;
		}
		int mineNum = 0;
		int possible = 0;
		Cell[] adj = getAdjacentCell(rowSize, colSize, cellList);
		for (Cell c : adj) {
			if (c==null) {
				continue;
			}
			if (c.getMine() == -1) {
				possible++;
			} else if (c.getMine() == 1) {
				mineNum++;
			}
		}
		return mineNum <= status && mineNum + possible >= status;
	}
	
	public Cell[] getAdjacentCell(int rowSize, int colSize, Cell[] cellList) {
		int tmp = 0;
		int maxId = rowSize*colSize -1;
		Cell[] ret = new Cell[8];
		tmp = id - colSize - 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) - 1) {
			ret[0] = cellList[tmp];
		}
		tmp = id - colSize;
		if (check(tmp, maxId)&&tmp % colSize == id % colSize) {
			ret[1] = cellList[tmp];
		}
		tmp = id - colSize + 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) + 1) {
			ret[2] = cellList[tmp];
		}
		tmp = id - 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) - 1) {
			ret[3] = cellList[tmp];
		}
		tmp = id + 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) + 1) {
			ret[4] = cellList[tmp];
		}
		tmp = id + colSize - 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) - 1) {
			ret[5] = cellList[tmp];
		}
		tmp = id + colSize;
		if (check(tmp, maxId)&&tmp % colSize == id % colSize) {
			ret[6] = cellList[tmp];
		}
		tmp = id + colSize + 1;
		if (check(tmp, maxId)&&tmp % colSize == (id % colSize) + 1) {
			ret[7] = cellList[tmp];
		}
		return ret;
	}

}

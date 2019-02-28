package assignment01;

import java.io.*;
import java.util.*;

public class Main {
	static Deque<Cell> rd = new ArrayDeque<>();
	static Deque<Cell> dd = new ArrayDeque<>();
	
	public static boolean satisfyCheck(int rowSize, int colSize, Cell[] cellList) {
		boolean flag;
		for (Cell c : cellList) {
			flag = c.satisfy(rowSize, colSize, cellList);
			if (!flag) {
				return false;
			}
		}
		return true;
	}
	

	
	public static void f(int rowSize, int colSize, Cell[] cellList) {
		Cell c = rd.pollLast();
		if (c==null) {
			for (Cell tmp : dd) {
				tmp.setAnswer();
			}
			return;
		}
		dd.offerLast(c);
		c.setMine(0);
		if(satisfyCheck(rowSize, colSize, cellList)) {
			f(rowSize, colSize, cellList);
		}
		c.setMine(1);
		if(satisfyCheck(rowSize, colSize, cellList)) {
			f(rowSize, colSize, cellList);
		}
		c.setMine(-1);
		dd.pollLast();
		rd.offerLast(c);
		return;
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		int testCase;
		int idx = 1;
		BufferedReader br = new BufferedReader(new FileReader("C:\\hw1\\input.txt"));
		PrintWriter pw = new PrintWriter("C:\\hw1\\2014147550.txt");
		testCase = Integer.parseInt(br.readLine());
		while (testCase > 0) {
			int row, col;
			int size;
			int id;
			int status;
			int clIdx = 0;
			int tIdx = 0;
			String[] str = br.readLine().split(" ");
			row = Integer.parseInt(str[0]);
			col = Integer.parseInt(str[1]);
			size = row*col;
			Cell[] cellList = new Cell[size];
			Cell[] targetCell = new Cell[size];
			for (int i=0; i < row; i++) {
				str = br.readLine().split(" ");
				for (int j=0; j < col; j++) {
					id = i*col + j;
					status = Integer.parseInt(str[j]);
					Cell newCell = new Cell(i, j, id, status);
					cellList[clIdx] = newCell;
					if (status == -1) {
						targetCell[tIdx] = newCell;
						tIdx++;
					}
					clIdx++;
				}
			}
			for (int i=0; i<size; i++) {
				if (targetCell[i] == null) {
					continue;
				}
				targetCell[i].setMine(-1);
				Cell[] adj = targetCell[i].getAdjacentCell(row, col, cellList);
				boolean flag = false;
				for (Cell c : adj) {
					if (c == null) {
						continue;
					}
					if (c.getStatus() >= 0) {
						flag = true;
					}
				}
				if (!flag) {
					targetCell[i].setMine(0);
					targetCell[i] = null;
				}
			}
			for (Cell c : targetCell) {
				if (c==null) {
					continue;
				}
				rd.offerLast(c);
			}
			f(row, col, cellList);
			
			List<Cell> mineExist = new ArrayList<>();
			List<Cell> mineNotExist = new ArrayList<>();
			
			
			for (Cell c : rd) {
				int ans = c.getAnswer();
				if (ans == 1) {
					mineExist.add(c);
				} else if (ans == 0) {
					mineNotExist.add(c);
				}
			}
//			System.out.println("#"+idx);
			pw.println("#"+idx);
			for (Cell c : mineExist) {
//				System.out.print(c.getRow() + " " + c.getCol() + " ");
				pw.print(c.getRow() + " " + c.getCol() + " ");
			}
//			System.out.println();
			pw.println();
			for (Cell c : mineNotExist) {
//				System.out.print(c.getRow() + " " + c.getCol() + " ");
				pw.print(c.getRow() + " " + c.getCol() + " ");
			}
//			System.out.println();
			pw.println();
			idx++;
			rd.clear();
			dd.clear();
			testCase--;
		}
		pw.close();
		br.close();
	}
	
}

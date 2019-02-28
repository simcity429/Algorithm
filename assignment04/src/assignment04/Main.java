package assignment04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.LinkedList;


public class Main {
	
	public static int[][] c;
	public static int[][] f;
	
	public static int residual(int here, int there) {
		return c[here][there] - f[here][there];
	}
	
	public static void printArr(int size, int[][] arr) {
		for(int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("- - - - - - - - - - ");
	}
	
	public static void printResArr(int size) {
		System.out.println("printing residual");
		for(int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				System.out.print(residual(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("- - - - - - - - - - ");
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int testCase;
		int idx = 1;
		BufferedReader br = new BufferedReader(new FileReader("C:\\hw4\\input.txt"));
		PrintWriter pw = new PrintWriter("C:\\hw4\\2014147550.txt");
		testCase = Integer.parseInt(br.readLine());
		while(testCase > 0) {
			int total = 0;
			String readStr = br.readLine();
			int nodeNum = Integer.parseInt(readStr.split(" ")[0]);
			int source = Integer.parseInt(readStr.split(" ")[1]);
			int sink = Integer.parseInt(readStr.split(" ")[2]);
			c= new int[nodeNum][nodeNum];
			f = new int[nodeNum][nodeNum];
			for (int i=0;i<nodeNum;i++) {

				String[] strArr = br.readLine().split(" ");
				for (int j=0; j<nodeNum;j++) {
					c[i][j] = Integer.parseInt(strArr[j]);
				}
			}
			for (int i=0; i<nodeNum;i++) {
				for (int j=0;j<nodeNum;j++) {
					f[i][j] = 0;
				}
			}
			while (true) {
				int parent[] = new int[nodeNum];
				for (int i=0;i<nodeNum;i++) {
					parent[i] = -1;
				}
				Queue<Integer> Q = new LinkedList<>();
				Q.offer(source);
				while (!Q.isEmpty() && parent[sink] == -1) {
					int here = Q.poll();
					for (int i=0;i<nodeNum;i++) {
						if (residual(here, i) > 0 && parent[i] == -1) {
							parent[i] = here;
							Q.offer(i);
						}
					}
				}
				if (parent[sink] == -1) {
					break;
				}
				int flow = 10000;
				for (int i=sink;i!=source;i=parent[i]) {
					flow = flow > residual(parent[i], i) ? residual(parent[i], i) : flow;
				}
				for (int i=sink;i!=source;i=parent[i]) {
					f[parent[i]][i] += flow;
					f[i][parent[i]] -= flow;
				}
				
				total += flow;
			}
			String printStr = "#" + Integer.toString(idx) + " " + Integer.toString(total);
			pw.println(printStr);
			idx++;
			testCase--;
		}
		br.close();
		pw.close();
	}
}

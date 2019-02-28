package assignment02;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {
	static int babyMedian;
	public static int miniMedian(int[] miniArr) {
		int length = miniArr.length;
		Arrays.sort(miniArr);
		if (length % 2 == 1) {
			return miniArr[length/2];
		} else {
			return miniArr[(length/2)-1];
		}
	}
	
	public static int[] partition(int[] arr, int pivot) {
		ArrayList<Integer> small = new ArrayList<>(500);
		ArrayList<Integer> big = new ArrayList<>(500);
		ArrayList<Integer> same = new ArrayList<>();
		int idx = 0;
		int s_idx = -1;
		int e_idx = -1;
		int smallSize = 0, bigSize = 0;
		int[] retArr = new int[4];
		for (int i : arr) {
			if (i == pivot) {
				same.add(i);
			} else if (i < pivot) {
				small.add(i);
			} else {
				big.add(i);
			}
		}
		for (int i : small) {
			arr[idx] = i;
			idx++;
			smallSize++;
		}
		for (int i : same) {
			arr[idx] = i;
			if (s_idx == -1) {
				s_idx = idx;
				e_idx = idx;
			} else {
				e_idx = idx;
			}
			idx++;
		}
		for (int i : big) {
			arr[idx] = i;
			idx++;
			bigSize++;
		}
		retArr[0] = smallSize;
		retArr[1] = bigSize;
		retArr[2] = s_idx;
		retArr[3] = e_idx;
		return retArr;
	}
	
	public static int Det_Select(int[] arr, int k, int a, int flag) {
		if (arr.length <= a) {
			Arrays.sort(arr);
			if (flag == 1) {
				babyMedian = miniMedian(arr);
			}
			return arr[k-1];
		}
		int arrLength = arr.length;
		int groupNum = arrLength/a;
		int[] miniArr = new int[a];
		int[] babyArr;
		if (arr.length % a == 0) {
			babyArr = new int[groupNum];
		} else {
			babyArr = new int[groupNum + 1];
		}
		int miniIdx = 0;
		int babyIdx = 0;
		for(int i : arr) {
			miniArr[miniIdx] = i;
			miniIdx++;
			if (miniIdx == a) {
				babyArr[babyIdx] = miniMedian(miniArr);
				babyIdx++;
				miniIdx = 0;
			}
		}
		if (arr.length % a != 0) {
			miniIdx = 0;
			miniArr = new int[arr.length % a];
			for (int i = arr.length-1;miniIdx < arr.length % a;i--) {
				miniArr[miniIdx] = arr[i];
				miniIdx++;
			}
			babyArr[babyIdx] = miniMedian(miniArr);
		}
		int median;
		if (babyArr.length % 2 == 0) {
			median = Det_Select(babyArr, babyArr.length/2, a, 0);
		} else {
			median = Det_Select(babyArr, babyArr.length/2 + 1, a, 0);
		}
		if (flag == 1) {
			babyMedian = median;
		}
		int[] partArr = partition(arr, median);
		int smallSize = partArr[0];
		int bigSize = partArr[1];
		int rank = partArr[2] + 1;
		int e_idx = partArr[3];
		if (k == rank) {
			return median;
		} else if (k < rank) {
			int[] tmpArr = new int[smallSize];
			System.arraycopy(arr, 0, tmpArr, 0, smallSize);
			return Det_Select(tmpArr, k, a, 0);
		} else if (k > rank && k <= e_idx+1) {
			return median;
		} else {
			int[] tmpArr = new int[bigSize];
			System.arraycopy(arr, e_idx+1, tmpArr, 0, bigSize);
			return Det_Select(tmpArr, k - (e_idx + 1), a, 0);
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		int testCase;
		int idx = 1;
		BufferedReader br = new BufferedReader(new FileReader("C:\\hw2\\input.txt"));
		PrintWriter pw = new PrintWriter("C:\\hw2\\2014147550.txt");
		testCase = Integer.parseInt(br.readLine());
		while (testCase > 0) {
			String[] str = br.readLine().split(" ");
			int k = Integer.parseInt(str[0]);
			int n = Integer.parseInt(str[1]);
			int a = Integer.parseInt(str[2]);
			str = br.readLine().split(" ");
			int[] arr = new int[n];
			int tmp = 0;
			for (String s : str) {
				arr[tmp] = Integer.parseInt(s);
				tmp++;
			}
			pw.println("#" + idx);
			pw.print(Det_Select(arr, k, a, 1) + " ");
			pw.println(babyMedian);
			idx++;
			testCase--;
		}
		br.close();
		pw.close();
	}
}

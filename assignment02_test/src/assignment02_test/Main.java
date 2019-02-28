package assignment02_test;


import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static int babyMedian;
	public static void generate() throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter("C:\\hw2\\input.txt");
		Random r = new Random();
		System.out.println("testCase: ");
		int testCase = sc.nextInt();
		pw.println(testCase);
		while (testCase > 0) {
			System.out.println("ith: ");
			int k = sc.nextInt();
			System.out.println("n: ");
			int n = sc.nextInt();
			System.out.println("a: ");
			int a = sc.nextInt();
			int[] arr = new int[n];
			for (int i = 0; i<arr.length;i++) {
				arr[i] = r.nextInt(n*10);
			}
			String str = Integer.toString(k) + " " + Integer.toString(n) + " " + Integer.toString(a);
			pw.println(str);
			StringBuilder sb = new StringBuilder(n*2);
			for (int i : arr) {
				sb.append(Integer.toString(i) + " ");
			}
			sb.setLength(sb.length()-1);
			str = sb.toString();
			pw.println(str);
			testCase--;
		}
		sc.close();
		pw.close();
	}
	public static int randomSelect(int[] arr, Random r) {
		int length = arr.length;
		int ret = arr[r.nextInt(length)];
		return ret;
	}
	
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
	
	public static int Ran_Select(int[] arr, int k, int a) {
		if (arr.length <= a) {
			Arrays.sort(arr);
			return arr[k-1];
		}
		Random r = new Random();
		int pivot = randomSelect(arr, r);
		int[] partArr = partition(arr, pivot);
		int smallSize = partArr[0];
		int bigSize = partArr[1];
		int rank = partArr[2] + 1;//s_idx+1
		int e_idx = partArr[3];
		if (k == rank) {
			return pivot;
		} else if (k < rank) {//lower part iteration
			int[] tmpArr = new int[smallSize];
			System.arraycopy(arr, 0, tmpArr, 0, smallSize);
			return Ran_Select(tmpArr, k, a);
		} else if (k > rank && k <= e_idx+1) {
			return pivot;
		} else {//upper part iteration
			int[] tmpArr = new int[bigSize];
			System.arraycopy(arr, e_idx+1, tmpArr, 0, bigSize);
			return Ran_Select(tmpArr, k - (e_idx + 1), a);
		}
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
		long start = 0, end = 0;
		int testCase;
		int idx = 1;
		generate();//generate input file
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
			start = System.currentTimeMillis();
			System.out.println("#" + idx);
//			System.out.println("Det_Select_answer: " + Det_Select(arr, k, a, 1));
//			System.out.println("babyMedian: " + babyMedian);
			//end = System.currentTimeMillis();
			//System.out.println("Det_Time: " + (end - start));
			//start = System.currentTimeMillis();
			System.out.println("Ran_Select_answer: " + Ran_Select(arr, k, a));
			end = System.currentTimeMillis();
			//System.out.println("Ran_Time: " + (end - start));
			idx++;
			testCase--;
		}
//		end = System.currentTimeMillis();
		System.out.println("time: " + (end - start));
		br.close();
		pw.close();
	}
}


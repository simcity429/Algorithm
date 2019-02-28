package assignment05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
	public static Long[] arr;
	public static void generateArray(int num) {
		arr = new Long[num];
		List<Long> l = new ArrayList<>(num);
		Random r = new Random();
		long val = r.nextInt(1000000000) + 1;
		for (int i=0;i<num;i++) {
			l.add(val);
			val += r.nextInt(1000000000);
		}
		Collections.shuffle(l);
		for (int i=0;i<num;i++) {
			arr[i] = l.get(i);
		}
	}
	
	public static void generateInitSol_rand(List<Integer> l1, List<Integer> l2) {
		Random r = new Random();
		for (int i=0;i<arr.length;i++) {
			if (r.nextInt(2) == 0) {
				l1.add(i);
			} else {
				l2.add(i);
			}
		}
	}
	
	public static void generateInitSol_half(List<Integer> l1, List<Integer> l2) {
		List<Tmp> tmpList = new ArrayList<Tmp>(arr.length);
		for (int i=0;i<arr.length;i++) {
			tmpList.add(new Tmp(arr[i], i));
		}
		Collections.sort(tmpList);
		int i = 0;
		for (Tmp t : tmpList) {
			if (i%2==0) {
				l1.add(t.index);
			} else {
				l2.add(t.index);
			}
			i++;
		}
	}
	
	public static void printArr(Object[] arr) {
		for (int i=0;i<arr.length;i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void printList(List<? extends Object> l) {
		for (Object i : l) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static long listSum(List<Integer> l) {
		long sum = 0;
		for (int i : l) {
			sum += arr[i];
		}
		return sum;
	}
	
	public static long residue(List<Integer> l1, List<Integer> l2) {
		long l1sum = listSum(l1);
		long l2sum = listSum(l2);
		return Math.abs(l1sum - l2sum);
	}
	
	public static void neighborProcess(List<Integer> l1, List<Integer> l2, int index) {
		if(l1.contains(index)) {
			int tmp = l1.indexOf(index);
			l1.remove(tmp);
			l2.add(index);
		} else {
			int tmp = l2.indexOf(index);
			l2.remove(tmp);
			l1.add(index);
		}
	}
	
	public static void goToNeighbor(List<Integer> l1, List<Integer> l2) {
		Random r = new Random();
		int i1 = r.nextInt(100);
		int i2 = r.nextInt(100);
		if (i1 == i2) {
			neighborProcess(l1, l2, i1);
		} else {
			neighborProcess(l1, l2, i1);
			neighborProcess(l1, l2, i2);
		}
	}
	
	public static List<Integer> copyList(List<Integer> l) {
		List<Integer> ret = new ArrayList<>(100);
		for (Integer i : l) {
			ret.add(i);
		}
		return ret;
	}
	
	public static long repeatedRandom(List<Integer> l1, List<Integer> l2, int iteration) {
		for (int i=0;i<iteration;i++) {
			List<Integer> t1, t2;
			t1 = new ArrayList<>(100);
			t2 = new ArrayList<>(100);
			generateInitSol_rand(t1, t2);
//			generateInitSol_half(t1, t2);
			if (residue(l1, l2) > residue(t1, t2)) {
				l1 = t1;
				l2 = t2;
			}
		}
		return residue(l1, l2);
	}
	
	public static long hillClimbing(List<Integer> l1, List<Integer> l2, int iteration) {
//		int cnt = 0;
		for (int i=0;i<iteration;i++) {
			List<Integer> t1, t2;
			t1 = copyList(l1);
			t2 = copyList(l2);
			goToNeighbor(t1, t2);
			if (residue(l1, l2) > residue(t1, t2)) {
				l1 = t1;
				l2 = t2;
//				cnt++;
			}
		}
//		printList(l1);
//		printList(l2);
//		System.out.println(cnt);
		return residue(l1, l2);
	}
	
	public static double T(int i) {
		return Math.pow(10, 10)*Math.pow(0.99, Math.ceil(i/300));
	}
	
	public static long simulatedAnnealing(List<Integer> l1, List<Integer> l2, int iteration) {
//		int acnt = 0;
//		int bcnt = 0;
		List<Integer> b1, b2;
		b1 = copyList(l1);
		b2 = copyList(l2);
		Random r = new Random();
		for (int i=0;i<iteration;i++){
			List<Integer> t1, t2;
			t1 = copyList(l1);
			t2 = copyList(l2);
			goToNeighbor(t1, t2);
			if (residue(l1, l2) > residue(t1, t2)) {
				l1 = t1;
				l2 = t2;
//				acnt++;
			} else {
				double prob = Math.exp(-((residue(t1, t2)-residue(l1, l2))/T(i)));
				int selectNum = (int) (prob*Math.pow(10, 5));
				int result = r.nextInt((int) Math.pow(10, 5));
				if (result < selectNum) {
					l1 = t1;
					l2 = t2;
//					bcnt++;
				}
			}
			if (residue(b1, b2) > residue(l1, l2)) {
				b1 = copyList(l1);
				b2 = copyList(l2);
			}
		}
//		System.out.println("cnt:" + acnt + " " + bcnt);
		return residue(b1, b2);
	}
	
	public static List<Gene> initPopulation(int num) {
		List<Gene> population = new ArrayList<>(num);
		for (int i=0;i<num;i++) {
			List<Integer> l1, l2;
			l1 = new ArrayList<>(100);
			l2 = new ArrayList<>(100);
			generateInitSol_rand(l1, l2);
			Gene tmp = new Gene(l1, l2, arr.length);
//			tmp.printGene();
			population.add(tmp);
		}
		return population;
	}
	
	public static Gene doTournament(Gene g1, Gene g2, double prob) {
		Gene ans, wrong;
		if (g1.residue > g2.residue) {
			ans = g2;
			wrong = g1;
		} else {
			ans = g1;
			wrong = g2;
		}
		Random r = new Random();
		int border = (int) Math.floor(prob*100000);
		int i = r.nextInt(100000);
		if (i < border) {
			return ans;
		} else {
			return wrong;
		}
	}
	
	public static Gene sex(Gene g1, Gene g2) {
		double mutationProb = 0.1;
		Random r = new Random();
		Gene child = new Gene(arr.length);
		int r1 = r.nextInt(100);
		int r2 = r.nextInt(100);
		if (r1 > r2) {
			int tmp = r2;
			r2 = r1;
			r1 = tmp;
		}
		if (r1 == r2) {
			System.arraycopy(g1.gene, 0, child.gene, 0, child.gene.length);
			System.arraycopy(g2.gene, r1, child.gene, r1, child.gene.length - r1);
		} else {
			System.arraycopy(g1.gene, 0, child.gene, 0, child.gene.length);
			System.arraycopy(g2.gene, r1, child.gene, r1, child.gene.length - r1);
			System.arraycopy(g1.gene, r2, child.gene, r2, child.gene.length - r2);
		}
		boolean mutation = false;
		if (r.nextInt(100) < mutationProb * 100) {
			mutation = true;
		}
		while(mutation) {
			int index = r.nextInt(100);
			if (child.gene[index] == 0) {
				child.gene[index] = 1;
			} else {
				child.gene[index] = 0;
			}
			if (r.nextInt(100) > mutationProb * 100) {
				mutation = false;
			}
		}
		List<Integer> l1, l2;
		l1 = new ArrayList<>(arr.length);
		l2 = new ArrayList<>(arr.length);
		child.geneToList(l1, l2);
		child.residue = residue(l1, l2);
		child.printGene();
		return child;
	}
	
	public static Gene f(List<Gene> pop) {
		List<Gene> l1, l2, tmp;
		l1 = new ArrayList<>(pop.size());
		l2 = new ArrayList<>(pop.size());
		for (Gene g : pop) {
			l1.add(g);
		}
		while(true) {
			while(!l1.isEmpty()) {
				Gene g1 = l1.remove(0);
				Gene g2 = l1.remove(0);
				l2.add(doTournament(g1, g2, 0.7));
			}
			tmp = l1;
			l1 = l2;
			l2 = tmp;
			if (l1.size() == 2) {
				break;
			}
		}
		return sex(l1.remove(0), l1.remove(0));
	}
	
	public static long genetics() {
		int num = 2048;
		List<Gene> population = initPopulation(num);
		List<Gene> child = new ArrayList<>(num);
		while (true) {
			Collections.sort(population);
			long min = population.get(0).residue;
			int cnt = 0;
			for (int j=0;j<num;j++) {
				if (population.get(j).residue == min) {
					cnt++;
				}
			}
			if (cnt > num*0.5) {
				break;
			}
			for (int j=0;j<num*0.5;j++) {
				child.add(f(population));
			}
			for (int j=0;j<num*0.5;j++) {
				population.remove(population.size()-1);
			}
			for (Gene g : child) {
				population.add(g);
			}
			child.clear();
		}
		Collections.sort(population);
		return population.get(0).residue;
	}
	

	public static void main(String[] args) {
		int caseNum = 50;
		int g=0, r=0, h=0, a=0;
		long gt=0, rt=0, ht=0, at=0;
		for (int i=0;i<caseNum;i++) {
			int iter = 250000;
			generateArray(100);
			List<Integer> l1, l2;
			l1 = new ArrayList<>(100);
			l2 = new ArrayList<>(100);
			generateInitSol_rand(l1, l2);
	//		generateInitSol_half(l1, l2);
			long start = System.currentTimeMillis();
			long geneticsResult = genetics();
			long elapsedTimeMillis = System.currentTimeMillis()-start;
			gt += elapsedTimeMillis;
			List<Integer> t1, t2;
			t1 = copyList(l1);
			t2 = copyList(l2);
			start = System.currentTimeMillis();
			long repeatedResult = repeatedRandom(t1, t2, iter);
			elapsedTimeMillis = System.currentTimeMillis()-start;
			rt += elapsedTimeMillis;
			t1 = copyList(l1);
			t2 = copyList(l2);
			start = System.currentTimeMillis();
			long hillResult = hillClimbing(t1, t2, iter);
			elapsedTimeMillis = System.currentTimeMillis()-start;
			ht += elapsedTimeMillis;
			t1 = copyList(l1);
			t2 = copyList(l2);
			start = System.currentTimeMillis();
			long saResult = simulatedAnnealing(t1, t2, iter);
			elapsedTimeMillis = System.currentTimeMillis()-start;
			at += elapsedTimeMillis;
			System.out.println("#" + i);
			System.out.println("genetics: " + geneticsResult);
			System.out.println("repeatedRandom: " + repeatedResult);
			System.out.println("hillClimbing: " + hillResult);
			System.out.println("saResult: " + saResult);
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
			long min1 = geneticsResult > repeatedResult ? repeatedResult : geneticsResult;
			long min2 = hillResult > saResult ? saResult : hillResult;
			long min = min1 > min2 ? min2 : min1;
			if (min == geneticsResult) {
				g++;
			} else if (min == repeatedResult) {
				r++;
			} else if (min == hillResult) {
				h++;
			} else if (min == saResult) {
				a++;
			}
		}
		System.out.println("g, r, h, a: " + g + " " + r + " " + h + " " + a);
		System.out.println("gt, rt, ht, at: " + gt + " " + rt + " " + ht + " " + at);
	}
}

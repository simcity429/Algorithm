package assignment05;

import java.util.List;

public class Gene implements Comparable<Gene> {
	public Byte[] gene;
	public Long residue;
	
	public Gene(int num) {
		gene = new Byte[num];
	}
	
	public Gene(List<Integer> l1, List<Integer> l2, int num) {
		gene = new Byte[num];
		listToGene(l1, l2);
		residue = Main.residue(l1, l2);
	}
	public void listToGene(List<Integer> l1, List<Integer> l2) {
		for (int i : l1) {
			gene[i] = 0;
		}
		for (int i : l2) {
			gene[i] = 1;
		}
	}
	public void geneToList(List<Integer> l1, List<Integer> l2) {
		for (int i=0;i<gene.length;i++) {
			if(gene[i] == 0) {
				l1.add(i);
			} else {
				l2.add(i);
			}
		}
	}
	
	public void printGene() {
		for (byte i : gene) {
			System.out.print(i + " ");
		}
		System.out.println();
//		System.out.println("residue: " + residue);
	}
	

	public int compareTo(Gene o) {
		// TODO Auto-generated method stub
		return residue.compareTo(o.residue);
	}
}

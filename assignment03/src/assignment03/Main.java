package assignment03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Main {
	public static int maxrank = 0;
	
	public static boolean canAdd(Edge e, VertexSet[] vset) {
		int[] vertexId = e.getVertex();
		VertexSet p1 = vset[vertexId[0]].find();
		VertexSet p2 = vset[vertexId[1]].find();
		if (p1.getId() != p2.getId()) {
			int rank = p1.union(p2);
			if (rank > maxrank) {
				maxrank = rank;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int testCase;
		int idx = 1;
		BufferedReader br = new BufferedReader(new FileReader("C:\\hw3\\input.txt"));
		PrintWriter pw = new PrintWriter("C:\\hw3\\2014147550.txt");
		testCase = Integer.parseInt(br.readLine());
		while (testCase > 0) {
			int size = Integer.parseInt(br.readLine());
			int id = 0;
			int edgeSize = size*(size-1)*2;
			Edge[] edge = new Edge[edgeSize];
			String[] readStr;
			Vertex [][] vertexArr = new Vertex[size][size];
			for (int i=0;i<size;i++) {
				readStr = br.readLine().split(" ");
				for (int j=0;j<size;j++) {
					vertexArr[i][j] = new Vertex(Integer.parseInt(readStr[j]), id);
					id++;
				}
			}
			id = 0;
			for (int i=0;i<size;i++) {
				for (int j=0;j<size-1;j++) {
					int value = vertexArr[i][j].getValue() + vertexArr[i][j+1].getValue();
					edge[id] = new Edge(vertexArr[i][j].getId(), vertexArr[i][j+1].getId(), value, id);
					id++;
				}
			}
			for (int i=0;i<size;i++) {
				for (int j=0;j<size-1;j++) {
					int value = vertexArr[j][i].getValue() + vertexArr[j+1][i].getValue();
					edge[id] = new Edge(vertexArr[j][i].getId(), vertexArr[j+1][i].getId(), value, id);
					id++;
				}
			}
			id = 0;
			VertexSet[] vertexSet = new VertexSet[size*size];
			for (int i=0;i<size;i++) {
				for (int j=0;j<size;j++) {
					vertexSet[id] = new VertexSet(vertexArr[i][j], id);
					id++;
				}
			}
			//vertex, edge input processing complete
			PriorityQueue<Edge> eq = new PriorityQueue<>();
			Edge[] mst = new Edge[size*size-1];
			id = 0;
			for (Edge e : edge) {
				eq.add(e);
			}
			
			for (int i=0;i<size*size-1;) {
				Edge e = eq.poll();
				if (canAdd(e, vertexSet)) {
					mst[id] = e;
					id++;
					i++;
				}
			}
			
			long sum = 0;
			for (Edge e : mst) {
//				System.out.println(e.getValue());
				sum += e.getValue();
			}
			pw.println(("#"+ idx + " " + sum + " " + maxrank));
			maxrank = 0;
			idx++;
			testCase--;
		}
		br.close();
		pw.close();
	}
}

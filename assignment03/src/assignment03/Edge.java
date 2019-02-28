package assignment03;

public class Edge implements Comparable<Edge> {
	private int start;
	private int end;
	private int value;
	private long id;
	
	public Edge(int start, int end, int value, int id) {
		this.start = start;
		this.end = end;
		this.value = value;
		this.id = id;
	}
	
	public int[] getVertex() {
		int[] ret = new int[2];
		ret[0] = start;
		ret[1] = end;
		return ret;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public long getId() {
		return this.id;
	}
	

	@Override
	public int compareTo(Edge target) {
		// TODO Auto-generated method stub
		if (this.value > target.value) {
			return 1;
		} else if (this.value < target.value) {
			return -1;
		} else {
			return 0;
		}
	}


	
}

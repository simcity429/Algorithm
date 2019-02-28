package assignment03;

public class VertexSet {
	private Vertex me;
	private VertexSet parent;
	private int id;
	private int rank;
	
	public VertexSet(Vertex me, int id) {
		this.id = id;
		this.me = me;
		this.parent = null;
		this.rank = 0;
	}
	
	public VertexSet setParent(VertexSet parent) {
		this.parent = parent;
		return this.parent;
	}
	
	public Vertex getVertex() {
		return this.me;
	}
	
	public void rankInc() {
		this.rank++;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int getId() {
		return this.id;
	}
	
	public VertexSet find() {
		if (this.parent != null) {
			return setParent(this.parent.find());
		} else {
			return this;
		}
	}
	
	public int union(VertexSet target) {
		if (this.rank > target.getRank()) {
			target.setParent(this);
			return this.rank;
		} else if (this.rank < target.getRank()) {
			this.setParent(target);
			return target.getRank();
		} else {
			target.setParent(this);
			rankInc();
			return this.rank;
		}
	}
}

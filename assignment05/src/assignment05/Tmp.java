package assignment05;

public class Tmp implements Comparable<Tmp> {
	public Long value;
	public Integer index;
	
	Tmp(Long value, Integer index){
		this.value = value;
		this.index = index;
	}
	@Override
	public int compareTo(Tmp o) {
		// TODO Auto-generated method stub
		return value.compareTo(o.value);
	}
}

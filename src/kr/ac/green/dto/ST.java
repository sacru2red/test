package kr.ac.green.dto;

public class ST extends Striker {
	public ST(int status) {
		super(status);
	}
	
	public ST() {			}
	
	@Override
	public int getStatus() {
		return super.getStatus();
	}
	
	public static void main(String[] args) {
		ST s = new ST();
		System.out.println(s.getClass().getName());
	}
}

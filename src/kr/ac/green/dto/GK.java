package kr.ac.green.dto;

public class GK implements Position {
	private int status;

	public GK(int status) {
		this.status = status;
	}
	public GK() {
		
	}
	@Override
	public int getStatus() {
		return status;
	}
	@Override
	public String getPositionName() {
		String name = this.getClass().getName();
		return name.substring(name.lastIndexOf(".")+1);
	}
	@Override
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GK [status=" + status + ", toString()=" + super.toString()
				+ "]";
	}
}
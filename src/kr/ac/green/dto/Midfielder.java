package kr.ac.green.dto;

public abstract class Midfielder implements Position{
	private int status;
	
	public Midfielder(int status) {
		this.status = status;
	}
	public Midfielder() {		}
	
	@Override
	public String getPositionName() {
		String name = this.getClass().getName();
		return name.substring(name.lastIndexOf(".")+1);
	}
	
	@Override
	public int getStatus() {
		return status;
	}
	
	@Override
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Midfielder [status=" + status + ", getClass()=" + getClass()
				+ "]";
	}
}

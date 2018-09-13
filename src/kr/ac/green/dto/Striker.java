package kr.ac.green.dto;

public abstract class Striker implements Position {
	private int status;
	
	public Striker(int status) {
		this.status = status;
	}
	
	public Striker() {		}
	
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
		return "Striker [status=" + status + ", getClass()=" + getClass() + "]";
	}
}
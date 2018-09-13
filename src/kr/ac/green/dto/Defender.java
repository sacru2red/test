package kr.ac.green.dto;

public abstract class Defender implements Position {
	private int status;
	
	public Defender(int status) {
		this.status = status;
	}
	public Defender() {		}
	
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
		return "Defender [status=" + status + ", getClass()=" + getClass()
				+ "]";
	}
}
package model;

public class Flower {
	private String name;
	private String path;
	private String rev;
	private String ease;
	private String inst;
	
	public Flower(){}
	
	public Flower(String name, String path, String rev, String ease, String inst) {
		this.name = name;
		this.path = path;
		this.rev = rev;
		this.ease = ease;
		this.inst = inst;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getRev() {
		return rev;
	}
	
	public String getEase() {
		return ease;
	}
	
	public String getInst() {
		return inst;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setRev(String rev) {
		this.rev = rev;
	}
	
	public void setEase(String ease) {
		this.ease = ease;
	}
	
	public void setInst(String inst) {
		this.inst = inst;
	}
}

package main;
public class ReservationStation {
	private String type;
	private boolean busy;
	private String op;
	private  int vj;
	private int vk;
	private int qj;
	
	private int qk;
	private int address;
	private int dest;
	private int result;
	private Instruction instruction;
	public ReservationStation(){
		busy = false;
	}
	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public int getVj() {
		return vj;
	}
	public void setVj(int vj) {
		this.vj = vj;
	}
	public int getVk() {
		return vk;
	}
	public void setVk(int vk) {
		this.vk = vk;
	}
	public int getQj() {
		return qj;
	}
	public void setQj(int qj) {
		this.qj = qj;
	}
	public int getQk() {
		return qk;
	}
	public void setQk(int qk) {
		this.qk = qk;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Instruction getInstruction() {
		return instruction;
	}
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

}

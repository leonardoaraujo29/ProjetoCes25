package main;
public class ReorderBuffer {
	private boolean busy;
	private int dest;
	private int value;
	private boolean ready;
	private Instruction instruction;
	private int Address;
	private boolean prediction;
	
	public ReorderBuffer(){
		busy = false;
	}
	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public Instruction getInstruction() {
		return instruction;
	}
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	public int getAddress(){ return Address; }
	public void setAddress(int address){ this.Address = address; }
	public boolean isPrediction() {
		return prediction;
	}
	public void setPrediction(boolean prediction) {
		this.prediction = prediction;
	}
}

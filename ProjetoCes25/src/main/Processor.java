package main;
import java.util.ArrayList;
import java.util.List;

public class Processor {
	private Prediction prediction;
	private int clock;
	public int getClock() {
		return clock;
	}
	private List<Instruction> instructions;
	private Tomasulo tomasulo;
	InstructionReader instructionReader;
	private int pc = 0;
	public int getPc() {
		return pc;
	}
	private int numInst = 0;
	private double cpi;
	private int nextToCommit = 0;
	public Processor(){
		prediction = new Prediction();
		tomasulo = new Tomasulo();
		instructions = new ArrayList<Instruction>();
		instructionReader = new InstructionReader();
		instructionReader.readFile();
		instructions = instructionReader.getInstructions();
		clock = 0;
	
	}
	public void run(){
		boolean hasCommitted = false;
		for(clock = 0;clock<30;clock++){
			int lastIssued = instructions.size();
			//pegar as instruções issue
			for(int i = 0;i<instructions.size();i++){
				if(instructions.get(i).get_status() == "Issue"){
					tomasulo.Issue(instructions.get(i));
					if(instructions.get(i).get_status() == "Execute" && instructions.get(i).get_op().substring(0, 1) == "B"){
						int index;
						if(prediction.V1()){
							tomasulo.setROBprediction(instructions.get(i), true);
							index = instructions.get(i).get_immediate()/4;
							int aux = i+1;
							for(int j = index;j<= i;j++){
								instructions.add(aux, instructions.get(j));
								aux++;
							}
						}
					}
					lastIssued = i;
					break;
				}
				
				
			}
			//pegar as instruções execute e write
			boolean hasWriten = false;
			for(int i = 0;i<lastIssued;i++){
				if(instructions.get(i).get_status() == "Execute"){
					tomasulo.Execute(instructions.get(i));
				}
				if(instructions.get(i).get_status() == "Write" && !hasWriten){
					tomasulo.Write(instructions.get(i));
				}
			}
			//pegar as instruções commit
			for(int i = 0;i<instructions.size();i++){
				if(instructions.get(i).get_status() == "Commit"){
					if(!instructions.get(i).isHasWriten() && i == nextToCommit && !hasCommitted){
						int value = tomasulo.Commit(instructions.get(i));
						if(value == 0 && instructions.get(i).get_status() == "Done" && instructions.get(i).get_op().substring(0,1) == "B"){
							for(int j = i +1;;j++){
								Instruction instructionaux = instructions.get(j);
								instructions.remove(j);
								if(instructionaux.get_op() == instructions.get(i).get_op())
									break;
							}
						}
						else if(value == 1 && instructions.get(i).get_status() == "Done" && instructions.get(i).get_op().substring(0,1) == "B"){
							int index = instructions.get(i).get_immediate()/4;
							int aux = i+1;
							for(int j = index;j<= i;j++){
								instructions.add(aux, new Instruction( instructions.get(j).getString(),instructions.get(i).getComment()));
								aux++;
							}
							for(int j = i+1;j< instructions.size();j++){
								instructions.get(j).set_status("Issue");
							}
						}
						if(instructions.get(i).get_status() == "Done"){
							setNumInst(getNumInst() + 1);
							nextToCommit++;
							hasCommitted = true;
						}
							
					}
					else
						instructions.get(i).setHasWriten(false);
				}
			}
		}
	}
	
	public void runStep(){
		boolean hasCommitted = false;
		int lastIssued = instructions.size();
		Register[]  registers = tomasulo.getRegisters();
		int[]  memory = tomasulo.getMemory();
		System.out.println("Clock " + clock + ": Mem[0] = " + memory[0]);
		//pegar as instruções issue
		for(int i = 0;i<instructions.size();i++){
			if(instructions.get(i).get_status() == "Issue"){
				tomasulo.Issue(instructions.get(i));
				if(instructions.get(i).get_status() == "Execute" && instructions.get(i).get_op().substring(0, 1) == "B"){
					int index;
					if(prediction.V1()){
						tomasulo.setROBprediction(instructions.get(i), true);
						index = instructions.get(i).get_immediate()/4;
						int aux = i+1;
						for(int j = index;j<= i;j++){
							instructions.add(aux, new Instruction( instructions.get(j).getString(),instructions.get(i).getComment()));
							aux++;
						}
					}
				}
				lastIssued = i;
				break;
			}
			
			
		}
		//pegar as instruções execute e write
		boolean hasWriten = false;
		for(int i = 0;i<lastIssued;i++){
			if(instructions.get(i).get_status() == "Execute"){
				tomasulo.Execute(instructions.get(i));
			}
			else if(instructions.get(i).get_status() == "Write" && !hasWriten){
				tomasulo.Write(instructions.get(i));
			}
		}
		//pegar as instruções commit
		for(int i = 0;i<instructions.size();i++){
			if(instructions.get(i).get_status() == "Commit"){
				if(!instructions.get(i).isHasWriten() && i == nextToCommit && !hasCommitted){
					int value = tomasulo.Commit(instructions.get(i));
					if(value == 0 && instructions.get(i).get_status() == "Done" && instructions.get(i).get_op().substring(0,1) == "B"){
						for(int j = i +1;;j++){
							Instruction instructionaux = instructions.get(j);
							instructions.remove(j);
							if(instructionaux.get_op() == instructions.get(i).get_op())
								break;
						}
					}
					else if(value == 1 && instructions.get(i).get_status() == "Done" && instructions.get(i).get_op().substring(0,1) == "B"){
						int index = instructions.get(i).get_immediate()/4;
						int aux = i+1;
						for(int j = index;j<= i;j++){
							instructions.add(aux, new Instruction( instructions.get(j).getString(),instructions.get(i).getComment()));
							aux++;
						}
						for(int j = i+1;j< instructions.size();j++){
							instructions.get(j).set_status("Issue");
						}
					}
					if(instructions.get(i).get_status() == "Done"){
						setNumInst(getNumInst() + 1);
						nextToCommit++;
						hasCommitted = true;
					}
						
				}
				else
					instructions.get(i).setHasWriten(false);
			}
		}
		clock++;
		pc = pc+4;
	}
	
	public Register[] getRegisters(){
		return tomasulo.getRegisters();
	}
	
	public ReorderBuffer[] getRob() {
		return tomasulo.getRob();
	}
	
	public ReservationStation[] getRs() {
		return tomasulo.getRs();
	}
	public int getNumInst() {
		return numInst;
	}
	public void setNumInst(int numInst) {
		this.numInst = numInst;
	}
}

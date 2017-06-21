import java.util.ArrayList;
import java.util.List;

public class Tomasulo {

	private List<ReorderBuffer> rob = new ArrayList<ReorderBuffer>();
	private List<ReservationStation> rs = new ArrayList<ReservationStation>();
	
	private int numReorderBuffers = 5;
	private Register[] registers = new Register[32];
	
	private int h;
	public void Issue(Instruction instruction){
		int r = 0;
		int b = 0;
		if(rob.size() < numReorderBuffers){
			if(registers[instruction.get_rs()].isBusy()){
				h = registers[instruction.get_rs()].getReorderNum();
				if(rob.get(h).isReady()){
					rs.get(r).setVj(rob.get(h).getValue());
					rs.get(r).setQj(0);
				}
				else{
					rs.get(r).setQj(h);
				}
				
			}
			else{
				rs.get(r).setVj(registers[instruction.get_rs()].getValue());
				rs.get(r).setQj(0);
			}
			rs.get(r).setBusy(true);
			rs.get(r).setDest(b);
			rob.get(b).setInstruction(instruction);
			rob.get(b).setDest(instruction.get_rd());
			rob.get(b).setReady(false);
		}
		instruction.set_status("Execute");
	}
	
	public void Execute(Instruction instruction){
		
	}
	
	public void Write(Instruction instruction){
		
	}
	
	public void Commit(Instruction instruction){
		
	}
}
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
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi" || instruction.get_op() == "Sw"){
				if(registers[instruction.get_rt()].isBusy()){
					h = registers[instruction.get_rt()].getReorderNum();
					if(rob.get(h).isReady()){
						rs.get(r).setVk(rob.get(h).getValue());
						rs.get(r).setQk(0);
					}
					else{
						rs.get(r).setQk(h);
					}
				}
				else{
					rs.get(r).setVk(registers[instruction.get_rt()].getValue());
					rs.get(r).setQk(0);
				}
			}
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi"){
				registers[instruction.get_rd()].setReorderNum(b);
				registers[instruction.get_rd()].setBusy(true);
				rob.get(b).setDest(instruction.get_rd());
			}
			if(instruction.get_op() == "Lw"){
				rs.get(r).setAddress(instruction.get_immediate());
				registers[instruction.get_rt()].setReorderNum(b);
				registers[instruction.get_rt()].setBusy(true);
				rob.get(b).setDest(instruction.get_rt());
			}
			if(instruction.get_op() == "Sw"){
				rs.get(r).setAddress(instruction.get_immediate());
			}
			instruction.set_status("Execute");
		}
	}
	
	public void Execute(Instruction instruction){
		int r = 0; // MUDAR DEPOIS
		int h = 0; // MUDAR DEPOIS

		String op = instruction.get_op();
		if(op.equals("Add") || op.equals("Sub") || op.equals("Addi") || op.equals("Mul")){
			if(rs.get(r).getQj() == 0 && rs.get(r).getQk() == 0){
				switch(op){
					case "Add":
						//...
						break;
					case "Sub":
						//...
						break;
					case "Addi":
						//...
						break;
					case "Mul":
						//...
						break;
				}
			}
		}
		else if(op.equals("Lw")){
			//FAZER ETAPA 1 E ETAPA 2
		}
		else if(op.equals("Sw")){
			if(rs.get(r).getQj() == 0){
				rob.get(h).setAddress(rs.get(r).getVj()+rs.get(r).getAddress());
			}
		}
	}
	
	public void Write(Instruction instruction){
		
	}
	
	public void Commit(Instruction instruction){
		
	}
}
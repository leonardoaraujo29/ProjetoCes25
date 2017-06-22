import java.util.ArrayList;
import java.util.List;

public class Tomasulo {

	private ReorderBuffer[] rob = new ReorderBuffer[10];
	private ReservationStation[] rs;

	private String[] memoria = new String[4000];
	
	private Register[] registers = new Register[32];
	
	private int h;
	private int b = 0;
	public Tomasulo(){
		rs = new ReservationStation[10];
		for(int i = 0; i <5; i++){
			rs[i].setType("Load/Store");
		}
		for(int i = 5; i <8; i++){
			rs[i].setType("Add");
		}
		for(int i = 8; i <11; i++){
			rs[i].setType("Mult");
		}
		
	}
	public void Issue(Instruction instruction){
		int maxrs = -1;
		int minrs = 0;
		String op = instruction.get_op();
		if(op.equals("Add") || op.equals("Sub") || op.equals("Addi") || op.equals("Mul") || op.equals("Beq") || op.equals("Ble") || op.equals("Bne") || op.equals("Jmp")){
			maxrs = 7;
			minrs = 5;
		}
		else if(op.equals("Mul")){
			maxrs = 10;
			minrs = 8;
		}
		else if(op.equals("Lw") || op.equals("Sw")){
			maxrs = 4;
			minrs = 0;
		}
		int r = -1;
		for (int i = minrs;i<=maxrs;i++){
			if(!rs[i].isBusy())
				r = i;
		}
		if(!rob[b].isBusy() && r != -1){
			if(registers[instruction.get_rs()].isBusy()){
				h = registers[instruction.get_rs()].getReorderNum();
				if(rob[h].isReady()){
					rs[r].setVj(rob[h].getValue());
					rs[r].setQj(0);
				}
				else{
					rs[r].setQj(h);
				}
			}
			else{
				rs[r].setVj(registers[instruction.get_rs()].getValue());
				rs[r].setQj(0);
			}
			rs[r].setBusy(true);
			rs[r].setDest(b);
			rob[b].setInstruction(instruction);
			rob[b].setDest(instruction.get_rd());
			rob[b].setReady(false);
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi" || instruction.get_op() == "Sw" || instruction.get_op() == "Beq" || instruction.get_op() == "Ble" || instruction.get_op() == "Bne"){
				if(registers[instruction.get_rt()].isBusy()){
					h = registers[instruction.get_rt()].getReorderNum();
					if(rob[h].isReady()){
						rs[r].setVk(rob[h].getValue());
						rs[r].setQk(0);
					}
					else{
						rs[r].setQk(h);
					}
				}
				else{
					rs[r].setVk(registers[instruction.get_rt()].getValue());
					rs[r].setQk(0);
				}
			}
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi"){
				registers[instruction.get_rd()].setReorderNum(b);
				registers[instruction.get_rd()].setBusy(true);
				rob[b].setDest(instruction.get_rd());
			}
			if(instruction.get_op() == "Lw"){
				rs[r].setAddress(instruction.get_immediate());
				registers[instruction.get_rt()].setReorderNum(b);
				registers[instruction.get_rt()].setBusy(true);
				rob[b].setDest(instruction.get_rt());
			}
			if(instruction.get_op() == "Sw"){
				rs[r].setAddress(instruction.get_immediate());
			}
			instruction.set_status("Execute");
			instruction.setReservationStation(r);
			b++;
			if(b == 10)
				b = 0;
		}
	}
	
	public void Execute(Instruction instruction){
		int r = instruction.getReservationStation();

		String op = instruction.get_op();
		if(op.equals("Add") || op.equals("Sub") || op.equals("Addi") || op.equals("Mul")){
			if(rs[r].getQj() == 0 && rs[r].getQk() == 0){
				instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
				switch(op){
					case "Add":
						rs[r].setResult(rs[r].getVj() + rs[r].getVk());
						break;
					case "Sub":
						rs[r].setResult(rs[r].getVj() - rs[r].getVk());
						break;
					case "Addi":
						rs[r].setResult(rs[r].getVj() + rs[r].getVk());
						break;
					case "Mul":
						rs[r].setResult(rs[r].getVj() * rs[r].getVk());
						break;
				}
			}
		}
		else if(op.equals("Lw")){
			
			if(instruction.getLoadStep() == 1 && rs[r].getQj() == 0){
				instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
				rs[r].setAddress(rs[r].getVj()+rs[r].getAddress());
				instruction.setLoadStep(2);
				
			}
			//PAREI AQUI
			else if(instruction.getLoadStep() == 2){
				instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
				
			}
		}
		else if(op.equals("Sw")){
			if(rs[r].getQj() == 0){
				rob[h].setAddress(rs[r].getVj()+rs[r].getAddress());
			}
		}
	}
	
	public void Write(Instruction instruction){
		int r = instruction.getReservationStation();
		int b_aux = getROBposition(instruction);
		rs[r].setBusy(false);
		for(int i = 0; i<10; i++){
			if(rs[i].getQj() == b_aux){
				rs[i].setVj(rs[r].getResult());
				rs[i].setQj(0);
			}
			else if(rs[i].getQk() == b_aux){
				rs[i].setVk(rs[r].getResult());
				rs[i].setQk(0);
			}
		}
		rob[b_aux].setValue(rs[r].getResult());
		rob[b_aux].setReady(true);
	}
	
	public void Commit(Instruction instruction){
		String op = instruction.get_op();
		if(op.substring(0,1).equals("B") || op.equals("Jmp")){
			/**
			 * if(ROB[h].Instruction == Branch)
			 * 	{if (branch is mispredicted)
			 * 		{clear ROB[h], RegisterStat; fetch branch dest;};}
			 */
		}
		else if(op.equals("Sw")){
			int AddressNumber = rs[instruction.getReservationStation()].getAddress();
			int ROBnumber = getROBposition(instruction);
			memoria[AddressNumber] = intTo32Binary(rob[ROBnumber].getValue());
		}
		else{
			int ROBnumber = getROBposition(instruction);
			int d_aux = rob[ROBnumber].getDest();
			registers[d_aux].setValue(rob[ROBnumber].getValue());
		}

		int ROBnumber = getROBposition(instruction);
		int d_aux = rob[ROBnumber].getDest();
		rob[ROBnumber].setBusy(false);
		if(registers[d_aux].getReorderNum() == ROBnumber){
			registers[d_aux].setBusy(false);
		}
	}

	/* --------------------------------------------------------------------------------------*/

	public int getROBposition(Instruction instruction){
		int ROBnumber = -1;
		for(int i = 0; i<10; i++){
			if(rob[i].getInstruction().equals(instruction)){
				ROBnumber = i;
				i = 10;
			}
		}
		return ROBnumber;
	}

	public String intTo32Binary(int value){
		String binaryString = Integer.toBinaryString(value);
		while(binaryString.length() < 32)
			binaryString = "0" + binaryString;
		return binaryString;
	}
}
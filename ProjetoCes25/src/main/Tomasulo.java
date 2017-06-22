package main;

import java.util.ArrayList;
import java.util.List;

public class Tomasulo {

	private ReorderBuffer[] rob;
	public ReorderBuffer[] getRob() {
		return rob;
	}

	private ReservationStation[] rs;

	public ReservationStation[] getRs() {
		return rs;
	}
	private int[] memoria = new int[4000];
	
	private Register[] registers;
	
	private int h;
	private int b = 0;
	public Tomasulo(){
		
		rs = new ReservationStation[11];
		for(int i = 0; i< 11;i++){
			rs[i] = new ReservationStation();
		}
		rob = new ReorderBuffer[10];
		for(int i = 0; i< 10;i++){
			rob[i] = new ReorderBuffer();
		}
		registers = new Register[32];
		for(int i = 0; i< 32;i++){
			registers[i] = new Register();
		}
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
		int d;
		int maxrs = -1;
		int minrs = 0;
		String op = instruction.get_op();
		if(op.equals("Add") || op.equals("Sub") || op.equals("Addi") || op.equals("Beq") || op.equals("Ble") || op.equals("Bne") || op.equals("Jmp")){
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
			{
				r = i;
				break;
			}
				
		}
		if(!rob[b].isBusy() && r != -1){
			if(registers[instruction.get_rs()].isBusy()){
				d = registers[instruction.get_rs()].getReorderNum();
				if(rob[d].isReady()){
					rs[r].setVj(rob[d].getValue());
					rs[r].setQj(-1);
				}
				else{
					rs[r].setQj(d);
				}
			}
			else{
				rs[r].setVj(registers[instruction.get_rs()].getValue());
				rs[r].setQj(-1);
			}
			rs[r].setBusy(true);
			rs[r].setDest(b);
			rob[b].setInstruction(instruction);
			rob[b].setDest(instruction.get_rd());
			rob[b].setReady(false);
			rob[b].setBusy(true);
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi" || instruction.get_op() == "Sw" || instruction.get_op() == "Beq" || instruction.get_op() == "Ble" || instruction.get_op() == "Bne"){
				if(registers[instruction.get_rt()].isBusy()){
					d = registers[instruction.get_rt()].getReorderNum();
					if(rob[d].isReady()){
						rs[r].setVk(rob[d].getValue());
						rs[r].setQk(-1);
					}
					else{
						rs[r].setQk(d);
					}
				}
				else{
					if(instruction.get_op() == "Addi")
						rs[r].setVk(instruction.get_immediate());
					else
						rs[r].setVk(registers[instruction.get_rt()].getValue());
					rs[r].setQk(-1);
				}
			}
			if(instruction.get_op() == "Add" || instruction.get_op() == "Sub" || instruction.get_op() == "Mul" || instruction.get_op() == "Addi"){
				if(instruction.get_op() == "Addi"){
					registers[instruction.get_rt()].setReorderNum(b);
					registers[instruction.get_rt()].setBusy(true);
					rob[b].setDest(instruction.get_rt());
				}
				else{
					registers[instruction.get_rd()].setReorderNum(b);
					registers[instruction.get_rd()].setBusy(true);
					rob[b].setDest(instruction.get_rd());
				}
					
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
			rs[r].setInstruction(instruction);
			b++;
			if(b == 10)
				b = 0;
		}
	}
	
	public void Execute(Instruction instruction){
		int r = instruction.getReservationStation();

		String op = instruction.get_op();
		if(op.equals("Add") || op.equals("Sub") || op.equals("Addi") || op.equals("Mul") || op.substring(0, 1).equals("B")){
			if(rs[r].getQj() == -1 && rs[r].getQk() == -1){
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
					case "Beq":
						rs[r].setResult(rs[r].getVj() - rs[r].getVk());
						break;
					case "Ble":
						rs[r].setResult(rs[r].getVj() - rs[r].getVk());
						break;
					case "Bne":
						rs[r].setResult(rs[r].getVj() - rs[r].getVk());
						break;
				}
			}
			if(op.equals("Mul")){
				if(instruction.getExecutionClocks() == 3)
					instruction.set_status("Write");
			}
			else{
				if(instruction.getExecutionClocks() == 1)
					instruction.set_status("Write");
			}
				
		}
		else if(op.equals("Lw")){
			
			if(instruction.getLoadStep() == 1 && rs[r].getQj() == -1){
				int b = getROBposition(instruction);
				int i = h;
				boolean entra = true;
				while(i != b){
					if(rob[i].getInstruction().get_op() == "Sw"){
						entra = false;
						break;
					}
					i++;
					if(i == 10)
						i = 0;
				}
				if(entra){
					instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
					rs[r].setAddress(rs[r].getVj()+rs[r].getAddress());
					instruction.setLoadStep(2);
				}
			}
			else if(instruction.getLoadStep() == 2){
				int b = getROBposition(instruction);
				int i = h;
				boolean entra = true;
				while(i != b){
					if(rob[i].getInstruction().get_op() == "Sw" && rs[r].getAddress() == rob[i].getDest()){
						entra = false;
						break;
					}
					i++;
					if(i == 10)
						i = 0;
				}
				instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
				rs[r].setResult(memoria[rs[r].getAddress()]);
				if(instruction.getExecutionClocks() == 4)
					instruction.set_status("Write");
				
			}
		}
		else if(op.equals("Sw")){
			if(rs[r].getQj() == -1 && getROBposition(instruction) == h){
				instruction.setExecutionClocks(instruction.getExecutionClocks() +1);
				rob[h].setAddress(rs[r].getVj()+rs[r].getAddress());
				if(instruction.getExecutionClocks() == 4)
					instruction.set_status("Write");
			}
		}
		
	}
	
	public void Write(Instruction instruction){
		int r = instruction.getReservationStation();
		if(instruction.get_op() == "Sw"){
			if(rs[r].getQk() == -1){
				rob[h].setValue(rs[r].getVk());
				rob[h].setReady(true);
				instruction.set_status("Commit");
				instruction.setHasWriten(true);
			}
				
			
		}
		else{
			
			int b_aux = getROBposition(instruction);
			rs[r].setBusy(false);
			for(int i = 0; i<10; i++){
				if(rs[i].getQj() == b_aux){
					rs[i].setVj(rs[r].getResult());
					rs[i].setQj(-1);
				}
				if(rs[i].getQk() == b_aux){
					rs[i].setVk(rs[r].getResult());
					rs[i].setQk(-1);
				}
			}
			rob[b_aux].setValue(rs[r].getResult());
			rob[b_aux].setReady(true);
			instruction.set_status("Commit");
			instruction.setHasWriten(true);
		}
		
	}
	
	public int Commit(Instruction instruction){
		int value = 0;
		if(rob[h].getInstruction() == instruction && rob[h].isReady()){
			String op = instruction.get_op();
			if(op.substring(0,1).equals("B") || op.equals("Jmp")){
				/**
				 * if(ROB[h].Instruction == Branch)
				 * 	{if (branch is mispredicted)
				 * 		{clear ROB[h], RegisterStat; fetch branch dest;};}
				 */
				boolean jump = false;
				switch(op){
				case "Beq":
					if( rob[h].getValue() == 0)
						jump = true;
					break;
				case "Ble":
					if( rob[h].getValue() <= 0)
						jump = true;
					break;
				case "Bne":
					if( rob[h].getValue() != 0)
						jump = true;
					break;
				}
				if(rob[h].isPrediction() != jump){
					if(rob[h].isPrediction() && !jump)
						value = 0;
					else if(!rob[h].isPrediction() && jump)
						value = 1;
					for(int i = 0;i<10;i++){
						rob[i].setBusy(false);
						b = h+2;
						if(b >= 10)
							b = 0;
					}
					for(int i = 0;i<32;i++){
						registers[i].setBusy(false);
					}
				}
			}
			else if(op.equals("Sw")){
				int AddressNumber = rs[instruction.getReservationStation()].getAddress();
				int ROBnumber = getROBposition(instruction);
				memoria[AddressNumber] = rob[ROBnumber].getValue();
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
			instruction.set_status("Done");
			h++;
			if(h == 10)
				h = 0;
		}
		return value;
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
	
	public Register[] getRegisters(){
		return registers;
	}
	
	public int[] getMemory(){
		return memoria;
	}
	
	public void setROBprediction(Instruction instruction, boolean prediction){
		int r = getROBposition(instruction);
		rob[r].setPrediction(prediction);
	}
}
package main;

import GUI.BufferReord;
import GUI.EstReserva;
import GUI.MemRecente;
import GUI.Registradores;
import GUI.Relogio;

public class Responder implements Listener{
	Registradores regs;
	Relogio time;
	MemRecente memoria;
	EstReserva estReserva;
	BufferReord bufferReord;
	public Responder(){
		regs = new Registradores();
		time = new Relogio();
		memoria = new MemRecente();
		estReserva = new EstReserva();
		bufferReord = new BufferReord();
		regs.setVisible(true);
		time.setVisible(true);
		memoria.setVisible(true);
		bufferReord.setVisible(true);
		estReserva.setVisible(true);
	}
	@Override
    public void event(Register[] registers,ReorderBuffer[] rob,ReservationStation[] rs) {
        for(int i =0; i< 32; i++){
        	regs.setValueAt(registers[i].getReorderNum(), i%8, 1 + 4*(i/8));
        	regs.setValueAt(String.valueOf(registers[i].isBusy()), i%8, 2 + 4*(i/8));
        	regs.setValueAt(registers[i].getValue(), i%8, 3 + 4*(i/8));
        }
        for(int i = 0; i< 11;i++){
        	if(rs[i].getInstruction() != null){
        		estReserva.setValueAt(String.valueOf(rs[i].isBusy()), i, 2);
            	estReserva.setValueAt(rs[i].getInstruction().getComment(), i, 3);
            	estReserva.setValueAt(rs[i].getDest(), i, 4);
            	estReserva.setValueAt(rs[i].getVj(), i, 5);
            	estReserva.setValueAt(rs[i].getVk(), i, 6);
            	estReserva.setValueAt(rs[i].getQj(), i, 7);
            	estReserva.setValueAt(rs[i].getQk(), i, 8);
            	estReserva.setValueAt(rs[i].getAddress(), i, 9);
        	}
        	
        }
        
        for(int i = 0; i< 10;i++){
        	if(rob[i].getInstruction() != null){
        		bufferReord.setValueAt(String.valueOf(rob[i].isBusy()), i, 1);
        		bufferReord.setValueAt(rob[i].getInstruction().getComment(), i, 2);
        		bufferReord.setValueAt(rob[i].getInstruction().get_status(), i, 3);
        		bufferReord.setValueAt(rob[i].getDest(), i, 4);
        		bufferReord.setValueAt(rob[i].getValue(), i, 5);
        	}
        	
        }
    }
}

package main;

import java.util.List;

import GUI.BufferReord;
import GUI.Buttons;
import GUI.EstReserva;
import GUI.MemRecente;
import GUI.Registradores;
import GUI.Relogio;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//	Processor processor = new Processor();
		//processor.run();
		
		Buttons buttons = new Buttons();
		//Registradores regs = new Registradores();
		
		
		//regs.setVisible(true);
		Responder responder = new Responder();
		buttons.addListener(responder);
		buttons.setVisible(true);
	}

}

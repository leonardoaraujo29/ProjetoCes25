package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InstructionReader {
	private List<Instruction> instructions;
	
	public void readFile () {
		String file = "C:\\Users\\Leonardo Araujo\\workspace\\ProjetoCes25\\src\\entrada1.txt";
		instructions = new ArrayList<Instruction>();
		try {
	    	FileReader arq = new FileReader(file);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // lê a primeira instrução
	    	while (instAux != null) {
	    		Instruction in = new Instruction(instAux.split(" ")[0],instAux.substring(34));
	    		System.out.printf("%s\n", in.get_op());
	    		instructions.add(in);
	    		instAux = lerArq.readLine(); // lê da segunda até a última linha
	    	} 
	    	arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	    }
		
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

}

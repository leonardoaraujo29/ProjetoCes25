import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InstructionReader {
	private List<Instruction> instructions;
	
	public void readFile () {
		String file = "entrada.txt";
		instructions = new ArrayList<Instruction>();
		try {
	    	FileReader arq = new FileReader(file);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // l� a primeira instru��o
	    	while (instAux != null) {
	    		Instruction in = new Instruction(instAux.split(" ")[0]);
	    		//System.out.printf("%s\n", in._op);
	    		instructions.add(in);
	    		instAux = lerArq.readLine(); // l� da segunda at� a �ltima linha
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

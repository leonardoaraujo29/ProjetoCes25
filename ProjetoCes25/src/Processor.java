import java.util.ArrayList;
import java.util.List;

public class Processor {
	private int clock;
	private List<Instruction> instructions;
	private Tomasulo tomasulo;
	
	public Processor(){
		tomasulo = new Tomasulo();
		instructions = new ArrayList<Instruction>();
		Instruction instruction = new Instruction();
		instruction.set_status("Issue");
		instructions.add(instruction);
		
	}
	public void run(){
		int lastIssued;
		for(clock = 0;clock<1;clock++){
			//pegar as instru��es issue
			for(int i = 0;i<instructions.size();i++){
				if(instructions.get(i).get_status() == "Issue"){
					tomasulo.Issue(instructions.get(i));
				}
				
				
			}
			//pegar as instru��es execute
			for(int i = 0;i<instructions.size();i++){
			}
			//pegar as instru��es write
			for(int i = 0;i<instructions.size();i++){
				
			}
			//pegar as instru��es commit
			for(int i = 0;i<instructions.size();i++){
				
			}
		}
	}
}

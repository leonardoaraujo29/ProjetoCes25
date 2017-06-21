import java.util.ArrayList;
import java.util.List;

public class Processor {
	private int clock;
	private List<Instruction> instructions;
	private Tomasulo tomasulo;
	InstructionReader instructionReader;
	
	public Processor(){
		tomasulo = new Tomasulo();
		instructions = new ArrayList<Instruction>();
		instructionReader = new InstructionReader();
		instructions = instructionReader.getInstructions();
	
	}
	public void run(){
		int lastIssued = 0;
		for(clock = 0;clock<1;clock++){
			//pegar as instruções issue
			for(int i = 0;i<instructions.size();i++){
				if(instructions.get(i).get_status() == "Issue"){
					tomasulo.Issue(instructions.get(i));
					lastIssued = i;
					break;
				}
				
				
			}
			//pegar as instruções execute e write
			for(int i = 0;i<lastIssued;i++){
				if(instructions.get(i).get_status() == "Execute"){
					if(instructions.get(i).get_op() == "Lw" && instructions.get(i).getLoadStep() == 0)
					{
						instructions.get(i).setLoadStep(1);
						for(int j = 0; j < i;j++){
							if(instructions.get(j).get_op() == "Sw" && instructions.get(j).get_status() != "Done"){
								instructions.get(i).setLoadStep(0);
							}
						}
					}
					tomasulo.Execute(instructions.get(i));
				}
				if(instructions.get(i).get_status() == "Write"){
					
				}
			}
			//pegar as instruções commit
			for(int i = 0;i<instructions.size();i++){
				
			}
		}
	}
}

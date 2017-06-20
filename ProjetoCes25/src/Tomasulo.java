public class Tomasulo {

	public void Issue(Instruction instruction){
		instruction.setStatus("Execute");
	}
	
	public void Execute(Instruction instruction){
		
	}
	
	public void Write(Instruction instruction){
		
	}
	
	public void Commit(Instruction instruction){
		
	}
}
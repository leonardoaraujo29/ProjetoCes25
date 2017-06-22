package GUI;

public class TestaPorra {
	
	public static void main (String[] args) throws InterruptedException{
		int clock = 0;
		Relogio time = new Relogio();
		MemRecente memoria = new MemRecente();
		time.setVisible(true);
		memoria.setVisible(true);
		time.setValueAt(clock, 0, 1);
		while(true){
			clock++;
			Thread.sleep(1000);
			time.setValueAt(clock, 0, 1);
		}
	}
}

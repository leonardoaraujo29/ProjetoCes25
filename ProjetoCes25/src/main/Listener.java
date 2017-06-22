package main;

public interface Listener {
	void event(Register[] registers,ReorderBuffer[] rob,ReservationStation[] rs);
}

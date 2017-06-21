public class Instruction {

	String _status;
	String _funct;
	String _opBinCode;
	String _op;
	String _targetAddress;
	int _rs; 
	int _rt;
	int _rd;
	int _shamt; 
	int _immediate;
	
	public Instruction(String string) {
		this._status = "issue";
		this._funct = string.substring(26);
		this._opBinCode = string.substring(0, 6);
		getOp(_opBinCode, _funct);
		this._targetAddress = string.substring(6);
		this._rs = Integer.parseInt(string.substring(6,11), 2); 
		this._rt = Integer.parseInt(string.substring(11,16), 2);
		this._rd = Integer.parseInt(string.substring(16,21), 2);
		this._shamt = Integer.parseInt(string.substring(21,26), 2); 
		this._immediate = getSignal(string.substring(16));
	}	
		
	private int getSignal(String data){
		int signalImm = data.charAt(0);
		int immediate = Integer.parseInt(data.substring(1),2);
		if(signalImm=='1')
			immediate= immediate*(-1);
		return immediate;
	}
		
	private void getOp(String opCode, String funct){
		switch(opCode){
		//Instru��o tipo R
		//Instru��o com rs rt rd 
			case "000000":
				switch(funct){
					//Fun��o Add
					case "100000":
						this._op = "Add";
						break;
					//Fun��o Mul
					case "011000":
						this._op = "Mul";
						break;
					//Fun��o Sub
					case "100010":
						this._op = "Sub";
						break;
					//Fun��o Nop
					case "000000":
						this._op = "Nope";
						break;
				}
				break;
	
				//Instru��es com rs rt immediate
				//Instru��o Addi
				case "001000":
					this._op = "Addi";
					break;
				//Instru��o Beq
				case "000101":
					this._op = "Beq";
					break;
				//Instru��o Ble
				case "000111":
					this._op = "Ble";
					break;
				//Instru��o Bne
				case "000100":
					this._op = "Bne";
					break;
				//Instru��o Jmp
				//Instru��o com targetAdress
				case "000010":
					this._op = "Jmp";
					break;
				//Instru��o Lw
				case "100011":
					this._op = "Lw";
					break;
				//Instru��o Sw
				case "101011":
					this._op = "Sw";
					break;
				default:
					System.out.println("Comando n�o interpretado!!!");
					break;
				
		}	
	}

	public String get_status() {
		return _status;
	}

	public void set_status(String _status) {
		this._status = _status;
	}

	public String get_opBinCode() {
		return _opBinCode;
	}

	public void set_opBinCode(String _opBinCode) {
		this._opBinCode = _opBinCode;
	}

	public String get_op() {
		return _op;
	}

	public void set_op(String _op) {
		this._op = _op;
	}

	public String get_targetAddress() {
		return _targetAddress;
	}

	public void set_targetAddress(String _targetAddress) {
		this._targetAddress = _targetAddress;
	}

	public int get_rs() {
		return _rs;
	}

	public void set_rs(int _rs) {
		this._rs = _rs;
	}

	public int get_rt() {
		return _rt;
	}

	public void set_rt(int _rt) {
		this._rt = _rt;
	}

	public int get_rd() {
		return _rd;
	}

	public void set_rd(int _rd) {
		this._rd = _rd;
	}

	public int get_immediate() {
		return _immediate;
	}

	public void set_immediate(int _immediate) {
		this._immediate = _immediate;
	}
	
}

public class Prediction {

    public String v2bit;
    public String v3bits;

    public boolean V1(){ return false ;}

    public void V2resultado(boolean seguiu){
        if(seguiu){
            this.v2bit = "1";
        }
        else{
            this.v2bit = "0";
        }
    }

    public boolean V2(){
        if(this.v2bit.equals("0")) return false;
        else return true;
    }

    public void V3resultado(boolean seguido){
        switch (this.v3bits){
            case "00":
                if(seguido){
                    this.v3bits = "01";
                }
                else;
                break;
            case "01":
                if(seguido){
                    this.v3bits = "11";
                }
                else{
                    this.v3bits = "00";
                }
                break;
            case "10":
                if(seguido){
                    this.v3bits = "11";
                }
                else{
                    this.v3bits = "00";
                }
                break;
            case "11":
                if(seguido);
                else{
                    this.v3bits = "10";
                }
                break;
        }
    }

    public boolean V3(){
        if(this.v3bits.equals("00") || this.v3bits.equals("01"))
            return false;
        else
            return true;
    }

    public Prediction(){
        this.v2bit = "0";
        this.v3bits = "01";
    }
}

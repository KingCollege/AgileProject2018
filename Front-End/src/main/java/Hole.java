
/**
 * A class that describes a kazan.
 * @author Marta Krawczyk, Tao Lin, Mandu Shi and Adam Able
 * @version    2018.11.28
 */
public class Hole {
    private int balls_num;
    private boolean hole_side;
    private int hindex;
    private boolean tuz;
    public  Hole(int num,boolean side,int index){//if it's true then for playerside//
        balls_num = num;
        hole_side = side;
        hindex = index;
        tuz = false;
    }

    boolean markAsTuz(){
        tuz = true;
        hole_side = !hole_side;
        return tuz;
    }

    boolean checkTuz(){
        return tuz;
    }
    int getNum(){
        return balls_num;
    }
    int getIndex(){
        return hindex;
    }
    boolean getSide(){
        return hole_side;
    }
    void changeNum(int Cnum){
        balls_num = Cnum;
    }
    void incrementBalls() { balls_num++;}

}


/**
 * A class that describes a kazan.
 * @author Marta Krawczyk, Tao Lin, Mandu Shi and Adam Able
 * @version    2018.11.28
 */
public class Kazan {
    private int number;
    private boolean side;
    public kazan(int num,boolean side){
        number = num;
        side = side;
    }
    public void add_balls(int num){
        number+=num;
    }
    public int return_num(){
        return number;
    }
    public boolean check_win(){
        if (number>=82){
            return true;
        }
        else {
            return false;
        }
    }
}

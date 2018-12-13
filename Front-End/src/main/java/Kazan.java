public class Kazan {
    private int number;
    private boolean side;
    public Kazan(int num,boolean side){
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

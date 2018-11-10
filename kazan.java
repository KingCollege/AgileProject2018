package Backend;

public class kazan {
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
    public void check_win(){
        if (number>=82){
            System.out.println("You win");
        }
        else {
            System.out.println("The game is not finish");
        }
    }
}

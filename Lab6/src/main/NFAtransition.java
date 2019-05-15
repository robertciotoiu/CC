package main;

public class NFAtransition{
    public int state_from;
    public int state_to;
    public char trans_symbol;

    public NFAtransition(int s1, int s2, char symbol){
        this.state_from = s1;
        this.state_to = s2;
        this.trans_symbol = symbol;
    }
}

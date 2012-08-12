package cluedo;

public class Pair {
    private int first = 0;
    private int second = 0;

    public Pair(int first, int second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
       

        return 31*(first + second) * first + second;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
                Pair otherPair = (Pair) other;
                return otherPair.getFirst()==this.first && otherPair.getSecond() == this.second;
                
        }

        return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
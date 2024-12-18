package core;

import java.util.ArrayList;

public class MoveOrder {
    private String current, prev;
    private ArrayList<String> canceled = new ArrayList<>();

    public void newMove(String movement){
        boolean stop = false;
        if(current == null) current = movement;
        else {
            for (String c : canceled) {
                if (movement.equals(c)) stop = true;
            }
        }
        if(!stop){
            prev = current;
            current = movement;
        }
    }

    public void cancel(String toCancel){
        if(!canceled.contains(toCancel))canceled.add(toCancel);
    }

    public void uncancel(String toUncancel){
        canceled.remove(toUncancel);
    }

    public void uncancelAll(){
        canceled.clear();
    }

    public String getCurrent(){
        return current;
    }

    public String getPrev(){
        return prev;
    }

    public void setCurrent(String newCurrent){
        current = newCurrent;
    }

    public void setPrev(String newPrev){
        prev = newPrev;
    }

}

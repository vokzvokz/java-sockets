package task2;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class task2{
    private volatile static Map<Character, Integer> chars = new TreeMap<>();
    public static void add(char c){
        if(chars.containsKey(c)){
            chars.put(c, chars.get(c)+1);
        }
        else chars.put(c, 1);
    }
    synchronized static Map<Character, Integer> getChars(){
        return chars;
    }
    public static void main(String [] args){
        Thread adding = new Thread(new symCollecting());
        Thread calculating = new Thread(new Runnable() {
            char [] maxes = new char[3];
            char [] mins = new char[3];
            char buf;
            @Override
            public void run() {
                while (adding.isAlive()){
                    try{
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e){}
                }
                System.out.println(task2.getChars().size());
                Iterator<Character> it = getChars().keySet().iterator();
                mins[0]=mins[1]=mins[2]=maxes[0]=maxes[1]=maxes[2]=it.next();
                while (it.hasNext()){
                    buf = it.next();
                    if(getChars().get(buf)>getChars().get(maxes[0])){
                        maxes[0] = buf;
                        continue;
                    }
                    if(getChars().get(buf)>getChars().get(maxes[1])){
                        maxes[1] = buf;
                        continue;
                    }
                    if(getChars().get(buf)>getChars().get(maxes[2])){
                        maxes[2] = buf;
                        continue;
                    }
                    if(getChars().get(buf)<getChars().get(mins[0])){
                        mins[0] = buf;
                        continue;
                    }
                    if(getChars().get(buf)<getChars().get(mins[1])){
                        mins[1] = buf;
                        continue;
                    }
                    if(getChars().get(buf)<getChars().get(mins[2])){
                        mins[2] = buf;
                        continue;
                    }

                }
                System.out.println("Popular symbols: '"+maxes[0]+"' '"+maxes[1]+"' '"+maxes[2]+"'");
                System.out.println("Unpopular symbols: '"+mins[0]+"' '"+mins[1]+"' '"+mins[2]+"'");

            }
        });
        adding.start();
        calculating.start();
        //System.out.println(chars.size());
    }
}

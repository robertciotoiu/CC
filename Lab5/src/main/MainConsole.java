package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainConsole {
    public static void main(String[] args) throws IOException {  
    	
        NFA nfa = new NFA();     
        
        try{            
            FileReader file = new FileReader("src\\main\\Example");
            BufferedReader readFile = new BufferedReader(file);            
            //Read a row
            String row = readFile.readLine();
            String[] input = row.split(" ");//take the  input           
           for(String letter : input){//separate them
                nfa.addLetterInput(letter);
            }            
            row = readFile.readLine();//read initial state
            nfa.addInitialState(new State(row));            
            row = readFile.readLine();//read initial state
            String[] finalStates = row.split(" ");           
            for(String state : finalStates){//parse final states
                nfa.addFinalState(new State(state));
            }           
            row = readFile.readLine();
            State state1;
            State state2;
            //We read line by line the transitions until \n
            while (row != null){              
                String[] part = row.split(" ");                
                state1 = nfa.findStateInProgramFunction(part[0]);
                state2 = nfa.findStateInProgramFunction(part[2]);       
                if (state1 == null){ 
                    nfa.addState(new State(part[0]));
                    state1 = nfa.findStateInProgramFunction(part[0]);
                }
                if (state2 == null){
                    if(part[0] == part[2]){
                        state2 = state1;
                    }else{
                        nfa.addState(new State(part[2]));
                        state2 = nfa.findStateInProgramFunction(part[2]);
                    }
                }  
                state1.addTransition(new Transition(part[1], state2));                    
                row = readFile.readLine();               
            }            
            file.close();            
        } catch (IOException e) {
            //Exception if the file could not be opened           
            System.err.printf("An error occurred opening the file. %s.\n", e.getMessage());            
        }       
        System.out.println("");
        
        DFA dfa;
        //Call function nfa.convertToDFA()
        System.out.println(nfa);
        dfa = nfa.convertToDFA();
        System.out.println("");
        System.out.println(dfa);
        
    }
    
}

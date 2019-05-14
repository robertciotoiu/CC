package main;

import java.util.ArrayList;

public class NfaToDfa {
    
    ArrayList<String> input;
    ArrayList<State> programFunction;
    ArrayList<State> last;
    State initial;
    
    NfaToDfa(){       
        input = new ArrayList<>();
        programFunction = new ArrayList<>();
        last = new ArrayList<>();   
    }
    
    void addInitialState(State state){        
        initial = state;
        addState(state);    
    }
    
    void addLetterInput(String letter) {    
        input.add(letter);   
    }
    
    void addState(State state){   
        State exist = findStateInProgramFunction(state.name);  //Verify if the state is already in program function  
        if(exist == null){  //If it is not    
            programFunction.add(state);  //Add state to program function  
        }
    }
    
    void addFinalState(State state){    
        last.add(state);
        addState(state);       
    }
    
    boolean findLetterInInput(String key){       
       for(String letter: input){           
            if(key.equals(letter))               
                return true;
        }       
        return false;
    }
    
    State findStateInProgramFunction(String name){       
        for(State state: programFunction){          
            if(state.name.equals(name))              
                return state;
        }        
        return null;
    }
    
    boolean isInProgramFunction(String name){
        State exist = findStateInProgramFunction(name);     //Look in program function
        if(exist != null)   //If didn't find
            return true;
        else                //If found it
            return false;
    }
    
    @Override
    public String toString(){
        String back = ""; 
        back += "Input: ";
        for(String letter: input)
            back += letter + " ";
        back += "\n";
        back += "Initial state: " + initial.name;
        back += "\n";
        back += "Final(s) states: ";
        for(State finals: last)
            back += finals.name + " ";
        back += "\n";
        for(State state: programFunction){
            for(Transition t: state.transitions){ 
                back += state.name + " " + t.letter + " " + t.next.name + "\n";
            }
        }
        return back;
    }
    


}
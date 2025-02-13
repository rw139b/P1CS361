package fa.dfa;

import fa.State;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a state in a Deterministic Finite Automaton (DFA).
 * This class extends the State class and provides methods to check if the state is final,
 * set the final status, and manage transitions to other states.
 * 
 * @author Ryan Wensmann, Kodey Thompson
 */
public class DFAState extends State {
    
    private boolean isFinal;
    private Map<Character, DFAState> transitions;

    /**
     * Constructs a new DFAState with the given name.
     * The state is initialized as non-final, and the transition map is empty.
     * 
     * @param name The name of the state.
     */
    public DFAState(String name) {
        super(name);
        this.isFinal = false;
        this.transitions = new LinkedHashMap<>(); // Preserve order
    }

    /**
     * Returns whether this state is a final state.
     * 
     * @return true if this state is final, false otherwise.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Sets the final status of this state.
     * 
     * @param isFinal The final status to set.
     */
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Adds a transition from this state to another state on a given symbol.
     * 
     * @param symbol The symbol that triggers the transition.
     * @param state The state to transition to.
     */
    public void addTransition(char symbol, DFAState state) {
        transitions.put(symbol, state);
    }

    /**
     * Returns the next state reached by transitioning on the given symbol.
     * 
     * @param symbol The symbol that triggers the transition.
     * @return The next DFAState after the transition, or null if no such transition exists.
     */
    public DFAState getNextState(char symbol) {
        return transitions.get(symbol);
    }
}
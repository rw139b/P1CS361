package fa.dfa;

import fa.State;
import java.util.LinkedHashMap;
import java.util.Map;

public class DFAState extends State {
    private boolean isFinal;
    private Map<Character, DFAState> transitions;

    public DFAState(String name) {
        super(name);
        this.isFinal = false;
        this.transitions = new LinkedHashMap<>(); // Preserve order
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void addTransition(char symbol, DFAState state) {
        transitions.put(symbol, state);
    }

    public DFAState getNextState(char symbol) {
        return transitions.get(symbol);
    }
}
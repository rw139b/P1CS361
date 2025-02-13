package fa.dfa;

import fa.State;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a Deterministic Finite Automaton (DFA).
 * This class implements the DFAInterface and provides methods
 * to add states, set final states, set the start state, add transitions,
 * check if a string is accepted, and more.
 * 
 * @author Ryan Wensmann, Kodey Thompson
 */
public class DFA implements DFAInterface {
    
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private LinkedHashMap<String, DFAState> states = new LinkedHashMap<>();
    private DFAState startState;
    private LinkedHashSet<DFAState> finalStates = new LinkedHashSet<>();

    /**
     * Adds a state to the DFA.
     * 
     * @param name The name of the state to add.
     * @return true if the state was added successfully, false if the state already exists.
     */
    @Override
    public boolean addState(String name) {
        if (states.containsKey(name)) return false;
        states.put(name, new DFAState(name));
        return true;
    }

    /**
     * Sets the given state as a final state.
     * 
     * @param name The name of the state to set as final.
     * @return true if the state was set as final, false if the state does not exist.
     */
    @Override
    public boolean setFinal(String name) {
        DFAState state = states.get(name);
        if (state == null) return false;
        state.setFinal(true);
        finalStates.add(state);
        return true;
    }

    /**
     * Sets the given state as the start state.
     * 
     * @param name The name of the state to set as the start state.
     * @return true if the state was set as the start state, false if the state does not exist.
     */
    @Override
    public boolean setStart(String name) {
        DFAState state = states.get(name);
        if (state == null) return false;
        startState = state;
        return true;
    }

    /**
     * Adds a symbol to the input alphabet (sigma) of the DFA.
     * 
     * @param symbol The symbol to add to the input alphabet.
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * Checks if the given string is accepted by the DFA.
     * 
     * @param s The string to check for acceptance.
     * @return true if the DFA accepts the string, false otherwise.
     */
    @Override
    public boolean accepts(String s) {
        DFAState current = startState;
        for (char c : s.toCharArray()) {
            current = current.getNextState(c);
            if (current == null) return false;
        }
        return current.isFinal();
    }

    /**
     * Returns the input alphabet (sigma) of the DFA.
     * 
     * @return A set of characters representing the input alphabet of the DFA.
     */
    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);
    }

    /**
     * Returns the state with the given name.
     * 
     * @param name The name of the state to retrieve.
     * @return The DFAState with the given name, or null if the state does not exist.
     */
    @Override
    public State getState(String name) {
        return states.get(name);
    }

    /**
     * Checks if the state with the given name is a final state.
     * 
     * @param name The name of the state to check.
     * @return true if the state is a final state, false otherwise.
     */
    @Override
    public boolean isFinal(String name) {
        DFAState state = states.get(name);
        return state != null && state.isFinal();
    }

    /**
     * Checks if the state with the given name is the start state.
     * 
     * @param name The name of the state to check.
     * @return true if the state is the start state, false otherwise.
     */
    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }

    /**
     * Returns a string representation of the DFA, including the states, input alphabet,
     * transition function, start state, and final states.
     * 
     * @return A string representation of the DFA.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // States
        sb.append("Q = { ");
        states.keySet().forEach(s -> sb.append(s).append(" "));
        sb.append("}\n");
        // Sigma
        sb.append("Sigma = { ");
        sigma.forEach(c -> sb.append(c).append(" "));
        sb.append("}\n");
        // Delta
        sb.append("delta =\n\t");
        sigma.forEach(c -> sb.append("\t").append(c));
        sb.append("\n");
        states.values().forEach(s -> {
            sb.append(s.getName()).append("\t");
            sigma.forEach(c -> {
                DFAState to = s.getNextState(c);
                sb.append("\t").append(to != null ? to.getName() : "");
            });
            sb.append("\n");
        });
        // Start and Final
        sb.append("q0 = ").append(startState != null ? startState.getName() : "").append("\n");
        sb.append("F = { ");
        finalStates.forEach(s -> sb.append(s.getName()).append(" "));
        sb.append("}\n");
        return sb.toString().trim();
    }

    /**
     * Adds a transition from one state to another on a given symbol.
     * 
     * @param from The name of the state to transition from.
     * @param to The name of the state to transition to.
     * @param symbol The symbol that triggers the transition.
     * @return true if the transition was added successfully, false otherwise.
     */
    @Override
    public boolean addTransition(String from, String to, char symbol) {
        if (!sigma.contains(symbol)) return false;
        DFAState fromState = states.get(from);
        DFAState toState = states.get(to);
        if (fromState == null || toState == null) return false;
        fromState.addTransition(symbol, toState);
        return true;
    }

    /**
     * Swaps two symbols in the DFA's alphabet and updates the transitions accordingly.
     * 
     * @param symb1 The first symbol to swap.
     * @param symb2 The second symbol to swap.
     * @return A new DFA with the swapped symbols and updated transitions.
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA swapped = new DFA();
        // Copy sigma and swap symbols
        sigma.forEach(c -> swapped.addSigma(c == symb1 ? symb2 : c == symb2 ? symb1 : c));
        // Copy states
        states.keySet().forEach(swapped::addState);
        // Set start and final
        if (startState != null) swapped.setStart(startState.getName());
        finalStates.forEach(s -> swapped.setFinal(s.getName()));
        // Copy transitions with swapped symbols
        states.values().forEach(s -> sigma.forEach(c -> {
            DFAState to = s.getNextState(c);
            if (to != null) {
                char newSymb = c == symb1 ? symb2 : c == symb2 ? symb1 : c;
                swapped.addTransition(s.getName(), to.getName(), newSymb);
            }
        }));
        return swapped;
    }
}
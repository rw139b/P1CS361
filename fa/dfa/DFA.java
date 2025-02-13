package fa.dfa;

import fa.State;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DFA implements DFAInterface {
    private LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    private LinkedHashMap<String, DFAState> states = new LinkedHashMap<>();
    private DFAState startState;
    private LinkedHashSet<DFAState> finalStates = new LinkedHashSet<>();

    @Override
    public boolean addState(String name) {
        if (states.containsKey(name)) return false;
        states.put(name, new DFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        DFAState state = states.get(name);
        if (state == null) return false;
        state.setFinal(true);
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        DFAState state = states.get(name);
        if (state == null) return false;
        startState = state;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        DFAState current = startState;
        for (char c : s.toCharArray()) {
            current = current.getNextState(c);
            if (current == null) return false;
        }
        return current.isFinal();
    }

    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);
    }

    @Override
    public State getState(String name) {
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        DFAState state = states.get(name);
        return state != null && state.isFinal();
    }

    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }

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

    @Override
    public boolean addTransition(String from, String to, char symbol) {
        if (!sigma.contains(symbol)) return false;
        DFAState fromState = states.get(from);
        DFAState toState = states.get(to);
        if (fromState == null || toState == null) return false;
        fromState.addTransition(symbol, toState);
        return true;
    }

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
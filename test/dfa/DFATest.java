package test.dfa;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import fa.dfa.DFA;

public class DFATest {
	
	
	//------------------- dfa1 tests ----------------------//
	private DFA dfa1() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));
		
		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("c"));
		assertFalse(dfa.setFinal("c"));
		
		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "a", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));
		
		assertFalse(dfa.addTransition("c", "b", '1'));
		assertFalse(dfa.addTransition("a", "c", '1'));
		assertFalse(dfa.addTransition("a", "b", '2'));
		
		return dfa;
	}
	
	@Test
	public void test1_1() {
		DFA dfa = dfa1();
		System.out.println("dfa1 instantiation pass");
	}

	@Test
	public void test1_2() {
		DFA dfa = dfa1();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa1 correctness pass");
	}
	
	@Test
	public void test1_3() {
		DFA dfa = dfa1();
		
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
		
		System.out.println("dfa1 accept pass");
	}
	
	@Test
	public void test1_4() {
		DFA dfa = dfa1();
		
		String dfaStr = dfa.toString();
		String expStr = " Q = { a b }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	a	b\n"
				+ "q0 = a\n"
				+ "F = { b }";
		
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		
		System.out.println("dfa1 toString pass");
	}
	
	
	
	@Test
	public void test1_5() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		
		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));
		
		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
	
		System.out.println("dfa1Swap instantiation pass");
	}
	
	@Test
	public void test1_6() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("0"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("e"));
		
		System.out.println("dfa1Swap accept pass");
	}

//------------------- dfaI tests ----------------------//
	private DFA dfa2() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("3"));
		assertTrue(dfa.setFinal("3"));
		
		assertTrue(dfa.addState("0"));
		assertTrue(dfa.setStart("0"));
		
		assertTrue(dfa.addState("1"));
		assertTrue(dfa.addState("2"));
		
		
		assertFalse(dfa.setFinal("c"));
		assertFalse(dfa.setStart("a"));
		assertFalse(dfa.addState("2"));
		
		assertTrue(dfa.addTransition("0", "1", '0'));
		assertTrue(dfa.addTransition("0", "0", '1'));
		assertTrue(dfa.addTransition("1", "3", '0'));
		assertTrue(dfa.addTransition("1", "2", '1'));
		assertTrue(dfa.addTransition("2", "1", '0'));
		assertTrue(dfa.addTransition("2", "1", '1'));
		assertTrue(dfa.addTransition("3", "3", '0'));
		assertTrue(dfa.addTransition("3", "3", '1'));
		
		assertFalse(dfa.addTransition("3", "a", '1'));
		assertFalse(dfa.addTransition("c", "a", '1'));
		assertFalse(dfa.addTransition("3", "a", '2'));
		
		return dfa;
	}
	
	@Test
	public void test2_1() {
		DFA dfa = dfa2();
		System.out.println("dfa2 instantiation pass");
	}

	@Test
	public void test2_2() {
		DFA dfa = dfa2();
		assertNotNull(dfa.getState("0"));
		assertEquals(dfa.getState("1").getName(),"1");
		assertTrue(dfa.isStart("0"));
		assertNotNull(dfa.getState("3"));
		assertEquals(dfa.getState("3").getName(),"3");
		assertTrue(dfa.isFinal("3"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa2 correctness pass");
	}
	
	@Test
	public void test2_3() {
		DFA dfa = dfa2();
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
	
		System.out.println("dfa2 accept pass");
	}
	
	@Test
	public void test2_4() {
		DFA dfa = dfa2();
		
		String dfaStr = dfa.toString();
		String expStr = "Q={3 0 1 2}\n"
				+ "Sigma = {0 1}\n"
				+ "delta =\n"
				+ "	0	1\n"
				+ "3	3	3\n"
				+ "0	1	0\n"
				+ "1	3	2\n"
				+ "2	1	1\n"
				+ "q0 = 0\n"
				+ "F={3}\n";
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa2 toString pass");
	}
	
	
	
	@Test
	public void test2_5() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		//different DFA states
		assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
		assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
		assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
		assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
		assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));
		
		//ensure that the transitions of the original DFA don't change
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
		
		System.out.println("dfa2Swap instantiation pass");
	}
	
	@Test
	public void test2_6() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("101"));
		assertTrue(dfaSwap.accepts("11"));
		assertFalse(dfaSwap.accepts("010"));
		assertTrue(dfaSwap.accepts("000100000000001"));
		assertFalse(dfaSwap.accepts("0001000000000101"));
		System.out.println("dfa2Swap accept pass");
	}	

//------------------- dfa3 tests ----------------------//
private DFA dfa3() {
	DFA dfa = new DFA();
	dfa.addSigma('2');
	dfa.addSigma('1');
	
	assertTrue(dfa.addState("G"));
	assertTrue(dfa.addState("D"));
	
	assertTrue(dfa.setFinal("G"));
	assertTrue(dfa.setFinal("D"));
	
	assertTrue(dfa.addState("A"));
	assertTrue(dfa.setStart("D"));
	assertTrue(dfa.setStart("A"));
	
	assertTrue(dfa.addState("B"));
	assertTrue(dfa.addState("C"));
	assertTrue(dfa.addState("E"));
	assertTrue(dfa.addState("F"));
	
	assertFalse(dfa.addState("A"));
	assertFalse(dfa.setFinal("K"));
	assertFalse(dfa.setStart("BK"));
	
	assertTrue(dfa.addTransition("A", "B", '1'));
	assertTrue(dfa.addTransition("A", "C", '2'));
	
	assertTrue(dfa.addTransition("B", "D", '1'));
	assertTrue(dfa.addTransition("B", "E", '2'));
	
	assertTrue(dfa.addTransition("C", "F", '1'));
	assertTrue(dfa.addTransition("C", "G", '2'));
	
//	assertTrue(dfa.addTransition("C", "F", '1'));
//	assertTrue(dfa.addTransition("C", "G", '2'));
	
	assertTrue(dfa.addTransition("D", "D", '1'));
	assertTrue(dfa.addTransition("D", "E", '2'));
	
	assertTrue(dfa.addTransition("E", "D", '1'));
	assertTrue(dfa.addTransition("E", "E", '2'));
	
	assertTrue(dfa.addTransition("F", "F", '1'));
	assertTrue(dfa.addTransition("F", "G", '2'));
	
	assertTrue(dfa.addTransition("G", "F", '1'));
	assertTrue(dfa.addTransition("G", "G", '2'));
	
	
	assertFalse(dfa.addTransition("FF", "F", '1'));
	assertFalse(dfa.addTransition("F", "GG", '2'));
	
	assertFalse(dfa.addTransition("G", "F", 'K'));
	assertFalse(dfa.addTransition("A", "K", '7'));
	
	return dfa;
}

@Test
public void test3_1() {
	DFA dfa = dfa3();
	
	System.out.println("dfa3 instantiation pass");
}

@Test
public void test3_2() {
	DFA dfa = dfa3();
	assertNotNull(dfa.getState("A"));
	assertNull(dfa.getState("K"));
	assertEquals(dfa.getState("C").getName(),"C");
	assertTrue(dfa.isStart("A"));
	assertFalse(dfa.isStart("D"));
	assertNotNull(dfa.getState("G"));
	assertEquals(dfa.getState("E").getName(),"E");
	assertTrue(dfa.isFinal("G"));
	assertFalse(dfa.isFinal("B"));
	assertEquals(dfa.getSigma(), Set.of('2','1'));

	System.out.println("dfa3 correctness pass");
}

@Test
public void test3_3() {
	DFA dfa = dfa3();
	assertTrue(dfa.accepts("121212121"));
	assertTrue(dfa.accepts("12221212121"));
	assertFalse(dfa.accepts("12"));
	assertFalse(dfa.accepts("2"));
	assertFalse(dfa.accepts("1212"));

	System.out.println("dfa3 accept pass");
}

@Test
public void test3_4() {
	DFA dfa = dfa3();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={GDABCEF}\n"
			+ "Sigma = {2 1}\n"
			+ "delta =\n"
			+ "	2	1\n"
			+ "G	G	F\n"
			+ "D	E	D\n"
			+ "A	C	B\n"
			+ "B	E	D\n"
			+ "C	G	F\n"
			+ "E	E	D\n"
			+ "F	G	F\n"
			+ "q0 = A\n"
			+ "F = {G D}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa3 toString pass");
}



@Test
public void test3_5() {
	DFA dfa = dfa3();
	DFA dfaSwap = dfa.swap('2', '1');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
	assertTrue(dfa.getState("G") != dfaSwap.getState("G"));
	assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
	assertEquals(dfa.isStart("D"), dfaSwap.isStart("D"));
	assertEquals(dfa.isFinal("A"), dfaSwap.isFinal("A"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts("121212121"));
	assertTrue(dfa.accepts("12221212121"));
	assertFalse(dfa.accepts("12"));
	assertFalse(dfa.accepts("2"));
	assertFalse(dfa.accepts("1212"));

	System.out.println("df31Swap instantiation pass");
}

@Test
public void test3_6() {
	DFA dfa = dfa3();
	DFA dfaSwap = dfa.swap('2', '1');
	assertTrue(dfaSwap.accepts("212121212"));
	assertTrue(dfaSwap.accepts("21112121212"));
	assertFalse(dfaSwap.accepts("21"));
	assertFalse(dfaSwap.accepts("1"));
	assertFalse(dfaSwap.accepts("2121"));
	
	System.out.println("dfa3Swap accept pass");
}

	//------------------- dfa4 tests ----------------------//
	private DFA dfa4() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("even_0")); // Start + Final
		assertTrue(dfa.addState("odd_0"));
		assertTrue(dfa.setStart("even_0"));
		assertTrue(dfa.setFinal("even_0"));

		// Transitions
		assertTrue(dfa.addTransition("even_0", "odd_0", '0'));
		assertTrue(dfa.addTransition("even_0", "even_0", '1'));
		assertTrue(dfa.addTransition("odd_0", "even_0", '0'));
		assertTrue(dfa.addTransition("odd_0", "odd_0", '1'));

		return dfa;
	}

	@Test
	public void test4_1() {
		DFA dfa = dfa4();
		System.out.println("dfa4 instantiation pass");
	}

	@Test
	public void test4_2() {
		DFA dfa = dfa4();
		assertTrue(dfa.isStart("even_0"));
		assertTrue(dfa.isFinal("even_0"));
		assertFalse(dfa.isFinal("odd_0"));
		assertEquals(Set.of('0', '1'), dfa.getSigma());
		System.out.println("dfa4 correctness pass");
	}

	@Test
	public void test4_3() {
		DFA dfa = dfa4();
		assertTrue(dfa.accepts(""));       // 0 zeros (even)
		assertTrue(dfa.accepts("11"));     // 0 zeros
		assertFalse(dfa.accepts("0"));     // 1 zero
		assertTrue(dfa.accepts("010"));    // 2 zeros
		assertFalse(dfa.accepts("0100"));  // 3 zeros
		System.out.println("dfa4 accept pass");
	}

	@Test
	public void test4_4() {
		DFA dfa = dfa4();
		String expStr = "Q = { even_0 odd_0 }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "\t\t0\t1\n"
				+ "even_0\todd_0\teven_0\n"
				+ "odd_0\teven_0\todd_0\n"
				+ "q0 = even_0\n"
				+ "F = { even_0 }";
		assertTrue(dfa.toString().replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa4 toString pass");
	}

	@Test
	public void test4_5() {
		DFA dfa = dfa4();
		DFA swapped = dfa.swap('0', '1');
		assertTrue(swapped.accepts("11"));  // 2 ones (even)
		assertFalse(swapped.accepts("1"));  // 1 one (odd)
		System.out.println("dfa4Swap accept pass");
	}

	//------------------- dfa5 tests ----------------------//
	private DFA dfa5() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("S")); // Start
		assertTrue(dfa.addState("A"));
		assertTrue(dfa.addState("B")); // Final
		assertTrue(dfa.setStart("S"));
		assertTrue(dfa.setFinal("B"));

		// Transitions
		assertTrue(dfa.addTransition("S", "A", '0'));
		assertTrue(dfa.addTransition("S", "S", '1'));
		assertTrue(dfa.addTransition("A", "A", '0'));
		assertTrue(dfa.addTransition("A", "B", '1'));
		assertTrue(dfa.addTransition("B", "A", '0'));
		assertTrue(dfa.addTransition("B", "S", '1'));

		return dfa;
	}

	@Test
	public void test5_1() {
		DFA dfa = dfa5();
		System.out.println("dfa5 instantiation pass");
	}

	@Test
	public void test5_2() {
		DFA dfa = dfa5();
		assertTrue(dfa.isStart("S"));
		assertTrue(dfa.isFinal("B"));
		assertFalse(dfa.isFinal("A"));
		assertEquals(Set.of('0', '1'), dfa.getSigma());
		System.out.println("dfa5 correctness pass");
	}

	@Test
	public void test5_3() {
		DFA dfa = dfa5();
		assertTrue(dfa.accepts("01"));      // Ends with 01
		assertTrue(dfa.accepts("101"));     // Ends with 01
		assertFalse(dfa.accepts("0"));      // Too short
		assertFalse(dfa.accepts("010"));    // Ends with 0
		assertFalse(dfa.accepts("111"));    // Ends with 1
		System.out.println("dfa5 accept pass");
	}

	@Test
	public void test5_4() {
		DFA dfa = dfa5();
		String expStr = "Q = { S A B }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "\t\t0\t1\n"
				+ "S\tA\tS\n"
				+ "A\tA\tB\n"
				+ "B\tA\tS\n"
				+ "q0 = S\n"
				+ "F = { B }";
		assertTrue(dfa.toString().replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa5 toString pass");
	}

	@Test
	public void test5_5() {
		DFA dfa = dfa5();
		DFA swapped = dfa.swap('0', '1');
		assertTrue(swapped.accepts("10"));  // Original "01" becomes "10" after swap
		assertFalse(swapped.accepts("01")); // Now invalid
		System.out.println("dfa5Swap instantiation pass");
	}

	@Test
	public void test5_6() {
		DFA dfa = dfa5();
		DFA swapped = dfa.swap('0', '1');
		assertTrue(swapped.accepts("110")); // Original "101" → "010"
		assertFalse(swapped.accepts("101"));
		System.out.println("dfa5Swap accept pass");
	}
	
}

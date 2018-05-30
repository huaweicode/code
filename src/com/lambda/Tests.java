package com.lambda;

import java.util.*;
import java.util.stream.*;

import org.junit.Test;

public class Tests {
	
	List<Integer> numbers = Arrays.asList(1,2,1,2,5,7,6,2,8,3,89);
	
//	@Test
	public void order() {
		Set<Integer> set = new HashSet<>(numbers);
		set.stream().map(x -> x +1).collect(Collectors.toList()).forEach(s -> System.out.println(s));
	}
	
	@Test
	public void toSet() {
		Set<Integer> set = numbers.stream().collect(Collectors.toCollection(TreeSet::new));
		set.forEach(s -> System.out.println(s));
	}
}

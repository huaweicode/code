package com.lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.Test;


public class LambdaTest {
	
//	@Test
	public void oldRunnable() {
		new Thread(()->System.out.println("hello word")).start();
		
	}
	
//	@Test
	public void newRunnable() {
		new Thread(()->System.out.println("hello world")).start();
	}
	
//	@Test
	public void iterTest() {
		List<String> list = Arrays.asList("java","C","C++","C#","python","ruby","js","hadoop");
		for (String string : list) {
			System.out.println(string);
		}
	}
//	@Test
	public void iterLam() {
		List<String> list = Arrays.asList("hadoop","yarn","hive","pig","hbase","storm","zookeeper","kafka");
		list.forEach(i->System.out.println(i));
	}
//	@Test
	public void map() {
		List<Double> cost = Arrays.asList(10.0,20.0,30.0);
		cost.stream().map(x-> x+x*0.5).forEach(x->System.out.println(x));
	}
//	@Test
	public void reduce() {
		List<Double> cost = Arrays.asList(10.0,20.0,30.0);
		double all = cost.stream().map(x->x+x*0.05).reduce((sum,x)->sum+x).get();
		System.out.println(all);
	}
	
//	@Test 
	public void filter() {
		List<Double> cost = Arrays.asList(10.0,20.0,30.0);
		cost= cost.stream().filter(x-> x>20).collect(Collectors.toList());
		cost.stream().forEach(x->System.out.println(x));
	}
	
	public static void filterTest(List<String> languaes,Predicate<String> condition) {
		languaes.stream().filter(x-> condition.test(x)).forEach(x->System.out.println(x+" "));
	}
	
	
	class Student{
	    private String name;
	    private Double score;
	  
	    public Student(String name, Double score) {
	        this.name = name;
	        this.score = score;
	    }
	  
	    public String getName() {
	        return name;
	    }
	  
	    public Double getScore() {
	        return score;
	    }
	  
	    public void setName(String name) {
	        this.name = name;
	    }
	  
	    public void setScore(Double score) {
	        this.score = score;
	    }
	  
	    @Override
	    public String toString() {
	        return "{"
	                + "\"name\":\"" + name + "\""
	                + ", \"score\":\"" + score + "\""
	                + "}";
	    }
	}
//	@Test
	public void test1(){
	    List<Student> studentList = new ArrayList<Student>();
	    studentList.add(new Student("stu1",100.0));
    	studentList.add(new Student("stu2",97.0));
    	studentList.add(new Student("stu3",96.0));
    	studentList.add(new Student("stu4",95.0));
	    Collections.sort(studentList, new Comparator<Student>() {
	        @Override
	        public int compare(Student o1, Student o2) {
	            return Double.compare(o1.getScore(),o2.getScore());
	        }
	    });
	    System.out.println(studentList);
	}
	
//	@Test
	public void test() {
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(new Student("stu1",100.0));
    	studentList.add(new Student("stu2",97.0));
    	studentList.add(new Student("stu3",96.0));
    	studentList.add(new Student("stu4",95.0));
    	
    	Collections.sort(studentList,(s1,s2)->Double.compare(s1.getScore(), s2.getScore()));
    	System.out.println(studentList);
	}
	
	
	@Test
	public void function() {
		final List<BigDecimal> prices = Arrays.asList(new BigDecimal("10"),
				new BigDecimal("30"),
				new BigDecimal("17"),
				new BigDecimal("20"),
				new BigDecimal("15"),
				new BigDecimal("18"),
				new BigDecimal("45"),
				new BigDecimal("12"));
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : prices) {
			if (bigDecimal.compareTo(BigDecimal.valueOf(20))>0) {
				total = total.add(bigDecimal.multiply(BigDecimal.valueOf(0,9)));
			}
			System.out.println(total);
		}
		
	}
	
	
//	public static void main(String[] args) {
////		List<String> list = Arrays.asList("hadoop","yarn","hive","pig","hbase","storm","zookeeper","kafka");
////		filterTest(list, x->x.startsWith("h"));
//		
//	}
}

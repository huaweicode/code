package com.lambda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.codehaus.groovy.syntax.Numbers;
import org.junit.Test;
import org.python.modules.math;
import org.python.modules.itertools.count;
import com.sun.org.apache.bcel.internal.generic.NEW;
import sun.net.www.content.audio.x_aiff;

public class StreamSoluation {
	
	static List<Article> articles = null;
	static List<Integer> maths = null;
	static List<Integer> maths1 = null;
	static List<Integer> maths2 = null;
	static List<Works> list = null;
	static List<Employee> cityList = null;
	static {
		List<String> array = Arrays.asList("java","C","C++","C#","ruby","js");
		Works works1 = new Works("hadoop", "100", "2018");
		Works works2 = new Works("hadoop", "100", "2018");
		Works works3 = new Works("hadoop", "100", "2018");
		list = Arrays.asList(works1,works2,works3);
		
		articles = Arrays.asList(new Article("books","au_zhangsan","china",list),
				new Article("hadoop", "tianqi","enegland", list),
				new Article("pig", "au_zhaoliu","enegland", list),
				new Article("strom", "au_zhaoliu","enegland", list),
				new Article("spark", "zhuba","enegland", list),
				new Article("names", "zhangsan","america", list));	
		maths = Arrays.asList(1,2,3,4,5,6,7,8,9,
				1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,
				1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,
				1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,333);
		maths1 = Arrays.asList(1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,111);
		maths2 = Arrays.asList(1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,222);
		cityList = Arrays.asList(new Employee("zhangsan", "beijing", 300),
				new Employee("lisi", "shanghai", 150),
				new Employee("wangwu", "tianjin", 2),
				new Employee("zhaoliu", "shanghai", 200),
				new Employee("tianqi", "tianjin", 21),
				new Employee("zhuba", "beijing", 380));
	}
	
//	@Test
	public void getCountry() {
		Set<String> set = articles.stream()
				.filter(x -> x.getAuthor().equals("au_"))
				.map(s->s.getConuntry()).collect(Collectors.toSet());
		set.forEach(x->System.out.println(x));
	}
	
//	@Test
	public void getFirstJavaArticle() {
		for (Article article : articles) {
			if (article.getTags().contains("java")) {
				System.out.println(article.getAuthor());
			}
		}
	}
	
//	@Test
	public void getStream() {
//		articles.stream().filter(x->x.getTags().contains("java"))
//		.collect(Collectors.toList())
//		.forEach(s->System.out.println(s.getAuthor()));
//		Optional<Article> a = articles.stream().filter(x->x.getTags().contains("java")).findFirst();
//		System.out.println(a.get().getAuthor());
		articles.stream().
		filter(x->x.getTags().contains("java")).
		collect(Collectors.toList()).
		forEach(s->System.out.println(s.getAuthor()));
	}
	
//	@Test
	public void groupByAuther() {
		Map<String, List<Article>> result = new HashMap<>();
		for (Article article : articles) {
			if (result.containsKey(article.getAuthor())) {
				result.get(article.getAuthor()).add(article);
			} else {
				ArrayList<Article> articles = new ArrayList<>();
				articles.add(article);
				result.put(article.getAuthor(), articles);
			}
		}
	}	
	
//	@Test
	public void groupAuther() {
		Map<String, List<Article>> result = 
				articles.stream().collect(Collectors.groupingBy(Article::getAuthor));
	}
	
//	@Test
	public void getDistinctTags() {
		/*Set<String> result = new HashSet<>();
		for (Article article : articles) {
			result.addAll(article.getTags());
		}
		System.out.println(result.size());*/
	}
	
//	@Test
	public void getDistinct() {
		/*Set<String> set = 
				articles.stream().
				flatMap(articles->articles.getTags().stream())
				.collect(Collectors.toSet());
		System.out.println(set.size());*/
	}
	
//	@Test
	public void lambda() {
		String name = "123";
		//name = "567";
		Map<String, Integer> map = new HashMap<String,Integer>();
		Map<String, String> hashmap = new HashMap<>();
		new Thread(()->System.out.println(name)).start();
		
//		Predicate<T>
	}
	
//	@Test
	public void count() {
//		long counts = articles.stream().filter(x->x.getAuthor().equals("zhangsan")).count();
//		System.out.println(counts);
		//System.out.println(getAuther());
		List<String> collected = Stream.of("a", "b", "hello")
				.map(string -> string.toUpperCase())
				.collect(Collectors.toList());
		collected.forEach(s -> System.out.println(s));
	}
	
//	@Test
	public void min() {
		Integer a = maths.stream().min(Comparator.comparing(s -> s)).get();
		System.out.println(a);
		Integer b = maths.stream().max(Comparator.comparing(s->s)).get();
		System.out.println(b);
		Integer c = maths.stream().reduce(0, (sum,element)->sum+element);
	    System.out.println(c);
	}
	
//	@Test
	private Long getAuther() {
		Long count = articles.stream().filter(x -> {
			System.out.println(x.getTitle());
			return x.getAuthor().equals("zhangsan");
		}).count();
		return count;
	}
//	@Test
	public void flatMap() {
	    Optional<Integer> result = Stream.of(maths,maths1,maths2)
		.flatMap(numbers -> numbers.stream())
		.max(Comparator.comparing(s->s));
	    System.out.println(result.get());
	}
	
//	@Test
	public void map() {
		list.stream().map(x->x+"s")
		.collect(Collectors.toList())
		.forEach(s->System.out.println(s));
	}
	
//	@Test
	public void reduce() {
		Optional<Integer> optional = maths.stream().reduce((result,item) ->{
			if (item>3) {
				result += item;
			}
			return result;
		});
		System.out.println(optional.get());
	}
//	@Test
	public void length() {
		Set<String> set = articles.stream()
				.filter(article -> article.getAuthor().startsWith("au_"))
				.map(article ->article.getConuntry())
				.collect(Collectors.toSet());
		set.forEach(s->System.out.println(s));
	}
	
//	@Test
	public void sum() {
		AtomicInteger count = new AtomicInteger(0);
		maths.forEach(a -> count.incrementAndGet());
		System.out.println(count);
	}
	
//	@Test
	public void option() {
//		Optional<String> optional = Optional.of("");
		Optional<String> optional = Optional.empty();
//		Optional<String> optional = Optional.ofNullable(null);
		optional.orElse("123");
 		System.out.println(optional.isPresent());
		System.out.println(optional.get());
		
	}
//	@Test
	public void get() {
//		articles.forEach(Article::getAuthor);
		Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		List<String> list = set.stream().collect(Collectors.toList());
		System.out.println(list.size());
		
	}
//	@Test
	public void date() {
	  String time = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
	  System.out.println(time);
	}
	
//	@Test
	public void group() {
//		Map<String,List<Employee>> map = cityList.stream()
//				.collect(Collectors.groupingBy(Employee::getCity));
//		Map<String, Long> map = 
//				cityList.stream()
//				.collect(Collectors
//						.groupingBy(Employee::getCity,Collectors.counting()));
//		Map<String, Double> map = 
//				cityList.stream().collect(Collectors.groupingBy(
//						Employee::getCity,
//						Collectors.averagingInt(Employee::getSales)));
//		Map<Boolean,Map<String, Long>> map = 
//				cityList.stream().collect(
//						Collectors.partitioningBy(
//								s -> s.getSales()>150,
//								Collectors.groupingBy(
//										Employee::getCity,Collectors.counting())));
		
		Map<Boolean, Map<String, Long>> map = 
				cityList.stream().
				collect(Collectors.partitioningBy(s->s.getSales()>150,
						Collectors.groupingBy(Employee::getCity,Collectors.counting())));
		System.out.println(map.size());
	}
	
	@Test
	public void join() {
		String resulst = articles.stream().map(Article :: getTitle)
		.collect(Collectors.joining(",","[","]"));
		System.out.println(resulst);
	}
	
}

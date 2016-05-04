package util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import persistence.Bigram;

public class NGramGenerator {
	public NGramGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public static Set<Bigram> generateBigrams(List<String> word_list) {
		Set<Bigram> result = new HashSet<>();
		for (int i = 0; i < word_list.size() - 1; ++i) {
			Bigram be = new Bigram(word_list.get(i), word_list.get(i+1));
			result.add(be);
		}
		return result;
	}
}

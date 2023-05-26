package com.wtongze.lotteryapi.data;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Generator {
    private final Random secureRandom = new SecureRandom();

    public List<Integer> intBetween(Integer count, Integer lowerBound, Integer upperBound) {
        var intIter = secureRandom.ints(lowerBound, upperBound + 1).iterator();
        var numSet = new HashSet<Integer>();
        while (numSet.size() < count) {
            Integer num = intIter.next();
            numSet.add(num);
        }
        return numSet.stream().sorted().toList();
    }

    public List<Integer> intBetween(Integer count, Integer upperBound) {
        return this.intBetween(count, 1, upperBound);
    }
}

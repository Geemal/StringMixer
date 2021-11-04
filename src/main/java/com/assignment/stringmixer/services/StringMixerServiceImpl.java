package com.assignment.stringmixer.services;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class StringMixerServiceImpl implements StringMixerService {

    @Override
    public String mix(String s1, String s2) {
        String[] strings = { s1, s2 };

        // To store frequencies of each lowercase letter appear in s1 and s2
        HashMap<Character, Integer> s1FrequencyMap = new HashMap<>();
        HashMap<Character, Integer> s2FrequencyMap = new HashMap<>();

        List<Map<Character, Integer>> frequecyMapList = new ArrayList<>();
        frequecyMapList.add(s1FrequencyMap);
        frequecyMapList.add(s2FrequencyMap);

        int j = 0;

        // Iterate through two strings and build frequency maps
        for (String s : strings) {
            for (char c : s.toCharArray()) {
                // Ignore uppercase letters and other characters
                if (!Character.isLowerCase(c)) {
                    continue;
                }
                Integer val = frequecyMapList.get(j).get(c);

                if (val != null) {
                    // Update frequency for existing letters
                    frequecyMapList.get(j).put(c, val + 1);
                } else {
                    // Add new entry
                    frequecyMapList.get(j).put(c, 1);
                }
            }
            j++;
        }

        // Create a superset of letter frequencies from s1 and s2
        HashMap<Character, Integer> mergedFrequencyMap = new HashMap<>(s1FrequencyMap);
        s2FrequencyMap.forEach((key, value) -> mergedFrequencyMap.merge(key, value,
                (v1, v2) -> v1.equals(v2) ? v1 : v1 > v2 ? v1 : v2));

        List<String> subStringList = new ArrayList<>();

        // Iterate through every letter frequency
        for (Map.Entry<Character, Integer> entry : mergedFrequencyMap.entrySet()) {
            Integer mergedFrequency = entry.getValue();
            Integer s1Frequency = s1FrequencyMap.get(entry.getKey());
            Integer s2Frequency = s2FrequencyMap.get(entry.getKey());

            // Ignore letters with occurrences less than or equal to 1
            if (mergedFrequency <= 1) {
                continue;
            }
            // Generate substring for each letter
            if (s2Frequency == null) {
                subStringList.add("1:" + entry.getKey().toString().repeat(s1Frequency));
            } else if (s1Frequency == null) {
                subStringList.add("2:" + entry.getKey().toString().repeat(s2Frequency));
            } else if (s1Frequency.equals(s2Frequency)) {
                subStringList.add("=:" + entry.getKey().toString().repeat(s1Frequency));
            } else if (s1Frequency > s2Frequency) {
                subStringList.add("1:" + entry.getKey().toString().repeat(s1Frequency));
            } else {
                subStringList.add("2:" + entry.getKey().toString().repeat(s2Frequency));
            }

        }
        // Sort list of substrings in decreasing order of their length and then alphabetically
        subStringList.sort(Comparator.comparingInt(String::length).reversed().thenComparing(Comparator.naturalOrder()));
       return  String.join("/", subStringList);

    }
}

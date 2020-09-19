package com.hx_ai.nlp.simple.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dzkan on 2016/11/19.
 * 收音机属性
 */
public class RadioProperties {
    private Map<String, List<String>> radios;
    private Map<String, List<String>> operations;

    private void setupRadios() {
        radios = new HashMap<>();
        try {
//            URI uri = this.getClass().getResource("/radio.txt").toURI();
//            List<String> lines = Files.readAllLines(Paths.get(uri), StandardCharsets.UTF_8);
            InputStream radioStream = this.getClass().getResourceAsStream("/radio.txt");
            List<String> lines = new BufferedReader(new InputStreamReader(radioStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] radio = line.split(",");
                radios.put(radio[0], Arrays.asList(radio));
/*
                if (radio.length > 1) {
                    radios.put(radio[0], Arrays.asList(radio));
                } else {
                    radios.put(radio[0], new ArrayList<>());
                }
*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupOperations() {
        operations = new HashMap<>();
        String[] pauses = {"暂停", "等一下", "稍等", "先停一下"};
        operations.put("PAUSE", Arrays.asList(pauses));
        String[] stops = {"不想听", "不要听", "不想要听", "不要说了","不要讲了", "别说了","停止", "停", "停下来", "停一下"};
        operations.put("STOP", Arrays.asList(stops));
    }

    public Map<String, List<String>> getRadios() {
        return radios;
    }

    public Map<String, List<String>> getOperations() {
        return operations;
    }

    public RadioProperties() {
        setupRadios();
        setupOperations();
    }


}

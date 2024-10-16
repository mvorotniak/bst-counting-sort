package com.demo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Person implements Comparable<Person> {
    
    private Integer id;
    
    @Override
    public int compareTo(Person o) {
        return this.id.compareTo(o.id);
    }
}

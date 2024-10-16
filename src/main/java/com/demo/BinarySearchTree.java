package com.demo;

public interface BinarySearchTree<T extends Comparable<T>> {

    boolean insert(T element);
    
    boolean contains(T element);
    
    int size();

    void delete(T element);
}

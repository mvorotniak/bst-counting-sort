package com.demo;

import java.util.List;
import java.util.Objects;

public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;

    private int size = 0;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(List<T> elements) {
        RecursiveBinarySearchTree<T> binarySearchTree = new RecursiveBinarySearchTree<>();
        elements.forEach(binarySearchTree::insert);

        return binarySearchTree;
    }

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);

        Node<T> node = new Node<>(element);

        if (Objects.isNull(root)) {
            root = node;
            size++;
            return true;
        } else {
            return insert(root, node);
        }
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);

        if (Objects.isNull(root)) {
            return false;
        } else {
            return contains(root, element);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void delete(T element) {
        root = deleteRec(root, element);
    }

    private Node<T> deleteRec(Node<T> root, T element) {
        if (Objects.isNull(root)) {
            return root;
        }

        if (element.compareTo(root.value) < 0) {
            root.left = deleteRec(root.left, element);
        } else if (element.compareTo(root.value) > 0) {
            root.right = deleteRec(root.right, element);
        } else {
            if (Objects.isNull(root.left)) {
                return root.right;
            } else if (Objects.isNull(root.right)) {
                return root.left;
            }

            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        
        return root;
    }

    private T minValue(Node<T> root) {
        T minv = root.value;
        while (Objects.nonNull(root.left)) {
            minv = root.left.value;
            root = root.left;
        }

        return minv;
    }

    private boolean insert(Node<T> current, Node<T> node) {
        if (node.value.compareTo(current.value) > 0) {
            return insertRight(current, node);
        } else if (node.value.compareTo(current.value) < 0) {
            return insertLeft(current, node);
        } else {
            return false;
        }
    }

    private boolean insertRight(Node<T> current, Node<T> node) {
        if (Objects.isNull(current.right)) {
            current.right = node;
            size++;
            return true;
        } else {
            return insert(current.right, node);
        }
    }

    private boolean insertLeft(Node<T> current, Node<T> node) {
        if (Objects.isNull(current.left)) {
            current.left = node;
            size++;
            return true;
        } else {
            return insert(current.left, node);
        }
    }

    private boolean contains(Node<T> current, T element) {
        if (Objects.isNull(current)) {
            return false;
        }

        if (element.compareTo(current.value) > 0) {
            return contains(current.right, element);
        } else if (element.compareTo(current.value) < 0) {
            return contains(current.left, element);
        }

        return true;
    }

    private static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }
}

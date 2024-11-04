package com.demo;

import java.util.List;
import java.util.Objects;

public class AVLTree<T extends Comparable<T>> implements BinarySearchTree<T> {

	private Node<T> root;
	private int size = 0;

	public static <T extends Comparable<T>> AVLTree<T> of(List<T> elements) {
		final AVLTree<T> avlTree = new AVLTree<>();
		elements.forEach(avlTree::insert);
		return avlTree;
	}

	@Override
	public boolean insert(T element) {
		Objects.requireNonNull(element);
		if (this.contains(element)) {
			return false;
		}
		this.root = this.insert(this.root, element);
		this.size++;
		return true;
	}

	private Node<T> insert(Node<T> node, T element) {
		if (node == null) {
			return new Node<>(element);
		}

		if (element.compareTo(node.value) < 0) {
			node.left = this.insert(node.left, element);
		} else if (element.compareTo(node.value) > 0) {
			node.right = this.insert(node.right, element);
		}

		node.height = 1 + Math.max(this.height(node.left), this.height(node.right));

		final int balance = this.getBalance(node);

		if (balance > 1 && element.compareTo(node.left.value) < 0) {
			return this.rightRotate(node);
		}

		if (balance < -1 && element.compareTo(node.right.value) > 0) {
			return this.leftRotate(node);
		}

		if (balance > 1 && element.compareTo(node.left.value) > 0) {
			node.left = this.leftRotate(node.left);
			return this.rightRotate(node);
		}

		if (balance < -1 && element.compareTo(node.right.value) < 0) {
			node.right = this.rightRotate(node.right);
			return this.leftRotate(node);
		}

		return node;
	}

	private int height(Node<T> node) {
		return node == null ? 0 : node.height;
	}

	private int getBalance(Node<T> node) {
		return node == null ? 0 : this.height(node.left) - this.height(node.right);
	}

	private Node<T> rightRotate(Node<T> y) {
		final Node<T> x = y.left;
		final Node<T> T2 = x.right;

		x.right = y;
		y.left = T2;

		y.height = Math.max(this.height(y.left), this.height(y.right)) + 1;
		x.height = Math.max(this.height(x.left), this.height(x.right)) + 1;

		return x;
	}

	private Node<T> leftRotate(Node<T> x) {
		final Node<T> y = x.right;
		final Node<T> T2 = y.left;

		y.left = x;
		x.right = T2;

		x.height = Math.max(this.height(x.left), this.height(x.right)) + 1;
		y.height = Math.max(this.height(y.left), this.height(y.right)) + 1;

		return y;
	}

	@Override
	public boolean contains(T element) {
		return this.contains(this.root, element);
	}

	private boolean contains(Node<T> node, T element) {
		if (node == null) {
			return false;
		}

		if (element.compareTo(node.value) < 0) {
			return this.contains(node.left, element);
		} else if (element.compareTo(node.value) > 0) {
			return this.contains(node.right, element);
		}

		return true;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void delete(T element) {
		this.root = this.deleteRec(this.root, element);
		this.size--;
	}

	private Node<T> deleteRec(Node<T> node, T element) {
		if (node == null) {
			this.size++;
			return null;
		}

		if (element.compareTo(node.value) < 0) {
			node.left = this.deleteRec(node.left, element);
		} else if (element.compareTo(node.value) > 0) {
			node.right = this.deleteRec(node.right, element);
		} else {
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			}

			node.value = this.minValue(node.right);
			node.right = this.deleteRec(node.right, node.value);
		}

		node.height = Math.max(this.height(node.left), this.height(node.right)) + 1;

		final int balance = this.getBalance(node);

		if (balance > 1 && this.getBalance(node.left) >= 0) {
			return this.rightRotate(node);
		}

		if (balance > 1 && this.getBalance(node.left) < 0) {
			node.left = this.leftRotate(node.left);
			return this.rightRotate(node);
		}

		if (balance < -1 && this.getBalance(node.right) <= 0) {
			return this.leftRotate(node);
		}

		if (balance < -1 && this.getBalance(node.right) > 0) {
			node.right = this.rightRotate(node.right);
			return this.leftRotate(node);
		}

		return node;
	}

	private T minValue(Node<T> node) {
		T minValue = node.value;
		while (node.left != null) {
			minValue = node.left.value;
			node = node.left;
		}
		return minValue;
	}

	private static class Node<T> {
		private T value;
		private Node<T> left;
		private Node<T> right;
		private int height;

		public Node(T value) {
			this.value = value;
			this.height = 1;
		}
	}
}

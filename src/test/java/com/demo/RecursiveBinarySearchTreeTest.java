package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class RecursiveBinarySearchTreeTest {

	private static final int DATASETS_AMOUNT = 100;

	private final Random random = new Random();

	private final List<BinarySearchTree<Person>> bstDatasets = new ArrayList<>();

	@BeforeEach
	public void init() {
		IntStream.range(0, DATASETS_AMOUNT).forEach(i -> this.bstDatasets.add(this.generateRandomDataset()));
	}

	@Test
	void insertPerformanceTest() {
		long totalTime = 0L;
		final int tries = 1_000;

		for (int i = 0; i < tries; i++) {
			for (final BinarySearchTree<Person> bst : this.bstDatasets) {
				final long now = System.nanoTime();
				bst.insert(this.getRandomPerson());
				final long time = System.nanoTime() - now;
				System.out.printf("INSERT person in [%s] elems dataset: [%s] nano%n", bst.size(), time);
				totalTime += time;
			}
		}

		final double avg = (double) totalTime / (DATASETS_AMOUNT * tries);
		final DecimalFormat df = new DecimalFormat("#.#######");
		System.out.printf("[INSERT] AVERAGE TIME = %s nano = %s ms%n", avg, df.format(avg / 1_000_000));
	}

	@Test
	void searchPerformanceTest() {
		long totalTime = 0L;
		final int tries = 1_000;

		for (int i = 0; i < tries; i++) {
			for (final BinarySearchTree<Person> bst : this.bstDatasets) {
				final long now = System.nanoTime();
				bst.contains(this.getRandomPerson());
				final long time = System.nanoTime() - now;
				System.out.printf("SEARCH person in [%s] elems dataset: [%s] nano%n", bst.size(), time);
				totalTime += time;
			}
		}

		final double avg = (double) totalTime / (DATASETS_AMOUNT * tries);
		final DecimalFormat df = new DecimalFormat("#.#######");
		System.out.printf("[SEARCH] AVERAGE TIME = %s nano = %s ms%n", avg, df.format(avg / 1_000_000));
	}

	@Test
	void deletePerformanceTest() {
		long totalTime = 0L;
		final int tries = 1_000;

		for (int i = 0; i < tries; i++) {
			for (final BinarySearchTree<Person> bst : this.bstDatasets) {
				final long now = System.nanoTime();
				bst.delete(this.getRandomPerson());
				final long time = System.nanoTime() - now;
				System.out.printf("DELETE person in [%s] elems dataset: [%s] nano%n", bst.size(), time);
				totalTime += time;
			}
		}

		final double avg = (double) totalTime / (DATASETS_AMOUNT * tries);
		final DecimalFormat df = new DecimalFormat("#.#######");
		System.out.printf("[DELETE] AVERAGE TIME = %s nano = %s ms%n", avg, df.format(avg / 1_000_000));
	}

	private RecursiveBinarySearchTree<Person> generateRandomDataset() {
		final int min = 100;
		final int max = 10_000;
		final int amount = this.random.nextInt((max - min) + 1) + min;

		final List<Person> persons = IntStream.range(0, amount).mapToObj(i -> this.getRandomPerson()).toList();

		return RecursiveBinarySearchTree.of(persons);
	}

	private Person getRandomPerson() {
		return new Person(this.random.nextInt(10_000));
	}
}
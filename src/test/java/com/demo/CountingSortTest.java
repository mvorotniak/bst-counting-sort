package com.demo;

import org.junit.jupiter.api.Test;

import java.util.Random;

class CountingSortTest {

	@Test
	void littleArray_sort() {
		final int[] inputArray = this.generateIntArray(10);
		final long now = System.nanoTime();
		final int[] sorted = CountingSort.getSorted(inputArray);
		final long time = System.nanoTime() - now;
		System.out.printf("SHORT ARRAY time: [%s] nano%n", time);

	}

	@Test
	void largeArray_sort() {
		final int[] inputArray = this.generateIntArray(100_000);
		final long now = System.nanoTime();
		final int[] sorted = CountingSort.getSorted(inputArray);
		final long time = System.nanoTime() - now;
		System.out.printf("LARGE ARRAY time: [%s] nano%n", time);
	}

	private int[] generateIntArray(int length) {
		final int[] array = new int[length];
		final Random random = new Random();

		for (int i = 0; i < length; i++) {
			array[i] = random.nextInt(100);
		}

		return array;
	}
}

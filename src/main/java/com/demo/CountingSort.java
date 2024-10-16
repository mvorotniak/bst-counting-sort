package com.demo;

import java.util.Arrays;

public class CountingSort {

	/**
	 * Its time and space complexity are O(N+M), where N = length and M = maxValue,
	 * so it's not efficient with large arrays. Requires elements to be mapped to
	 * integers, so will not perform well with complex objects. Requires additional
	 * arrays, which makes it bad memory usage.
	 *
	 */
	public static int[] getSorted(int[] inputArray) {
		final int length = inputArray.length;

		// Find the maximum value in the input array
		final int maxValue = Arrays.stream(inputArray).max().orElse(0);

		// Create and populate the count array
		final int[] countArray = new int[maxValue + 1];
		for (final int num : inputArray) {
			countArray[num]++;
		}

		// Update the count array to hold cumulative sums
		for (int i = 1; i <= maxValue; i++) {
			countArray[i] += countArray[i - 1];
		}

		// Create the output array and place elements in sorted order
		final int[] outputArray = new int[length];
		for (int i = length - 1; i >= 0; i--) {
			outputArray[--countArray[inputArray[i]]] = inputArray[i];
		}

		return outputArray;
	}

}

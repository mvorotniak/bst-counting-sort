package com.demo;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1)
@Measurement(iterations = 2)
@Fork(value = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AVLTreeBenchmark {

	private AVLTree<Integer> tree;
	private final Random random = new Random();
	private int[] testData;

	@Param({"10000", "100000", "1000000"})
	public int size = 1_000_000;

	@Setup(Level.Trial)
	public void setup() {
		System.gc();
		this.testData = this.random.ints(this.size, 1, this.size + 1).toArray();
		this.tree = new AVLTree<>();
		for (final int value : this.testData) {
			this.tree.insert(value);
		}
	}

	@Benchmark
	public void testInsert() {
		this.measureMemoryUsage("Before Insert");
		this.tree.insert(this.random.nextInt(20000));
		this.measureMemoryUsage("After Insert");
	}

	@Benchmark
	public void testDelete() {
		final int valueToDelete = this.testData[this.random.nextInt(this.testData.length)];
		this.measureMemoryUsage("Before Delete");
		this.tree.delete(valueToDelete);
		this.measureMemoryUsage("After Delete");
	}

	@Benchmark
	public void testSearch() {
		final int valueToSearch = this.testData[this.random.nextInt(this.testData.length)];
		this.measureMemoryUsage("Before Search");
		this.tree.contains(valueToSearch);
		this.measureMemoryUsage("After Search");
	}

	@TearDown(Level.Trial)
	public void tearDown() {
		this.tree = null;
	}

	private void measureMemoryUsage(String operation) {
		System.gc(); // Suggest garbage collection for a clearer measurement
		final long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println(operation + " - Memory used: " + (usedMemory / (1024 * 1024)) + " MB");
	}
}

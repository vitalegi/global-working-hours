package it.vitalegi.globalworkinghours.util;

public class Pair<K, V> {

	K value1;
	V value2;

	public Pair(K key, V value) {
		super();
		this.value1 = key;
		this.value2 = value;
	}

	public K getValue1() {
		return value1;
	}

	public void setValue1(K value1) {
		this.value1 = value1;
	}

	public V getValue2() {
		return value2;
	}

	public void setValue2(V value2) {
		this.value2 = value2;
	}

}

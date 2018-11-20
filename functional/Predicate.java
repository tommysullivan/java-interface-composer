package com.classdojo.android.utility.functional;

@FunctionalInterface
public interface Predicate<T> {
	Boolean test(T item);
}

package com.classdojo.android.utility.functional;

@FunctionalInterface
public interface Consumer2<T1, T2> {
	void invoke(T1 arg1, T2 arg2);
}
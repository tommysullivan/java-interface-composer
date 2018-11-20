package com.classdojo.android.utility.list;

public interface ImmutableLists {
	<T> ImmutableList<T> newEmptyList();
	<T> ImmutableList<T> newList(T ...items);
	<T> ImmutableList<T> newNonEmptyList(T head, ImmutableList<T> items);
}
package com.classdojo.android.utility.list;

public class ImmutableListsImpl implements ImmutableLists {
	public <T> ImmutableList<T> newEmptyList() {
		return new EmptyList<>(this);
	}

	public <T> ImmutableList<T> newList(T[] items) {
		ImmutableList listSoFar = newEmptyList();
		for(T item : items) {
			listSoFar = listSoFar.insertAtBeginning(item);
		}
		return listSoFar;
	}

	public <T> ImmutableList<T> newNonEmptyList(T head, ImmutableList<T> items) {
		return new NonEmptyList<>(head, items, this);
	}
}

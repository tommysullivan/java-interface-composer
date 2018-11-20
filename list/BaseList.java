package com.classdojo.android.utility.list;

import kotlin.jvm.functions.Function1;

public abstract class BaseList<T extends Object> implements ImmutableList<T> {
	protected final ImmutableLists immutableLists;

	public BaseList(ImmutableLists immutableLists) {
		this.immutableLists = immutableLists;
	}

	public ImmutableList<T> insertAtBeginning(T item) {
		return immutableLists.newNonEmptyList(item, this);
	}

	public <S> ImmutableList<S> flatMapArray(Function1<T, S[]> mapToSubList) {
		return flatMap(item -> immutableLists.newList(mapToSubList.invoke(item)));
	}
}
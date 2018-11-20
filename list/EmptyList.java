package com.classdojo.android.utility.list;

import com.classdojo.android.utility.functional.Consumer1;
import com.classdojo.android.utility.functional.Consumer2;
import com.classdojo.android.utility.functional.Predicate;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class EmptyList<T extends Object> extends BaseList<T> {
	public EmptyList(ImmutableLists immutableLists) {
		super(immutableLists);
	}

	public Integer length() {
		return 0;
	}
	public <S> ImmutableList<S> map(Function1<T, S> mapper) {
		return immutableLists.newEmptyList();
	}
	public void forEach(Consumer2<T, Integer> handler) {}
	public void forEach(Consumer1<T> handler) {}
	public String join(String delimiter) { return ""; }
	public Boolean isEmpty() { return true; }
	public <S> S reduce(S identity, Function2<S, T, S> reducer) { return identity; }
	public <S> S reduceRight(S identity, Function2<S, T, S> reducer) { return identity; }
	public Object[] toArray() { return new Object[0]; }
	public ImmutableList<T> append(T item) { return insertAtBeginning(item); }
	public Boolean contains(T item) { return false; }
	public ImmutableList<T> where(Predicate<T> predicate) { return this; }
	public Boolean all(Predicate<T> predicate) { return true; }
	public ImmutableList<T> notIn(ImmutableList<T> someOtherList) { return this; }
	public <S> S forEachItemTryOperationUntilOneSucceeds(Function1<T, S> operationThatMayThrowException) {
		throw new Error("Got to end of list of operations before a successful operation (without exception thrown)");
	}
	public <S> ImmutableList<S> flatMap(Function1<T, ImmutableList<S>> mapToSubList) { return immutableLists.newEmptyList(); }
	public <S> ImmutableList<Pair<T, S>> zip(ImmutableList<S> otherList) { return immutableLists.newEmptyList(); }

	public T head() {
		throw new RuntimeException("Cannot retrieve head of empty list");
	}

	public ImmutableList<T> tail() {
		throw new RuntimeException("Cannot retrieve tail of empty list");
	}
}
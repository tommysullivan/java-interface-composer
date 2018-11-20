package com.classdojo.android.utility.list;

import com.classdojo.android.utility.functional.Consumer1;
import com.classdojo.android.utility.functional.Predicate;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public interface ImmutableList<T extends Object> {
	ImmutableList<T> insertAtBeginning(T item);
	ImmutableList<T> append(T item);
	Integer length();
	void forEach(Consumer1<T> handler);
	<S> ImmutableList<S> map(Function1<T, S> mapper);
	String join(String delimiter);
	Boolean isEmpty();
	<S> S reduce(S identity, Function2<S, T, S> reducer);
	<S> S reduceRight(S identity, Function2<S, T, S> reducer);
	Object[] toArray();
	Boolean contains(T item);
	ImmutableList<T> where(Predicate<T> predicate);
	Boolean all(Predicate<T> predicate);
	ImmutableList<T> notIn(ImmutableList<T> someOtherList);
	<S> S forEachItemTryOperationUntilOneSucceeds(Function1<T, S> operationThatMayThrowException);
	<S> ImmutableList<S> flatMap(Function1<T, ImmutableList<S>> mapToSubList);
	<S> ImmutableList<S> flatMapArray(Function1<T, S[]> mapToSubList);
	<S> ImmutableList<Pair<T, S>> zip(ImmutableList<S> otherList);
	T head();
	ImmutableList<T> tail();

	default String join() {
		return join("");
	}

	default Boolean contain(T item) { return contains(item); }
	default Boolean doesNotContain(T item) { return !contain(item); }
	default Boolean doNotContain(T item) { return !contain(item); }
	default Boolean notEmpty() { return !isEmpty(); }

	default ImmutableList<T> append(ImmutableList<T> items) {
		return items.reduce(
			this,
			(listSoFar, itemToAppend) -> listSoFar.append(itemToAppend)
		);
	}

	default Boolean none(Predicate<T> predicate) {
		return all(t -> !predicate.test(t));
	}

	default Boolean containsExactlyTheSameItemsAs(ImmutableList<T> otherList) {
		return containsAll(otherList) && otherList.containsAll(this);
	}

	default Boolean containsAll(ImmutableList<T> items) {
		return all(items::contain);
	}
}

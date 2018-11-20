package com.classdojo.android.utility.list;

import com.classdojo.android.utility.functional.Consumer1;
import com.classdojo.android.utility.functional.Predicate;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class NonEmptyList<T extends Object> extends BaseList<T> {
	private final T _head;
	private final ImmutableList<T> _tail;

	public NonEmptyList(T head, ImmutableList<T> tail, ImmutableLists immutableLists) {
		super(immutableLists);
		this._head = head;
		this._tail = tail;
	}

	public T head() { return _head; }
	public ImmutableList<T> tail() { return _tail; }

	public Boolean isEmpty() {
		return false;
	}

	public Integer length() {
		return 1 + _tail.length();
	}

	public <S> ImmutableList<S> map(Function1<T, S> mapper) {
		return immutableLists.newNonEmptyList(mapper.invoke(_head), _tail.map(mapper));
	}

	public void forEach(Consumer1<T> handler) {
		handler.invoke(_head);
		_tail.forEach(handler);
	}

	public String join(String delimiter) {
		String remainder = _tail.isEmpty()
			? ""
			: delimiter + _tail.join(delimiter);

		return _head.toString() + remainder;
	}

	public <S> S reduceRight(S identity, Function2<S, T, S> reducer) {
		return reducer.invoke(_tail.reduce(identity, reducer), _head);
	}

	public <S> S reduce(S identity, Function2<S, T, S> reducer) {
		return _tail.reduce(reducer.invoke(identity, _head), reducer);
	}

	public Object[] toArray() {
		List<T> arrayList = new ArrayList<>();
		forEach(arrayList::add);
		return arrayList.toArray();
	}

	public ImmutableList<T> append(T item) {
		return immutableLists.newNonEmptyList(_head, _tail.append(item));
	}

	public Boolean contains(T item) {
		return _head.equals(item) || _tail.contains(item);
	}

	public ImmutableList<T> where(Predicate<T> predicate) {
		ImmutableList<T> otherMatches = _tail.where(predicate);
		return predicate.test(_head)
			? immutableLists.newNonEmptyList(_head, otherMatches)
			: otherMatches;
	}

	public Boolean all(Predicate<T> predicate) {
		return where(predicate).length().equals(length());
	}

	public ImmutableList<T> notIn(ImmutableList<T> someOtherList) {
		return where(item -> !someOtherList.contains(item));
	}

	public <S> S forEachItemTryOperationUntilOneSucceeds(Function1<T, S> operationThatMayThrowException) {
		try {
			return operationThatMayThrowException.invoke(_head);
		}
		catch (RuntimeException e) {
			return _tail.forEachItemTryOperationUntilOneSucceeds(operationThatMayThrowException);
		}
	}

	public <S> ImmutableList<S> flatMap(Function1<T, ImmutableList<S>> mapToSubList) {
		ImmutableList<ImmutableList<S>> listOfLists = map(mapToSubList);
		ImmutableList<S> identity = immutableLists.newEmptyList();
		return listOfLists.reduce(
			identity,
			(flattenedSoFar, currentListToFlatten) -> flattenedSoFar.append(currentListToFlatten)
		);
	}

	public <S> ImmutableList<Pair<T, S>> zip(ImmutableList<S> otherList) {
		return immutableLists.newNonEmptyList(new Pair<>(_head, otherList.head()), _tail.zip(otherList.tail()));
	}
}

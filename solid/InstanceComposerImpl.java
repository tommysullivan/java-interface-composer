package com.classdojo.android.utility.solid;

import com.classdojo.android.utility.list.ImmutableList;
import com.classdojo.android.utility.list.ImmutableLists;

import java.lang.reflect.Proxy;

public class InstanceComposerImpl implements InstanceComposer {
	private final ImmutableLists immutableLists;
	private final ValidatesCombinedInstancesImplementOutputInterface validator;

	public InstanceComposerImpl(ImmutableLists immutableLists, ValidatesCombinedInstancesImplementOutputInterface validator) {
		this.immutableLists = immutableLists;
		this.validator = validator;
	}

	public <T> T combineImplementations(Class<T> expectedOutputInterface, Object... instances) {
		ImmutableList<Object> instanceList = immutableLists.newList(instances);
		validator.validate(expectedOutputInterface, instanceList);
		return (T) Proxy.newProxyInstance(
			expectedOutputInterface.getClassLoader(),
			new Class[] { expectedOutputInterface },
			new SerialInvocationHandler<>(instanceList)
		);
	}
}
package com.classdojo.android.utility.solid;

import com.classdojo.android.utility.list.ImmutableList;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SerialInvocationHandler<T extends Object> implements InvocationHandler {
	private final ImmutableList<T> instances;

	public SerialInvocationHandler(ImmutableList<T> instances) {
		this.instances = instances;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		for(Object instance : instances.toArray()) {
			try {
				return instance.getClass().getMethod(methodName, parameterTypes).invoke(instance, args);
			}
			catch(NoSuchMethodException e) {

			}
		}
		throw new RuntimeException("Serial invocation failed to find match for method "+methodName);
	}
}
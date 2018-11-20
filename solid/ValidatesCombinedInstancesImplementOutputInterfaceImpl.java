package com.classdojo.android.utility.solid;

import com.classdojo.android.utility.functional.Predicate;
import com.classdojo.android.utility.list.ImmutableList;
import com.classdojo.android.utility.list.ImmutableLists;

import java.lang.reflect.Method;

public class ValidatesCombinedInstancesImplementOutputInterfaceImpl implements ValidatesCombinedInstancesImplementOutputInterface {
	private final ImmutableLists immutableLists;

	public ValidatesCombinedInstancesImplementOutputInterfaceImpl(ImmutableLists immutableLists) {
		this.immutableLists = immutableLists;
	}

	public <T> void validate(Class<T> expectedOutputInterface, ImmutableList<Object> instances) {
		ImmutableList<Class<?>> types = instances.map(instance -> instance.getClass());
		ImmutableList<Method> implementedMethods = types.flatMapArray(type -> type.getMethods());
		ImmutableList<Method> requiredMethods = immutableLists.newList(expectedOutputInterface.getMethods());
		ImmutableList<Method> missingMethods = requiredMethods.where(requiredMethodIsNotCompatibleWithAny(implementedMethods));

		if(missingMethods.notEmpty()) {
			throw new RuntimeException(
				"All methods of target type "
				+ expectedOutputInterface.getName()
				+ " must be implemented by the union of the "
				+ types.length()
				+ " types: "
				+ types.map(type -> type.getName()).join(", ")
				+ "... but they are not. The following methods were not found: "
				+ missingMethods.join(", ")
			);
		}
	}

	private Predicate<Method> requiredMethodIsNotCompatibleWithAny(ImmutableList<Method> possiblyCompatibleMethods) {
		return (desiredMethod) -> possiblyCompatibleMethods
			.where(possiblyCompatibleMethod -> methodsSignaturesAreClose(possiblyCompatibleMethod, desiredMethod))
			.none(isCompatibleWith(desiredMethod));
	}

	private Boolean methodsSignaturesAreClose(Method m1, Method m2) {
		return m1.getName().equals(m2.getName()) &&
			m1.getParameterTypes().length == m2.getParameterTypes().length
			&& m1.getReturnType() == m2.getReturnType();
	}

	private Predicate<Method> isCompatibleWith(Method desiredMethod) {
		return (otherMethod) -> signaturesAreCompatible(desiredMethod, otherMethod);
	}

	private Boolean signaturesAreCompatible(Method desiredMethod, Method possibleMethodToInvoke) {
		ImmutableList<Class<?>> paramTypesOfPossibleMethodToInvoke =  immutableLists.newList(possibleMethodToInvoke.getParameterTypes());
		ImmutableList<Class<?>> paramTypesOfDesiredMethod =  immutableLists.newList(desiredMethod.getParameterTypes());
		return paramTypesOfPossibleMethodToInvoke
			.zip(paramTypesOfDesiredMethod)
			.all(paramTypePair -> paramTypePair.component1().isAssignableFrom(paramTypePair.component2())); //TODO: Is this backwards?
	}
}
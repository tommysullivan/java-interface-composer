package com.classdojo.android.utility.solid;

public class ValidatesAssignabilityImpl implements ValidatesAssignability {
	public void validate(Class<?> supposedNarrowInterface, Class<?> supposedWideInterface) {
		if(!supposedWideInterface.isAssignableFrom(supposedNarrowInterface)) {
			throw new InvalidInheritanceRelationshipError(
				supposedWideInterface.getName()
				+ " must be assignable from "
				+ supposedNarrowInterface.getName()
			);
		}
	}
}

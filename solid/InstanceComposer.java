package com.classdojo.android.utility.solid;

public interface InstanceComposer {
	<T> T combineImplementations(Class<T> expectedOutputInterface, Object... instances);
}
package com.classdojo.android.utility.solid;

import com.classdojo.android.utility.list.ImmutableList;

interface ValidatesCombinedInstancesImplementOutputInterface {
	<T> void validate(Class<T> expectedOutputInterface, ImmutableList<Object> instances);
}

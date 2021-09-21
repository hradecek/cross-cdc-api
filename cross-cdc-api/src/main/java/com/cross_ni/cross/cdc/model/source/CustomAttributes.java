package com.cross_ni.cross.cdc.model.source;

import java.util.HashSet;
import java.util.Set;

public class CustomAttributes {

	private final Set<CustomAttribute> customAttributes;

	public CustomAttributes() {
		customAttributes = new HashSet<>();
	}

	public CustomAttributes addCustomAttribute(CustomAttribute customAttribute) {
		customAttributes.add(customAttribute);
		return this;
	}

	public Set<CustomAttribute> getCustomAttributes() {
		return customAttributes;
	}

	@Override
	public String toString() {
		return "CustomAttributes{" +
				"customAttributes=" + customAttributes +
				'}';
	}
}

package com.sparkx120.jwake.graphics3d.objects;

/**
 * Generic Type Enumeration
 * @author James
 *
 */
public enum GenericObjectType {
	SPHERE, PLANE, OMNI_LIGHT;
	
	public static GenericObjectType getTypeFromString(String str){
		if(str.equals("sphere"))
			return SPHERE;
		if(str.equals("plane"))
			return PLANE;
		if(str.equals("olight"))
			return OMNI_LIGHT;
		return null;
	}
}

/*
 * package com.servosys.exception;
 * 
 * 
 * public class ResourceNotFoundException extends RuntimeException { public
 * ResourceNotFoundException(String message) { super(message); } }
 */

package com.servosys.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
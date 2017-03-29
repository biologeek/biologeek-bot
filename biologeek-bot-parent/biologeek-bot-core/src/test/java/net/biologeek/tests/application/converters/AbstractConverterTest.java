package net.biologeek.tests.application.converters;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AbstractConverterTest<T> {
	
	
	public void assertAllGetsAreNotNull(T object, List<String> excludedMethods) throws Exception{
		List<Method> methods = Arrays.asList(object.getClass().getMethods());
		// filter getters
		List<Method> getters = methods.stream().filter(new Predicate<Method>() {
			@Override
			public boolean test(Method t) {
				if (t.getName().startsWith("get") && !excludedMethods.contains(t.getName()))
					return true;
				return false;
			}
		}).collect(Collectors.toList());
		for (Method method : getters){
			
			Object result = method.invoke(object);
			if (object == null)
				throw new Exception("Getter "+method.getName()+" returns null");
		}
	}

}

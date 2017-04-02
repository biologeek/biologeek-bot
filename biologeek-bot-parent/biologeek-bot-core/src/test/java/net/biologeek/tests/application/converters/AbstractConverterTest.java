	package net.biologeek.tests.application.converters;
	
	import java.lang.reflect.Method;
	import java.util.Arrays;
	import java.util.List;
	import java.util.function.Predicate;
	import java.util.stream.Collectors;
	
	import org.junit.Assert;
	
	public class AbstractConverterTest<T> {
	
		/**
		 * Checks if all getters return data. Only checks if field returns a value
		 * or null and throws an Exception if field is not filled (uh !). <br>
		 * <br>
		 * Attributes of converted object can be excluded from check by adding
		 * method name to excludedMethods.
		 * 
		 * @param object the object to test
		 * @param excludedMethods a list of methods that are excluded from test
		 * @throws Exception in case method returns null and is not excluded
		 */
		public void assertAllGetsAreNotNull(T object, List<String> excludedMethods) throws Exception {
			Assert.assertNotNull(object);
			List<Method> methods = Arrays.asList(object.getClass().getMethods());
			// filter getters
			List<Method> getters = methods.stream().filter(new Predicate<Method>() {
				@Override
				public boolean test(Method t) {
					if (t.getName().startsWith("get") && (excludedMethods == null
							|| (excludedMethods != null && !excludedMethods.contains(t.getName()))))
						return true;
					return false;
				}
			}).collect(Collectors.toList());
			for (Method method : getters) {
	
				Object result = method.invoke(object);
				if (result == null)
					throw new Exception("Getter " + method.getName() + " returns null");
			}
		}
	
		public void assertAllGetsAreNotNull(T object, String[] excludedMethods) throws Exception {
			assertAllGetsAreNotNull(object, Arrays.asList(excludedMethods));
		}
	
	}

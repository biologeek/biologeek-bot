package net.biologeek.bot.api.plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

/**
 * A list of parameters grouped by category and eventually with type for
 * validation on client side
 *
 */
public class ParametersList implements Errorable {
	List<ParametersGroup> groups;

	public ParametersList() {
		groups = new ArrayList<>();
	}
	public List<ParametersGroup> getGroups() {
		return groups;
	}

	public ParametersGroup getGroup(ParameterGroup name) {
		return groups.stream().filter(new Predicate<ParametersGroup>() {
			@Override
			public boolean test(ParametersGroup t) {
				return t.getName() == name;
			}
		}).findFirst().get();
		
	}

	public void setGroups(List<ParametersGroup> groups) {
		this.groups = groups;
	}
	public ParametersList groups(List<ParametersGroup> groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * A group of coherent parameters belonging to the same functional domain
	 *
	 */
	public class ParametersGroup {

		ParameterGroup name;
		List<Parameter> parameters;
		
		public ParametersGroup() {
			parameters = new ArrayList<>();
		}
		public ParameterGroup getName() {
			return name;
		}
		public void setName(ParameterGroup name) {
			this.name = name;
		}
		public List<Parameter> getParameters() {
			return parameters;
		}
		public void setParameters(List<Parameter> parameters) {
			this.parameters = parameters;
		}
		public ParametersGroup name(ParameterGroup name) {
			this.name = name;
			return this;
		}
		public ParametersGroup parameters(List<Parameter> parameters) {
			this.parameters = parameters;
			return this;
		}
		public Parameter getParameter(String string) {
			return parameters.stream().filter(new Predicate<Parameter>() {
				@Override
				public boolean test(Parameter t) {
					return t.getName().equals(string);
				}
			}).findFirst().get();
		}
	}

	/**
	 * A single parameter
	 */
	public class Parameter {

		String name;
		String labelKey;
		String value;
		String type;

		public String getLabelKey() {
			return labelKey;
		}

		public void setLabelKey(String labelKey) {
			this.labelKey = labelKey;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Parameter value(String value) {
			this.value = value;
			return this;
		}
		public Parameter type(String type) {
			this.type = type;
			return this;
		}
		public Parameter name(String name) {
			this.name = name;
			return this;
		}
	}
}

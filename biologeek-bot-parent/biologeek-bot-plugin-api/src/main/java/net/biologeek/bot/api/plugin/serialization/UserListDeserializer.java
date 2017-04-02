package net.biologeek.bot.api.plugin.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.biologeek.bot.api.plugin.users.User;
import net.biologeek.bot.api.plugin.users.UsersList;

public class UserListDeserializer extends JsonDeserializer<UsersList> {

	@Override
	public UsersList deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		UsersList result = new UsersList();
		ObjectMapper map = (ObjectMapper) arg0.getCodec();
		ObjectNode root = map.readTree(arg0);
		if (root.get("continue") != null) {
			result.setCmContinue(root.get("continue").get("cmcontinue").toString());
		}
		JsonNode query = root.get("query");
		if (!query.isNull()) {
			result.setUsers(convertUsers(query.get("users")));
		}

		return result;
	}

	private List<User> convertUsers(JsonNode jsonNode) {
		List<User> users = new ArrayList<>();
		Iterator<JsonNode> elts = jsonNode.elements();
		while (elts.hasNext()) {
			JsonNode node = elts.next();
			users.add(convertUser(node));
		}
		return users;
	}

	private User convertUser(JsonNode node) {
		User user = new User();
		if (node.has("name"))
			user.setName(node.get("name").toString());
		if (node.has("userid"))
			user.setUserId(node.get("userid").toString());
		if (node.has("editcount"))
			user.setEmailable(node.get("emailable").toString());
		if (node.has("name"))
			user.setGender(node.get("gender").toString());
		if (node.has("registration"))
			user.setRegistration(node.get("registration").toString());
		if (node.has("blockinfo"))
			user.setBlockinfo(node.get("blockinfo").toString());
		if (node.has("implicitgroups"))
			user.setImplicitgroups(node.get("implicitgroups").toString());
		if (node.has("rights"))
			user.setRights(node.get("rights").toString());
		if (node.has("editcount"))
			user.setEditcount(node.get("editcount").toString());
		if (node.has("groups")){
			user.setGroups(convertGroups(node.get("groups")));
		}
		return user;
	}

	private List<String> convertGroups(JsonNode jsonNode) {
		Iterator<Entry<String, JsonNode>> it = jsonNode.fields();
		List<String> groups = new ArrayList<>();
		while (it.hasNext()) {
			groups.add(it.next().getKey());			
		}
		return groups;
	}

}

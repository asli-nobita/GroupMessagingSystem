import java.util.*;

public class GroupMessagingSystem {
	
	// Group names mapped to list of messages 
	private Map<String, List<String>> groupMessages = new HashMap<String, List<String>> ();
	// Group names mapped to set of users
	private Map<String, Set<User>> groupUsers = new HashMap<String, Set<User>> (); 
	 
	private Map<User, List<String>> userMessages = new HashMap<User, List<String>> (); 
	
	// Q3.
	public void sendMessage(String groupName, String message, User sender) {
		if(groupMessages.containsKey(groupName) && groupUsers.containsKey(groupName)) {
			groupMessages.get(groupName).add(message); 
			groupUsers.get(groupName).add(sender);
		}
		else {
			if(!groupMessages.containsKey(groupName)) {	
				groupMessages.put(groupName, new ArrayList<String> ()); 
				groupMessages.get(groupName).add(message);
			}
			if(!groupUsers.containsKey(groupName)) {
				groupUsers.put(groupName, new HashSet<User> ()); 
				groupUsers.get(groupName).add(sender);
			}
		}
		if(!userMessages.containsKey(sender)) {
			userMessages.put(sender, new ArrayList<String> ());
		}
		userMessages.get(sender).add(message);
	}
	
	// Q4.
	public User findMostActiveUser(String groupName) {
		if(!groupMessages.containsKey(groupName) || !groupUsers.containsKey(groupName)) return null;
		int maxCount = 0; 
		User mostActiveUser = null;
		for(User usr : groupUsers.get(groupName)) {
			int msgCount = userMessages.get(usr).size();
			if(msgCount > maxCount) {
				maxCount = msgCount; 
				mostActiveUser = usr;
			}
		}
		return mostActiveUser;
	}
	
	// Q5. 
	private void sortUsersByMessageCount(List<User> users, Map<User, Integer> criteriaMap) {
		int userCount = users.size(); 
		while(true) {
			boolean swapped = false; 
			Iterator<User> it = users.iterator(); 
			while(it.hasNext()) {
				User prevUser = it.next(); 
				User nextUser = it.next();
				if(criteriaMap.get(prevUser) < criteriaMap.get(nextUser)) {
					Collections.swap(users, users.indexOf(prevUser), users.indexOf(nextUser));
					swapped = true;
				}
			}
			if(!swapped) break;
		}
	}
	
	// Q6. 
	public UserStatistics getUserStatistics() {
		UserStatistics UserStatisticsImpl = (user, groupName) -> {
			return userMessages.get(user).size();
		};
		return UserStatisticsImpl;
	}
	
	// Q7. 
	public double getAverageMessagesPerUser(String groupName) {
		if(!groupMessages.containsKey(groupName) || !groupUsers.containsKey(groupName)) return 0.0; 
		double userCount = groupUsers.get(groupName).size(); 
		double messageCount = groupMessages.get(groupName).size();
		return messageCount / userCount;
	}
	
	// Q8. 
	public String getMostFrequentMessage(String groupName) {
		if(!groupMessages.containsKey(groupName) || !groupUsers.containsKey(groupName)) return null; 
		List<String> messages = groupMessages.get(groupName); 
		Map<String, Integer> msgFreqMap = new HashMap<String, Integer> ();
		int maxFreq = 0; String mostFreqMsg = null;
		for(String msg : messages) {
			if(msgFreqMap.containsKey(msg)) msgFreqMap.put(msg, msgFreqMap.get(msg)+1);
			else msgFreqMap.put(msg, 1);
			
			if(msgFreqMap.get(msg) > maxFreq) {
				maxFreq = msgFreqMap.get(msg); 
				mostFreqMsg = msg;
			}
		}
		return mostFreqMsg;
	}
	
	// Q9. 
	private void sortMessages(List<String> messages) {
		// ???
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GroupMessagingSystem system = new GroupMessagingSystem(); 
		
		system.sendMessage("GroupA", "Hello from Alpha", new User("U001", "Alpha"));
		system.sendMessage("GroupA", "Hi from Bravo", new User("U002", "Bravo"));
		system.sendMessage("GroupB", "Greetings from Charlie", new User("U003", "Charlie"));
		system.sendMessage("GroupB", "Good morning from Delta", new User("U001", "Delta"));
		system.sendMessage("GroupA", "Another message from Alpha", new User("U001", "Alpha"));
		system.sendMessage("GroupA", "How are you, Bravo?", new User("U002", "Bravo"));
		
		User mostActiveUser = system.findMostActiveUser("GroupA");
		System.out.println("Most active user in GroupA: " + mostActiveUser.getUserName()); 
		
		double avgMessages = system.getAverageMessagesPerUser("GroupA"); 
		System.out.println("Average messages per user in GroupA: " + avgMessages);
		
		String mostFrequentMessage = system.getMostFrequentMessage("GroupA"); 
		System.out.println("Most frequent message in GroupA: " + mostFrequentMessage);
	}
	
	static class User {
		private final String userID; private final String userName;
		
		public User(String userID, String userName) {
			this.userID = userID; 
			this.userName = userName; 
		}

		public String getUserID() { return userID; } 
		public String getUserName() { return userName; }
		
		// Q10. 
		public boolean equals(Object obj) {
//			if(this != obj) return false; 
			if(this == obj) return true;
			if(this.getUserID().equals(((User) obj).getUserID())) return true; 
			else return false;
	 	}
		
		public String toString() {
			return "User{" + "userID='" + userID + '\'' + ", userName='" + userName + '\'' + '}';
		}
		
		public int hashCode() {
			return this.getUserID().hashCode();
		}
	}
	
	interface UserStatistics {
		int getUserMessageCount(User user, String groupName);
	}
}


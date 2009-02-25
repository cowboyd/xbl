package xbox;

public class Main {
	public static void main(String[] args) {

		String username = args[0];
		String password = args[1];
		XBoxLive live = new XBoxLive();
		live.signIn(username, password);

		System.out.println("Your Friends");
		for (Friend friend : live.getFriends()) {
			System.out.println("    gt:         " + friend.getGamerTag());
			System.out.println("    status:     " + friend.getStatus());
			System.out.println("    score:      " + friend.getGamerScore());
			System.out.println("    last seen:  " + friend.getLastSeen() + (friend.hasLastActivity() ? "(" + friend.getLastActivity() + ")" : ""));

		}
	}
}

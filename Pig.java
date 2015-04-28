public class Pig {

	public static void main(String[] args) {
		int roll, accumulated, P0, P1, player;
		accumulated = 0;
		P0 = 0;
		P1 = 0;
		player = 1;
		while (P0 < 100 && P1 < 100) {
			StdOut.println("Score: " + P0 + "-" + P1);
			accumulated = 0;
			if (player == 1) {
				player = 0;
			} else {
				player = 1;
			}
			while (true) {
				roll = StdRandom.uniform(1, 7);
				if (roll == 1) {
					StdOut.println("Player " + player + ", you rolled a 1.");
					accumulated = 0;
					break;
				} else if (roll > 1) {
					accumulated = accumulated + roll;
					StdOut.println("Player " + player + ", you rolled a "
							+ roll + ".");
					StdOut.println("You have " + accumulated + " points.");
					StdOut.println("Keep going (y/n)?");
					if (StdIn.readLine().equals("n")) {
						if (player == 0) {
							P0 = P0 + accumulated;
						} else {
							P1 = P1 + accumulated;
						}
						break;
					}
				}
			}
		}
		if (P0 <= 100 || P1 <= 100) {
			StdOut.println("Game over. Final score: " + P0 + "-" + P1);
		}
	}

}

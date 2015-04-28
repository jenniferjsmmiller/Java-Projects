import java.util.ArrayList;

public class HexModel {

    public static final int VACANT = 2;
    public static final int WHITE = 1;
    public static final int BLACK = 0;
    public HexNode[][] nodes;

    private HexNode north;
    private HexNode south;
    private HexNode west;
    private HexNode east;

    private int width;
    private int currentPlayer;
    private ArrayList<HexNode> checked;

    public HexModel(int size) {
   	 this.width = size;
   	 currentPlayer = BLACK;
   	 nodes = new HexNode[size][size];
   	 for (int i = 0; i < nodes.length; i++) {
   		 for (int j = 0; j < nodes[i].length; j++) {
   			 nodes[i][j] = new HexNode();
   		 }
   	 }

   	 north = new HexNode();
   	 south = new HexNode();
   	 east = new HexNode();
   	 west = new HexNode();

   	 east.setColor(WHITE);
   	 west.setColor(WHITE);
   	 north.setColor(BLACK);
   	 south.setColor(BLACK);

   	 for (int i = 0; i < nodes.length; i++) {
   		 for (int j = 0; j < nodes.length; j++) {
   			 if (j - 1 >= 0) {
   				 nodes[i][j].addNeighbor(nodes[i][j - 1]);
   			 }
   			 if (j + 1 < size) {
   				 nodes[i][j].addNeighbor(nodes[i][j + 1]);
   			 }
   			 if (i - 1 >= 0) {
   				 nodes[i][j].addNeighbor(nodes[i - 1][j]);
   			 }
   			 if (i + 1 < size) {
   				 nodes[i][j].addNeighbor(nodes[i + 1][j]);
   			 }
   			 if (i - 1 >= 0 && j + 1 < size) {
   				 nodes[i][j].addNeighbor(nodes[i - 1][j + 1]);
   			 }
   			 if (i + 1 < size && j - 1 >= 0) {
   				 nodes[i][j].addNeighbor(nodes[i + 1][j - 1]);
   			 }
   		 }
   	 }

   	 for (int i = 0; i < width; i++) {
   		 east.addNeighbor(nodes[i][width - 1]);
   		 nodes[i][width - 1].addNeighbor(east);
   		 west.addNeighbor(nodes[i][0]);
   		 nodes[i][0].addNeighbor(west);
   		 north.addNeighbor(nodes[0][i]);
   		 nodes[0][i].addNeighbor(north);
   		 south.addNeighbor(nodes[width - 1][i]);
   		 nodes[width - 1][i].addNeighbor(south);
   	 }
    }

    public int getWidth() {
   	 return this.width;
    }

    public int getColor(int i, int j) {
   	 int color = nodes[i][j].getColor();
   	 return color;
    }

    public HexNode getNode(int i, int j) {
   	 return nodes[i][j];
    }

    public int getNeighborCount(HexNode node) {
   	 return node.getNeighbors().size();
    }

    public HexNode getSouth() {
   	 return south;
    }

    public HexNode getEast() {
   	 return east;
    }

    public HexNode getNorth() {
   	 return north;
    }

    public HexNode getWest() {
   	 return west;
    }

    public int findWinner() {
   	 int winner = VACANT;
   	 checked = new ArrayList<HexNode>();
   	 if (checkForPath(north, south)) {
   		 winner = BLACK;
   	 }
   	 checked = new ArrayList<HexNode>();
   	 if (checkForPath(west, east)) {
   		 winner = WHITE;
   	 }
   	 return winner;
    }

    public boolean checkForPath(HexNode start, HexNode end) {
   	 if (start.isNeighborOf(end)) {
   		 return true;
   	 } else {
   		 checked.add(start);
   		 for (HexNode each : start.getNeighbors()) {
   			 if ((!checked.contains(each))
   					 && (each.getColor() == start.getColor())) {
   				 if (checkForPath(each, end)) {
   					 return true;
   				 }
   			 }
   		 }
   		 return false;
   	 }
    }

    public int getCurrentPlayer() {
   	 return currentPlayer;
    }

    public void playAt(int i, int j) {
   	 nodes[i][j].setColor(currentPlayer);
   	 if (currentPlayer == BLACK) {
   		 currentPlayer = WHITE;
   	 } else if (currentPlayer == WHITE) {
   		 currentPlayer = BLACK;
   	 }
    }

    public void setColor(int i, int j, int color) {
   	 nodes[i][j].setColor(color);
    }

    public String toString() {
   	 String stringToReturn = " ";

   	 for (int i = 0; i < width; i++) {
   		 stringToReturn += i + " ";
   	 }

   	 stringToReturn += "\n";
   	 for (int j = 0; j < width; j++) {
   		 for (int k = 0; k < j; k++) {
   			 stringToReturn += " ";
   		 }
   		 stringToReturn += (j + " ");
   		 for (int c = 0; c < width; c++) {
   			 if (nodes[j][c].getColor() == BLACK) {
   				 stringToReturn += "# ";
   			 } else if (nodes[j][c].getColor() == WHITE) {
   				 stringToReturn += "0 ";
   			 } else if (nodes[j][c].getColor() == VACANT) {
   				 stringToReturn += ". ";
   			 }
   		 }
   		 stringToReturn += (j + "\n");
   	 }
   	 for (int i = 0; i < width + 2; i++) {
   		 stringToReturn += " ";
   	 }
   	 for (int i = 0; i < width; i++) {
   		 stringToReturn += (i + " ");
   	 }
   	 stringToReturn += "\n";
   	 return stringToReturn;
    }

}
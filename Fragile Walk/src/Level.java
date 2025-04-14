import java.util.HashMap;

public class Level {
	public static HashMap<Integer, String> dictionary;
	static {
		dictionary = new HashMap<>();
		dictionary.put(-1, "emptyTile");
		dictionary.put(1, "crackedTile");
		dictionary.put(2, "crackedTileBordered");
		dictionary.put(3, "basicTile");
		dictionary.put(4, "basicTileBordered");
		dictionary.put(5, "reinforcedTile");
		dictionary.put(6, "reinforcedTileBordered");
		dictionary.put(7, "doubleReinforcedTile");
		dictionary.put(8, "doubleReinforcedTileBordered");
		dictionary.put(9, "flagTile");
		dictionary.put(10, "flagTileBordered");
		
	}
	
	
	public int[][] map;
	private int rows;
	private int columns;
	
	public Level(int[][] map) {
		this.map = map;
		this.rows = map.length;
		this.columns = map[0].length;
	}
	
	public Level copy() {
        int[][] newMap = new int[map.length][];
        for (int i = 0; i < map.length; i++) {
            newMap[i] = map[i].clone();
        }
        return new Level(newMap);
    }
	
	public int[][] getMap() {
		return map;
	}
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	
}
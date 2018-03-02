import java.util.Arrays;

public class EightQueen {
    public static void main(String[] args) {
        eightQueen(0, new int[8]);
    }

    private static void eightQueen(int n, int[] map) {
        if (n == map.length) {
            System.out.println(Arrays.toString(map));
            return;
        }

        main: for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < n; j++) {
                if (map[j] == i || n - i == j - map[j] || n + i == j + map[j])
                    continue main;
            }

            map[n] = i;
            eightQueen(n + 1, map);
        }
    }
}

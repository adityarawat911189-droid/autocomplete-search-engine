public class LevenshteinTest {

    // Do strings ke beech minimum edit distance nikalta hai
    public static int calculateDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // dp[i][j] = word1 ke pehle 'i' characters aur word2 ke pehle 'j' characters ke beech distance
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: agar ek string khaali hai
        for (int i = 0; i <= m; i++) dp[i][0] = i; // sab delete karne padenge
        for (int j = 0; j <= n; j++) dp[0][j] = j; // sab insert karne padenge

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match kar gaye, koi operation nahi chahiye
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Teeno operations mein se minimum le lo: insert, delete, replace
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],      // replace
                            Math.min(dp[i - 1][j],          // delete
                                    dp[i][j - 1]));        // insert
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("Distance between 'car' and 'acr': " + calculateDistance("car", "acr"));
        System.out.println("Distance between 'cat' and 'cats': " + calculateDistance("cat", "cats"));
        System.out.println("Distance between 'kitten' and 'sitting': " + calculateDistance("kitten", "sitting"));
        System.out.println("Distance between 'car' and 'car': " + calculateDistance("car", "car"));
    }
}
package me.rrs.headdrop;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import java.util.Map.Entry;
import java.util.stream.Collectors;


public class WebsiteController {

    private HttpServer server;

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        // Create a context for the leaderboard endpoint
        server.createContext("/" + HeadDrop.getInstance().getConfiguration().getString("Web.Endpoint"), new LeaderboardHandler());
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    static class LeaderboardHandler implements HttpHandler {
        private static final int ENTRIES_PER_PAGE = 10;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<Entry<String, Integer>> sortedPlayerData = getSortedPlayerData();

            int page = getRequestedPage(exchange.getRequestURI().getQuery());
            int startIndex = (page - 1) * ENTRIES_PER_PAGE;
            int endIndex = Math.min(startIndex + ENTRIES_PER_PAGE, sortedPlayerData.size());

            String response = generateHtmlResponse(sortedPlayerData, startIndex, endIndex, page);
            sendResponse(exchange, response);
        }

        private List<Entry<String, Integer>> getSortedPlayerData() {
            Map<String, Integer> playerData = HeadDrop.getInstance().getDatabase().getPlayerData();
            return playerData.entrySet()
                    .stream()
                    .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        private int getRequestedPage(String query) {
            if (query != null) {
                return Arrays.stream(query.split("&"))
                        .filter(param -> param.startsWith("page="))
                        .map(param -> param.split("=")[1])
                        .filter(page -> page.matches("\\d+")) // Validate numeric
                        .mapToInt(Integer::parseInt)
                        .findFirst()
                        .orElse(1); // Default to page 1
            }
            return 1; // Default to page 1
        }

        private String generateHtmlResponse(List<Entry<String, Integer>> sortedPlayerData, int startIndex, int endIndex, int page) {
            return "<!DOCTYPE html>" +
                    "<html lang=\"en\">\n<head>\n<title>Leaderboard</title>\n" +
                    "<meta charset=\"UTF-8\">\n" +  // Add this line for UTF-8 encoding
                    "<link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap\" rel=\"stylesheet\">\n<style>\n" +
                    generateCssStyles() +
                    "</style>\n</head>\n<body>\n" +
                    "<div class=\"container\">\n<h1>Leaderboard</h1>\n" +
                    "<button id=\"theme-toggle\" onclick=\"toggleTheme()\">🌙</button>\n" +
                    generateTable(sortedPlayerData, startIndex, endIndex) +
                    generatePaginationLinks(sortedPlayerData.size(), page) +
                    "</div>\n" +
                    "<script>\n" +
                    "function toggleTheme() {\n" +
                    "    const body = document.body;\n" +
                    "    const isDarkMode = body.classList.toggle('dark-mode');\n" +
                    "    localStorage.setItem('theme', isDarkMode ? 'dark' : 'light');\n" +
                    "    const themeToggle = document.getElementById('theme-toggle');\n" +
                    "    themeToggle.innerHTML = isDarkMode ? '🌞' : '🌙';\n" +
                    "}\n" +
                    "function loadTheme() {\n" +
                    "    const savedTheme = localStorage.getItem('theme');\n" +
                    "    if (savedTheme === 'dark') {\n" +
                    "        document.body.classList.add('dark-mode');\n" +
                    "        document.getElementById('theme-toggle').innerHTML = '🌞';\n" +
                    "    }\n" +
                    "}\n" +
                    "loadTheme();\n" +
                    "</script>\n" +
                    "</body>\n</html>";
        }



        private String generateCssStyles() {
            return """
                    body { font-family: 'Roboto', sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; transition: background-color 0.3s, color 0.3s; }
                    .dark-mode { background-color: #121212; color: #e0e0e0; }
                    .container { max-width: 800px; margin: 2rem auto; padding: 2rem; background: white; border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); transition: background 0.3s; }
                    .dark-mode .container { background-color: #1e1e1e; }
                    h1 { text-align: center; color: #333; margin-bottom: 1rem; }
                    .dark-mode h1 { color: #e0e0e0; }
                    table { width: 100%; border-collapse: collapse; margin-bottom: 1rem; }
                    th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
                    th { background-color: #6200ea; color: white; }
                    .dark-mode th { background-color: #3700b3; }
                    tr:hover { background-color: #f1f1f1; }
                    .dark-mode tr:hover { background-color: #333; }
                    .pagination { display: flex; justify-content: center; margin-top: 1rem; }
                    .pagination a { display: inline-block; margin: 0 5px; padding: 8px 16px; border-radius: 4px; background-color: #6200ea; color: white; text-decoration: none; transition: background-color 0.3s; }
                    .dark-mode .pagination a { background-color: #3700b3; }
                    .pagination a:hover { background-color: #3700b3; }
                    .dark-mode .pagination a:hover { background-color: #6200ea; }
                    #theme-toggle { position: absolute; top: 20px; right: 20px; padding: 8px 12px; background-color: #6200ea; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s; }
                    .dark-mode #theme-toggle { background-color: #3700b3; }
                    @media (max-width: 600px) {
                    .container { padding: 1rem; }
                    h1 { font-size: 1.5rem; }
                    th, td { font-size: 14px; padding: 10px; }
                    }
                    """;
        }



        private String generateTable(List<Entry<String, Integer>> sortedPlayerData, int startIndex, int endIndex) {
            StringBuilder tableHtml = new StringBuilder();
            tableHtml.append("<table>\n<tr>\n<th class=\"th-header\">Rank</th>\n<th class=\"th-header\">Player</th>\n<th class=\"th-header\">Score</th>\n</tr>\n");

            for (int i = startIndex; i < endIndex; i++) {
                Entry<String, Integer> entry = sortedPlayerData.get(i);
                tableHtml.append("<tr>\n<td>").append(i + 1).append("</td>\n<td>").append(entry.getKey()).append("</td>\n<td>").append(entry.getValue()).append("</td>\n</tr>\n");
            }
            tableHtml.append("</table>\n");
            return tableHtml.toString();
        }

        private String generatePaginationLinks(int totalEntries, int currentPage) {
            int totalPages = (int) Math.ceil((double) totalEntries / ENTRIES_PER_PAGE);
            StringBuilder paginationHtml = new StringBuilder();

            if (totalPages > 1) {
                paginationHtml.append("<div class=\"pagination\">\n");
                if (currentPage > 1) {
                    paginationHtml.append("<a href=\"?page=").append(currentPage - 1).append("\" class=\"pagination-link\">Previous</a>\n");
                }
                if (currentPage < totalPages) {
                    paginationHtml.append("<a href=\"?page=").append(currentPage + 1).append("\" class=\"pagination-link\">Next</a>\n");
                }
                paginationHtml.append("</div>\n");
            }
            return paginationHtml.toString();
        }

        private void sendResponse(HttpExchange exchange, String response) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
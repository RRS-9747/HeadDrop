package me.rrs.headdrop;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Comparator;
import java.util.Map.Entry;


public class WebsiteController {

    private HttpServer server;

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Create a context for the leaderboard endpoint
        server.createContext("/" + HeadDrop.getConfiguration().getString("Web.Endpoint"), new LeaderboardHandler());
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
            // Get player data from the database
            Map<String, Integer> playerData = HeadDrop.getDatabase().getPlayerData();

            // Sort the player data by highest score first
            List<Entry<String, Integer>> sortedPlayerData = new ArrayList<>(playerData.entrySet());
            sortedPlayerData.sort(Entry.comparingByValue(Comparator.reverseOrder()));

            // Get the requested page from the query parameters
            String query = exchange.getRequestURI().getQuery();
            int page = 1; // Default to the first page
            if (query != null) {
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2 && keyValue[0].equals("page")) {
                        try {
                            page = Integer.parseInt(keyValue[1]);
                        } catch (NumberFormatException e) {
                            // Invalid page number, use the default
                        }
                        break;
                    }
                }
            }

            // Calculate the start and end indices for the entries on the requested page
            int startIndex = (page - 1) * ENTRIES_PER_PAGE;
            int endIndex = Math.min(startIndex + ENTRIES_PER_PAGE, sortedPlayerData.size());

            // Generate the HTML for the leaderboard with responsive design
            StringBuilder html = new StringBuilder("<html>\n<head>\n<title>Leaderboard</title>\n<style>\n");
            html.append("body {\nbackground-color: #1e1e1e;\ncolor: #ffffff;\n}\n");
            html.append(".container {\nmax-width: 800px;\nmargin: 0 auto;\npadding: 16px;\n}\n");
            html.append("table {\nborder-collapse: collapse;\nwidth: 100%;\n}\n");
            html.append("th, td {\nborder: 1px solid #ffffff;\ntext-align: left;\npadding: 8px;\n}\n");
            html.append("th {\nbackground-color: #3c3c3c;\ncolor: #ffffff;\n}\n");
            html.append("@media only screen and (max-width: 600px) {\n");
            html.append(".container {\nmax-width: 100%;\n}\n");
            html.append("table {\nfont-size: 14px;\n}\n");
            html.append("th, td {\npadding: 4px;\n}\n");
            html.append("}\n");
            html.append(".pagination {\n");
            html.append("margin-top: 16px;\n");
            html.append("}\n");
            html.append(".pagination a {\n");
            html.append("display: inline-block;\n");
            html.append("margin-right: 8px;\n");
            html.append("padding: 4px 8px;\n");
            html.append("background-color: #3c3c3c;\n");
            html.append("color: #ffffff;\n");
            html.append("text-decoration: none;\n");
            html.append("}\n");
            html.append(".pagination a:hover {\n");
            html.append("background-color: #1e1e1e;\n");
            html.append("}\n");

            html.append("</style>\n</head>\n<body>\n<div class=\"container\">\n<h1 style=\"text-align: center;\">Leaderboard</h1>\n<table>\n<tr>\n<th>Rank</th>\n<th>Player</th>\n<th>Score</th>\n</tr>\n");

            int rank = startIndex + 1;
            for (int i = startIndex; i < endIndex; i++) {
                Entry<String, Integer> entry = sortedPlayerData.get(i);
                html.append("<tr>\n<td>").append(rank).append("</td>\n<td>").append(entry.getKey()).append("</td>\n<td>").append(entry.getValue()).append("</td>\n</tr>\n");
                rank++;
            }

            html.append("</table>\n");

            // Add pagination links
            int totalPages = (int) Math.ceil((double) sortedPlayerData.size() / ENTRIES_PER_PAGE);
            if (totalPages > 1) {
                html.append("<div class=\"pagination\">\n");
                if (page > 1) {
                    html.append("<a href=\"?page=").append(page - 1).append("\">Previous</a>\n");
                }
                if (page < totalPages) {
                    html.append("<a href=\"?page=").append(page + 1).append("\">Next</a>\n");
                }
                html.append("</div>\n");
            }

            html.append("</div>\n</body>\n</html>");

            // Send the HTML response
            String response = html.toString();
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

}

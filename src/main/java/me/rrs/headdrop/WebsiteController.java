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
        public void handle(HttpExchange exchange) throws IOException {
            // Get player data from the database
            Map<String, Integer> playerData = HeadDrop.getDatabase().getPlayerData();

            // Sort the player data by highest score first
            List<Map.Entry<String, Integer>> sortedPlayerData = new ArrayList<>(playerData.entrySet());
            sortedPlayerData.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

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
            html.append("</style>\n</head>\n<body>\n<div class=\"container\">\n<h1 style=\"text-align: center;\">Leaderboard</h1>\n<table>\n<tr>\n<th>Rank</th>\n<th>Player</th>\n<th>Score</th>\n</tr>\n");

            int rank = 1;
            for (Map.Entry<String, Integer> entry : sortedPlayerData) {
                html.append("<tr>\n<td>").append(rank).append("</td>\n<td>").append(entry.getKey()).append("</td>\n<td>").append(entry.getValue()).append("</td>\n</tr>\n");
                rank++;
            }

            html.append("</table>\n</div>\n</body>\n</html>");

            // Send the HTML response
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, html.length());
            OutputStream os = exchange.getResponseBody();
            os.write(html.toString().getBytes());
            os.close();
        }


    }
}
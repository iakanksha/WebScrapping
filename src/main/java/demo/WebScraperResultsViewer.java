package demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebScraperResultsViewer {


    public static void main(String[] args) {
        // Set port for the web server
        Spark.port(8443);

        // Read and parse JSON files
        String json1 = readJsonFile("src/test/resources/data1.json");
        String json2 = readJsonFile("src/test/resources/data2.json");

        // Generate HTML pages
        String html1 = generateHtmlPage(json1, "Data 1");
        String html2 = generateHtmlPage(json2, "Data 2");

        // Set up routes
        Spark.get("/data1", (req, res) -> html1);
        Spark.get("/data2", (req, res) -> html2);

        // Start the server
        Spark.init();

        // Print URLs in the terminal
        System.out.println("View Data 1 at: http://localhost:8443/data1");
        System.out.println("View Data 2 at: http://localhost:8443/data2");
    }

    private static String readJsonFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private static String generateHtmlPage(String jsonString, String title) {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"en\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "    <title>" + title + "</title>\n" +
               "    <link href=\"https://cdnjs.cloudflare.com/ajax/libs/jsoneditor/9.9.2/jsoneditor.min.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
               "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jsoneditor/9.9.2/jsoneditor.min.js\"></script>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <h1>" + title + "</h1>\n" +
               "    <div id=\"jsoneditor\" style=\"width: 100%; height: 600px;\"></div>\n" +
               "    <script>\n" +
               "        const container = document.getElementById('jsoneditor');\n" +
               "        const options = {};\n" +
               "        const editor = new JSONEditor(container, options);\n" +
               "        const json = " + jsonString + ";\n" +
               "        editor.set(json);\n" +
               "    </script>\n" +
               "</body>\n" +
               "</html>";
    }

    // private static final int PORT = 4567;
    // private static final String JSON_FILE_PATH = "path/to/your/json/file.json";

    // public static void main(String[] args) {
    //     JsonObject scrapedData = readJsonFile(JSON_FILE_PATH);
    //     startServer(scrapedData);
    //     openBrowser();
    // }

    // private static JsonObject readJsonFile(String filePath) {
    //     try {
    //         String content = new String(Files.readAllBytes(Paths.get(filePath)));
    //         return new Gson().fromJson(content, JsonObject.class);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new JsonObject();
    //     }
    // }

    // private static void startServer(JsonObject data) {
    //     Spark.port(PORT);
    //     Spark.get("/", (req, res) -> {
    //         res.type("text/html");
    //         return generateHtml(data);
    //     });
    // }

    // private static String generateHtml(JsonObject data) {
    //     StringBuilder html = new StringBuilder();
    //     html.append("<html><head><title>Web Scraper Results</title></head><body>");
    //     html.append("<h1>Web Scraper Results</h1>");
    //     html.append("<pre>").append(new Gson().toJson(data)).append("</pre>");
    //     html.append("</body></html>");
    //     return html.toString();
    // }

    // private static void openBrowser() {
    //     String url = "http://localhost:" + PORT;
    //     try {
    //         if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
    //             Desktop.getDesktop().browse(new URI(url));
    //         } else {
    //             System.out.println("Open this link in your browser: " + url);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         System.out.println("Unable to open browser. Please visit: " + url);
    //     }
    // }
    
}














package web;

import javax.net.ssl.HttpsURLConnection;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WebUtil {
    private WebUtil() {
    }

    public static void closeAll(Closeable... able) {
        Closeable[] var1 = able;
        int var2 = able.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Closeable c = var1[var3];
            if (c != null) {
                try {
                    c.close();
                } catch (IOException var6) {
                    var6.printStackTrace();
                }
            }
        }

    }

    public static void downloadMusic(String url, String fileName) throws IOException {
        URL url1 = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) url1.openConnection();
        InputStream inputStream = connection.getInputStream();

        FileOutputStream outputFile = new FileOutputStream(fileName);
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = inputStream.read(buffer)) != 1){
            outputFile.write(buffer,0,len);
        }

        closeAll(inputStream, outputFile);
        connection.disconnect();
    }
}
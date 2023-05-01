package com.scaler.assignments;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Assignment2 {
    public static void main(String[] args) throws Exception {
        String json = getUrl("https://jsonplaceholder.typicode.com/photos");
        Type listType = new TypeToken<ArrayList<Album>>(){}.getType();
        List<Album> albums = new Gson().fromJson(json, listType);
        File dir = new File("src/main/resources/albums");
        if (dir.exists()) {
            dir.delete();
        }
        dir.mkdirs();
        int count =0;
        for (Album album: albums) {
            File file = new File(dir, String.valueOf(album.albumId));
            if (!file.exists()){
                file.mkdir();
                count++;
            }
            if (file.listFiles().length < 2){
                file = new File(file, String.valueOf(album.id + ".jpg"));
                file.createNewFile();
                saveImage(album.url, file);
            }else{
                if (count>=10){
                    break;
                }
            }
        }
    }
    public static String getUrl(String urlStr) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    private static void saveImage(String imageUrl, File destinationFile) throws Exception
    {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinationFile));

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1)
        {
            bos.write(b, 0, length);
        }

        is.close();
        bos.close();
    }
}
class Album{
    Integer albumId;
    Integer id;
    String url;
    String thumbnailUrl;

}
class Albums{
    List<Album> albumList;
}

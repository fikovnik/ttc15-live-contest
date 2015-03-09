package ttc15.tranj.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDownload {

  private final URL url;

  public URLDownload(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  public byte[] get() throws IOException {
    try (InputStream input = url.openStream()) {

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] chunk = new byte[4 * 1024];
      int n;

      while ((n = input.read(chunk)) > 0) {
        buffer.write(chunk, 0, n);
      }

      return buffer.toByteArray();
    }
  }
}

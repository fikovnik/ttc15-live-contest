import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ttc15.tranj.examples.ManualURLDownload;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ManualURLDownload.class)
public class ManualURLDownloadTest {

  String url = "http://example.com";

  @Test
  public void retry() throws Exception {
    URL mockURL = PowerMockito.mock(URL.class);

    PowerMockito.whenNew(URL.class).withArguments(url).thenReturn(mockURL);

    byte[] response = "OK".getBytes();
    
    PowerMockito.when(mockURL.openStream())
        .thenThrow(new SocketTimeoutException())
        .thenThrow(new SocketTimeoutException())
        .thenReturn(new ByteArrayInputStream(response));

    ManualURLDownload download = new ManualURLDownload(url);
    assertArrayEquals(response, download.get());
  }

  @Test
  public void cache() throws Exception {
    URL mockURL = PowerMockito.mock(URL.class);

    PowerMockito.whenNew(URL.class).withArguments(url).thenReturn(mockURL);

    byte[] response1 = "OK".getBytes();
    byte[] response2 = "FAIL".getBytes();
    
    PowerMockito.when(mockURL.openStream())
        .thenReturn(new ByteArrayInputStream(response1))
        .thenReturn(new ByteArrayInputStream(response2));

    ManualURLDownload download = new ManualURLDownload(url);
    assertArrayEquals(response1, download.get());
    assertArrayEquals(response1, download.get());
  }
  
  
}

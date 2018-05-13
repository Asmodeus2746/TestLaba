import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.*;

import org.powermock.modules.junit4.PowerMockRunner;
import ru.asmi.GetOccurencies;
import ru.asmi.OccurenciesHandler;
import ru.asmi.SentenceHandler;
import sun.net.www.protocol.ftp.FtpURLConnection;
import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class, URLConnection.class,HttpURLConnection.class,GetOccurencies.class, OccurenciesHandler.class, SentenceHandler.class, FileWriter.class, FileReader.class, FileInputStream.class} )
public class GetOccurenciesTest {

    @Test
    public void testHttp() throws Exception {

        String test_words[] = {"sentence", "words", "letter"};
        String test_source[] = {"http://www.http_url.com"};
        String test_ret = "/mock_directory/mock_path.txt";

        String rec_content = "I am first sentence. I am second and I have letter! I am sentAnce with error. I am third with words.";
        InputStream inputStream = new ByteArrayInputStream(rec_content.getBytes());

        String http_url = test_source[0];
        URL mock_http_url = PowerMockito.mock(URL.class);
        HttpURLConnection http_urlConnection = PowerMockito.mock(HttpURLConnection.class);

        PowerMockito.whenNew(URL.class).withArguments(http_url).thenReturn(mock_http_url);
        PowerMockito.when(mock_http_url.openConnection()).thenReturn(http_urlConnection);
        PowerMockito.when(http_urlConnection.getInputStream()).thenReturn(inputStream);

        FileWriter mock_resFile = Mockito.mock(FileWriter.class);
        PowerMockito.whenNew(FileWriter.class).withArguments(test_ret).thenReturn(mock_resFile);

        GetOccurencies occurenciesHandler = new OccurenciesHandler();
        occurenciesHandler.getOccurencies(test_source, test_words, test_ret);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mock_resFile, Mockito.times(3)).append(captor.capture());

        List<String> list = captor.getAllValues();
        assert list.get(0).trim().equals("I am first sentence.");
        assert list.get(1).trim().equals("I am second and I have letter!");
        assert list.get(2).trim().equals("I am third with words.");
    }

    @Test
    public void testFtp() throws Exception {

        String test_words[] = {"sentence", "words", "letter"};
        String test_source[] = {"ftp://www.ftp_url.com"};
        String test_ret = "/mock_directory/mock_path.txt";

        String rec_content = "I am first sentence. I am second and I have letter! I am sentAnce with error. I am third with words.";
        InputStream inputStream = new ByteArrayInputStream(rec_content.getBytes());

        String ftp_url = test_source[0];
        URL mock_ftp_url = PowerMockito.mock(URL.class);
        FtpURLConnection ftp_urlConnection = PowerMockito.mock(FtpURLConnection.class);

        PowerMockito.whenNew(URL.class).withArguments(ftp_url).thenReturn(mock_ftp_url);
        PowerMockito.when(mock_ftp_url.openConnection()).thenReturn(ftp_urlConnection);
        PowerMockito.when(ftp_urlConnection.getInputStream()).thenReturn(inputStream);

        FileWriter mock_resFile = Mockito.mock(FileWriter.class);
        PowerMockito.whenNew(FileWriter.class).withArguments(test_ret).thenReturn(mock_resFile);

        GetOccurencies occurenciesHandler = new OccurenciesHandler();
        occurenciesHandler.getOccurencies(test_source, test_words, test_ret);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mock_resFile, Mockito.times(3)).append(captor.capture());

        List<String> list = captor.getAllValues();
        assert list.get(0).trim().equals("I am first sentence.");
        assert list.get(1).trim().equals("I am second and I have letter!");
        assert list.get(2).trim().equals("I am third with words.");
    }

    @Test
    public void testFile() throws Exception {

        String test_words[] = {"sentence", "words", "letter"};
        String test_source[] = {"/mock_directory/mock_source.txt"};
        String test_ret = "/mock_directory/mock_path.txt";

        String rec_content = "I am first sentence. I am second and I have letter! I am sentAnce with error. I am third with words.";
        InputStream inputStream = new ByteArrayInputStream(rec_content.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        String file_path = test_source[0];
        FileInputStream mock_file = PowerMockito.mock(FileInputStream.class);

        PowerMockito.whenNew(FileInputStream.class).withArguments(file_path).thenReturn(mock_file);
        PowerMockito.whenNew(InputStreamReader.class).withArguments(mock_file).thenReturn(inputStreamReader);

        FileWriter mock_resFile = Mockito.mock(FileWriter.class);
        PowerMockito.whenNew(FileWriter.class).withArguments(test_ret).thenReturn(mock_resFile);

        GetOccurencies occurenciesHandler = new OccurenciesHandler();
        occurenciesHandler.getOccurencies(test_source, test_words, test_ret);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mock_resFile, Mockito.times(3)).append(captor.capture());

        List<String> list = captor.getAllValues();
        assert list.get(0).trim().equals("I am first sentence.");
        assert list.get(1).trim().equals("I am second and I have letter!");
        assert list.get(2).trim().equals("I am third with words.");
    }
}
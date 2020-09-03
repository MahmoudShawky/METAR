package eg.mahmoudShawky.metar.data.remote;

import android.util.Pair;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eg.mahmoudShawky.metar.utils.AppLogger;
import eg.mahmoudShawky.metar.utils.ContextUtils;
import eg.mahmoudShawky.metar.utils.Utils;
import eg.mahmoudShawky.metar.utils.exceptions.NetworkException;

@Singleton
public class RemoteRepoImp implements RemoteRepo {
    private static final String TAG = "RemoteRepoImp";

    private ContextUtils contextUtils;

    @Inject
    public RemoteRepoImp(ContextUtils contextUtils) {
        this.contextUtils = contextUtils;
    }

    @Override
    public List<Pair<String, String>> getStationsCodes(String nameFilter) throws IOException {
        return getDirectoryFiles(nameFilter, ServicesUrls.getDecodedDirectoryUrl());
    }

    @NotNull
    private List<Pair<String, String>> getDirectoryFiles(String nameFilter, String directoryUrl) throws IOException {
        if (!contextUtils.isOnline()) throw new NetworkException();
        List<Pair<String, String>> locationNamePairsList = new ArrayList<>();
        URL url = new URL(directoryUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            AppLogger.getInstance().d(TAG, line);
            Pair<String, String> locationNamePair = Utils.getLinkLocationAndName(line);
            if (locationNamePair != null
                    && locationNamePair.second.toLowerCase().startsWith(nameFilter.toLowerCase())) {
                locationNamePairsList.add(locationNamePair);

                //where is the stations code are sorted so, we can break in case of any line didn't match
            } else if (!locationNamePairsList.isEmpty()) {
                break;
            }
        }

        inputStream.close();
        conn.disconnect();
        return locationNamePairsList;
    }


    @Override
    public String getStationsDecodedFile(String stationCode) throws IOException {
        return getFileContent(ServicesUrls.getDecodedFileUrl(stationCode));
    }

    @NotNull
    private String getFileContent(String directoryUrl) throws IOException {
        if (!contextUtils.isOnline()) throw new NetworkException();
        URL url = new URL(directoryUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder fileContent = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            fileContent.append(line)
                    .append("\n");
        }

        inputStream.close();
        conn.disconnect();

        return fileContent.toString();
    }
}

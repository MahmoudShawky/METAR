package eg.mahmoudShawky.metar.data.remote;

import eg.mahmoudShawky.metar.BuildConfig;

public class ServicesUrls {

    public static String getRawReportDirectoryUrl() {
        return BuildConfig.BASE_URL + "stations/";
    }

    public static String getRawReportFileUrl(String stationCode) {
        return getRawReportDirectoryUrl() + stationCode+ ".TXT";
    }

    public static String getDecodedDirectoryUrl() {
        return BuildConfig.BASE_URL + "decoded/";
    }

    public static String getDecodedFileUrl(String stationCode) {
        return getDecodedDirectoryUrl() + stationCode + ".TXT";
    }
}

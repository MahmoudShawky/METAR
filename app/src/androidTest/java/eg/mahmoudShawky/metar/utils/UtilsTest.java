package eg.mahmoudShawky.metar.utils;

import android.util.Pair;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    String line1 = "<tr><td><a href=\"EDAC.TXT\">EDAC.TXT</a></td><td align=\"right\">28-Jun-2020 11:25  </td><td align=\"right\">426 </td></tr>\n";
    String line2 = "<tr><td><a href=\"EDAH.TXT\">EDAH.TXT</a></td><td align=\"right\">06-Sep-2020 14:25  </td><td align=\"right\">414 </td></tr>";

    String line3 = "<tr><td><a href=\"EDACB.TXT\">EDACB.TXT</a></td><td align=\"right\">28-Jun-2020 11:25  </td><td align=\"right\">426 </td></tr>\n";
    String line4 = "<tr><td><a href=\"EDAC.TXT\">EDAC.Text</a></td><td align=\"right\">28-Jun-2020 11:25  </td><td align=\"right\">426 </td></tr>\n";

    @Test
    public void getLinkLocationAndName() {
        Pair<String, String> pair = Utils.getLinkLocationAndName(line1);
        Assert.assertNotNull(pair);
        Assert.assertEquals(pair.first, "EDAC.TXT");
        Assert.assertEquals(pair.second, "EDAC");
    }

    @Test
    public void getLinkLocationAndNameNotEqual4Char() {
        Pair<String, String> pair = Utils.getLinkLocationAndName(line3);
        Assert.assertNull(pair);
    }

    @Test
    public void getLinkLocationAndNameNotTXTFile() {
        Pair<String, String> pair = Utils.getLinkLocationAndName(line3);
        Assert.assertNull(pair);
    }
}
package util;

import io.CSVReader;
import elements.DataSheet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringConversionTest
{
    private DataSheet ds;

    @Before
    public void setUp()
    {
        CSVReader reader = new CSVReader( "src/test/resources/data.csv" );
        ds = new DataSheet( reader );
    }

    @Test
    public void testFrame()
    {
        //@formatter:off
        String expected =
                "      Name |    Capital | Population | Forest ... |        GDP |  Seashore?\n" +
                "   Germany |     Berlin |   83149300 |       0.32 |      52556 |       true\n" +
                "   Austria |     Vienna |    8858775 |      0.462 |      53879 |      false\n" +
                "    Sweden |  Stockholm |   10327589 |       0.57 |      51405 |       true\n" +
                "    Russia |     Moscow |  144526636 |     0.4976 |      25763 |       true\n" +
                "  Paraguay |   Asunción |    6943739 |      0.443 |      13109 |      false\n" +
                "     Japan |      Tokyo |  126860000 |       0.67 |      42067 |       true\n" +
                " Australia |   Canberra |   25324713 |       0.17 |      49378 |       true\n" +
                "    Brazil |   Brasília |  208360000 |      0.624 |      15553 |       true\n" +
                "South K... |      Seoul |   51629512 |     0.6335 |      38824 |       true\n" +
                "     Qatar |       Doha |    2700000 |          0 |     128647 |       true\n";
        //@formatter:on
        String actual = StringConversion.from( ds, false );
        Assert.assertEquals( expected, actual );
    }

    @Test
    public void testRecord()
    {
        String expected = "Germany,Berlin,83149300,0.32,52556,true";
        String actual = StringConversion.from( ds.getRow( 1 ) );
        Assert.assertEquals( expected, actual );
    }
}

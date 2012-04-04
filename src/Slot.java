import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

/**
 * Created with IntelliJ IDEA.
 * User: farmisen
 * Date: 4/1/12
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Slot {
    Region region;
    private Pattern emptyPattern;
    public String name;

    public Slot(Region region, String emptyImageFileName, String name ) {
        this.name = name;
        this.region = region;
        this.emptyPattern = new Pattern(emptyImageFileName);
    }

    public boolean empty() {
        try {
            Match res = region.findNow(emptyPattern);

            if ( null != res ) {
                System.out.println("is " + name + " empty: "  + res.getScore());
            }
            return res != null && res.getScore() > .9f;
        } catch (FindFailed findFailed) {
            return false;
        }
    }
}

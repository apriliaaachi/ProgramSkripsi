package analisisentimen.control;
import java.util.Comparator;
import java.util.Map;
import analisisentimen.entity.Tag;

/**
 *
 * @author Asus
 */
public class ValueComparator implements Comparator<Tag> {
    Map<Tag, Integer> base;
    
    public ValueComparator(Map<Tag, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    @Override
    public int compare(Tag a, Tag b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

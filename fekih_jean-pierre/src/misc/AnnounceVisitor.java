package misc;

import java.util.Collection;
import data.AnnounceData;

public interface AnnounceVisitor {

	public void visit(Collection<AnnounceData> c);
}

package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseBundlesLocalSource {
	public List<Bundle> listBundles() throws JMOPSourceException;

	public void createBundle(Bundle bundle) throws JMOPSourceException;

	public void deleteBundle(Bundle bundle) throws JMOPSourceException;

	public void renameBundle(Bundle oldBundle, Bundle newBundle) throws JMOPSourceException;

	public void saveBundleChanges(Bundle bundle) throws JMOPSourceException;
}

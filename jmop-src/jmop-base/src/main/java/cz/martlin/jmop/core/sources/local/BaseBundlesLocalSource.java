package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;

public interface BaseBundlesLocalSource {
	public List<Bundle> listBundles();
	public void createBundle(Bundle bundle);
	public void deleteBundle(Bundle bundle);
	public void renameBundle(Bundle oldBundle, Bundle newBundle);
	
	public void saveBundleChanges(Bundle bundle);
}

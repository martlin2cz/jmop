package cz.martlin.jmop.common.musicbase;

public interface BaseMusicbase extends BaseMusicbaseLoading, BaseMusicbaseModifing, TracksSource {

	public void load();

	public void terminate();
	
}

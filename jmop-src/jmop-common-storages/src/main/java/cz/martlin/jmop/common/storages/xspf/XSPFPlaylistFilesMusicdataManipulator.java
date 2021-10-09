package cz.martlin.jmop.common.storages.xspf;

import cz.martlin.jmop.common.storages.fileobjects.BaseFileObjectIO;
import cz.martlin.jmop.common.storages.fileobjects.BaseFileObjectManipulator;
import cz.martlin.jmop.common.storages.musicdatafile.CommonMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.playlists.BasePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.common.storages.playlists.FailsavePlaylistMetaInfoManager;
import cz.martlin.jmop.common.storages.playlists.FormatingValueToAndFromStringConverters;
import cz.martlin.jmop.common.storages.playlists.ValueToAndFromStringMetaInfoManager;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.elements.XSPFFile;

/**
 * An musicdata file manipulator working with the XSPF files.
 * 
 * @author martin
 *
 */
public class XSPFPlaylistFilesMusicdataManipulator extends CommonMusicdataFileManipulator<XSPFFile> {

	private static final String XSPF_FILE_EXTENSION = "xspf";

	private XSPFPlaylistFilesMusicdataManipulator(BaseFileObjectIO<XSPFFile> io,
			BaseFileObjectManipulator<XSPFFile> extender) {
		super(XSPF_FILE_EXTENSION, io, extender);
	}
	
	/**
	 * Creates the "week" (fail-immediatelly) implementation.
	 * 
	 * @param reporter
	 * @return
	 */
	public static XSPFPlaylistFilesMusicdataManipulator createWeak(BaseErrorReporter reporter) {
		BaseFileObjectIO<XSPFFile> io = new XSPFPlaylistIO(reporter);
		BaseValueToAndFromStringConverters converters = new FormatingValueToAndFromStringConverters();

		BasePlaylistMetaInfoManager<XSPFCommon> mim = createMetaInfoManager(converters);

		JMOPtoXSFPAdapter adapter = new JMOPtoXSFPAdapter(mim);

		XSPFPlaylistTracksManager tracker = new XSPFPlaylistTracksManager(adapter);
		BaseFileObjectManipulator<XSPFFile> extender = new XSPFPlaylistManipulator(adapter, tracker);

		return new XSPFPlaylistFilesMusicdataManipulator(io, extender);
	}

	/**
	 * Creates the "failsave" (robust) implementation.
	 * 
	 * @param reporter
	 * @return
	 */
	public static XSPFPlaylistFilesMusicdataManipulator createFailsave(BaseErrorReporter reporter) {
		BaseFileObjectIO<XSPFFile> io = new XSPFPlaylistIO(reporter);
		BaseValueToAndFromStringConverters converters = new FormatingValueToAndFromStringConverters();

		BasePlaylistMetaInfoManager<XSPFCommon> mim = createMetaInfoManager(converters);
		BasePlaylistMetaInfoManager<XSPFCommon> failsaveMim = new FailsavePlaylistMetaInfoManager<>(mim, reporter);

		JMOPtoXSFPAdapter adapter = new FailsaveJMOPToXSPFAdapter(failsaveMim, reporter);
		XSPFPlaylistTracksManager tracker = new XSPFPlaylistTracksManager(adapter);
		BaseFileObjectManipulator<XSPFFile> extender = new XSPFPlaylistManipulator(adapter, tracker);

		return new XSPFPlaylistFilesMusicdataManipulator(io, extender);
	}
	
	private static ValueToAndFromStringMetaInfoManager<XSPFCommon> createMetaInfoManager(
			BaseValueToAndFromStringConverters converters) {

//		return new XSPFExtensionElemsChildsMetaInfoManager(converters);
		return new XSPFExtensionElemsAttrsMetaInfoManager(converters);
	}

}

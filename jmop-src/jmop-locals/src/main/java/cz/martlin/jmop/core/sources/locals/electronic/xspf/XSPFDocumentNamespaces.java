package cz.martlin.jmop.core.sources.locals.electronic.xspf;

public enum XSPFDocumentNamespaces {
	XSPF(null, "http://xspf.org/ns/0/"),
	JMOP("jmop", XSPFPlaylistFilesLoaderStorer.APPLICATION_URL + "/schemas/xspf-extension.xsd");

	private final String prefix;
	private final String url;

	private XSPFDocumentNamespaces(String prefix, String url) {
		this.prefix = prefix;
		this.url = url;
	}

	public String namify(String name) {
		if (prefix != null) {
			return prefix + ":" + name;
		} else {
			return name;
		}
	}
	
	public String namifyXMLNS() {
		if (prefix != null) {
			return "xmlns:" + prefix;
		} else {
			return "xmlns";
		}
	}

	public String getUrl() {
		return url;
	}

}

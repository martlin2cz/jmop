package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.PlaylistFileData;

public class DefaultPlaylistLoader implements AbstractPlaylistLoader {

	private final static XSPFParserComposer PARSER_COMPOSER = new XSPFParserComposer();

	@Override
	public PlaylistFileData load(Bundle bundle, File file, boolean onlyMetadata) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			PlaylistFileData data = new PlaylistFileData();
			PARSER_COMPOSER.parse(bundle, document, data, onlyMetadata);
			
			return data;
		} catch (Exception e) {
			throw new IOException("Cannot load playlist file", e);
		}
	}

	@Override
	public void save(PlaylistFileData data, File file) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			PARSER_COMPOSER.compose(data, document);

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new IOException("Cannot save playlist file", e);
		}
	}

	@Override
	public String getFileExtension() {
		return XSPFParserComposer.FILE_EXTENSION;
	}

}

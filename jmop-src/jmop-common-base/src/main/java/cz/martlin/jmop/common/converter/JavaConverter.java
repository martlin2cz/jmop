package cz.martlin.jmop.common.converter;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Source: https://stackoverflow.com/questions/41784397/convert-mp3-to-wav-in-java?rq=1
 * @author martin
 *
 */
public class JavaConverter {

	
	public void mp3ToWav(File mp3Data, File wavData) throws UnsupportedAudioFileException, IOException {
	    // open stream
	    AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3Data);
	    AudioFormat sourceFormat = mp3Stream.getFormat();
	    // create audio format object for the desired stream/audio format
	    // this is *not* the same as the file format (wav)
	    AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
	        sourceFormat.getSampleRate(), 16, 
	        sourceFormat.getChannels(), 
	        sourceFormat.getChannels() * 2,
	        sourceFormat.getSampleRate(),
	        false);
	    // create stream that delivers the desired format
	    AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
	    // write stream into a file with file format wav
	    AudioSystem.write(converted, Type.WAVE, wavData);
	}
}

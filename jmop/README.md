# JMOP

 ![logo](graphics/logo.svg) 

JMOP is Java-based music player working both online and offline.

# What?

JMOP holds its own collection of music. This music is beeing played from some source (well, currently only Youtube is supported). Each (music) video is at first play downloaded and converted into MP3. At the later play it is played from the disk. This saves your bandwidth (or even allows you to listen your favourite music when you don't have access to internet) and also creates simple audioteque at your local computer.
  
# Screenshots
![Playing with (down)loading of track](screens/screens/play-and-load.png) 
![(Down)loading more than one track](screens/and-1-more.png) 
 
# Download
See GitHub [](https://github.com/martlin2cz/jmop/releases "Releases") site to get the latest version or take look into `releases` folder in the project root. 

# Help
The JMOP user help files (in english and czech) can be found inside of the `/jmop/src/main/resources/cz/martlin/jmop/gui/help` directory. See them online: [](https://github.com/martlin2cz/jmop/blob/master/jmop/src/main/resources/cz/martlin/jmop/gui/help/help_cs.html "czech"), [](https://github.com/martlin2cz/jmop/blob/master/jmop/src/main/resources/cz/martlin/jmop/gui/help/help_en.html "english"). 

The same help is accesible from the app by menu item _Help_ or by pressing _F1_.

# Build from sources
To build JMOP from sources, use maven. For instance `mvn clean install`. Then run as usual.

You can modify JMOP behaviour quite a lot by changing line `BaseJMOPBuilder builder = new DefaultJMOPPlayerBuilder();` in `JMOPGUIApp` class. JMOP builder generates the core object, take a look to its default implementation. You can modify and provide updated to the builder any part of the application core.

# TODO
 - [ ] add more sources (Soundcloud for instance)
 - [ ] add dynamic search and autocomplete
 - [ ] allow to import playlists
 - [ ] fix various bugs
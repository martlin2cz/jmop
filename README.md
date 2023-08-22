# JMOP

 ![logo](graphics/logo.svg) 

JMOP is very simple (console-based) Java based music player. You can see it as an acronym of "Java music _only_ player". And that's mostly everything you should know about.

# But...?

Apart from the usual music players, JMOP can play **only and only** what's located in its _musicbase_. It's like a very simple library of your music. (It's just directory in specific format.)

The main idea of JMOP is to play music you have locally on your computer, not beeing annoyed by some extra GUI window. The music shall just flow, and you may not have to even bee thinking about any music player been running.

And why just command-line app? Well, It's a hobby project and mantaining good and user-friendly GUI is extremelly time-consuming. If you want to see _bad_ UX, try the version 1.

# Screenshots
![Working with JMOP](screens/jmop-cli.png) 
 
# How to start
1. Dedicate folder for your musicbase.
2. Start jmop by running
````
$ cd dist
$ ./jmop-player.sh /your/favourite/music/directory #or jmop-player.bat
````
3. If everything goes well, you can start building your own musicbase, like:
````
> create bundle Ventolin
> create track "Disco science" bundle=Ventolin duration=3:15 set-file=~/music/ventolin/disco-science.mp3
> create playlist best-of-ventolin bundle=Ventolin
> playlist Ventolin/best-of-ventolin add "Ventolin/Disco science"
````
4. And then play:
````
play playlist best-of-ventolin
pause
next
stop
````
5. If that's too anoying for you, you can use sourcery to import your music, various ways, like:
````
$ ./jmop-sourcery.sh import-tracks --create-bundle MidiLidi ~/music/midilidi/*.mp3
$ ./jmop-sourcery.sh import-playlist --into-bundle MidiLidi ~/music/midilidi/OperaceKindigo.xspf
````


# Download
See GitHub [](https://github.com/martlin2cz/jmop/releases "Releases") site to get the latest version. **Important note:** The project is still under development, expected less-then-more stable releases. Rather checkout the latest version and build from sources.

# Build from sources
To build JMOP from sources, use _Apache maven_. Naviage to `jmop-player-cli` module and build. For instance `mvn clean package`. Then run as usual, `mvn exec:java` for instance.

To prepare executable binaries, navigate to `jmop-player-cli` module and run the `./release/do-the-release.sh`. It will output the ZIP containing all the nescessary files, so with the Windows BAT and unix Bash scripts to run the JMOP.

# Requirements
- any Windows/Unix (and possibly Mac too, but didn't tried)
- Java > 11


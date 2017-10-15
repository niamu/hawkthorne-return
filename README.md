# Return to the Center of Hawkthorne

A multiplayer, 2D platformer which targets the browser and is based on
the
[Digital Estate Planning](http://en.wikipedia.org/wiki/Digital_Estate_Planning)
episode of
[Community](http://www.imdb.com/title/tt1439629/?ref_=fn_al_tt_1).

Return to the Center of Hawkthorne uses assets from the original
single player game;
[Journey to the Center of Hawkthorne](https://github.com/hawkthorne/hawkthorne-journey).


## Goals

Currently the goals of this project are not clearly defined as it is
mostly an exploration of what is possible as a web game.

However, there is no current plan to have feature parity of any kind
with the original Journey to the Center of Hawkthorne game. This
project is an opportunity to explore new game mechanics and
storytelling that will work best as a multiplayer experience.


## Development

The game is written in [Clojure](https://clojure.org) and uses
[play-cljs](https://github.com/oakes/play-cljs) for the game on the client.

To start the game server for development install
[Leiningen](https://leiningen.org) and run:

```BASH
lein run
```

The web server will start on [localhost:8080](http://localhost:8080)
where the game will load and you can move your player around the
level.

Changes made to *.cljs and *.cljc source files will perform hot
reloading with [figwheel](https://github.com/bhauman/lein-figwheel).


## Testing

Eventually there will be a suite of tests that confirm player physics
behave as expected so we can generate new levels based on maximum jump
heights and have the physics of the player change with ease during
development stages of the game.

Until then, it is possible to build the game into a Docker container
to test out the features of the game without needing to install
Leiningen or Java on your system.

```BASH
./script/build.sh;
docker run --rm -it -p 8080:8080 hawkthorne;
```

## License

Distributed under the Eclipse Public License either version 1.0 or
(at your option) any later version.

Artwork and audio files are licensed under
[CC BY-NC 3.0](http://creativecommons.org/licenses/by-nc/3.0/).
Artwork includes all .png, .psd, .ogg, and .wav files.

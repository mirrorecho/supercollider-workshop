// --------------------------------------------------------------------------------

// GENERAL ENVIRONMENT/LANGUAGE NOTES

// double clicking parens, square, or curly brackets highlights!!
// Cmd + B boots server
// Cmd + Shift + p clears post window
// Cmd + m (or click on server in lower right) to see server meter


// note that "yooooo" posts twice
(
"yooooo".postln;
)

// s is the only built-in variable... it's for referencing the server

// execute 2 statements at once:
Pbind().play; Pbind(\degree, 3).play;

// SIMPLE MATH
// order of operations: msgs evaluated first, operators evaluated left to right
1 + 3.squared;
(1 + 3).squared;
2 + 2 * 2;
2 + (2 * 2);

4 ** 2; // exponent

// FUN WITH SPEAK
// speak mac!!!!!!!!
"hello world".speak;
"hello world".scramble.speak;
"randall west".reverse.speak;


// ARRAYS
(0..9); // creates an array
[13,6,2,3,6,2,3,5,4,36].size;  //... size returns array length


// array math:
[100, 200, 300, 400, 500] + 1;
[100, 200, 300, 400, 500] + [1, 2];
[100, 200, 300, 400, 500] * 2;
[100, 200, 300, 400, 500] * [1.5, 2];
[100, 200, 300, 400, 500] ++ 600;
[100, 200, 300, 400, 500] ++ [600, 700];


// VARIABLES
// alternative variable declaration
~yoServerVariable = "Ya!"; // ~ and a,b,c sort of like "global" variables... (really "Environment" variable)... even work accross windows
~yoServerVariable;

~anyEnvironmentVariableOK; // just fine... returns nil.

// whereas var declares local variables (within a code block or function)
(
var yo = "Hello"; // note that this local variable must be first
yo;
)

// --------------------------------------------------------------------------------

// PATTERNS (Pbinds) .... THIS IS A GOOD EXAMPLE OF ORGANIZED PBIND CODE WITH PLAYER
(
~score = Pbind(
	\degree, Pseq([
		0,
		Pwhite(1,10,inf)],
		inf),
	\amp, Prand([0.1,0.2,0.3,0.4], inf),
	\dur, Pseq([0.3,0.15], inf),
	\dur, 0.1
);
// note that the Pbind (not the EventStreamPlayer) implements trace
~player = ~score.trace.play.stop;
)

~player.play;
~player.resume;
~player.stop;

// SEE ALL SCALES (FOR USE IN DEGREE WITH PATTERNS)
Scale.directory;


// DURATIONS
// with dur, you can use something like Rest(1.5) to specify rests
// dur is really duration of note in beats (defaults to 1 beat per second)... can effect with \tempo arg or with TempoClock passed to the Pbind play function


// examples with TempoClock and tempo arg ... also modal and chromatic transpose
(
Pbind(
	\scale, Scale.shawqAfza,
	\degree, Pseq([0,1,2,3,4,5,6,7,8]),
	// \mtranspose, 1, // modal transpose
	\ctranspose, Pwrand([0,4],[0.9, 0.1], inf), // chromatic transpose
	\dur, Pwrand([1,1/2,1/4], [0.25, 0.5, 0.25], inf),
).trace.play(TempoClock(120/60));
)

(
Pbind(*[
	// note:Pseries(0, 1, 24),
	freq: Pgeom(440, 9/8, 24),
	dur:Pgeom(1.0, 0.9, inf),
	tempo: 120/60,
	]
).trace.play;
)


// AN EXAMPLE WITH RESTS MIXED IN WITH NOTE VALES (and two Pbinds starting at same time)
(
Pbind(*[
	scale: Scale.zamzam,
	degree: Pseq([[0,1],2, Prand([[2,3], [2,4], [2,6], [2,8], \rest])], inf),
	dur: 1/2,
	legato: 0.01,
	amp: Pwhite(0.3, 0.6),
	tempo:120/60,
]).trace.play;

Pbind(*[
	scale: Scale.zamzam,
	degree: Pwrand([24, 25, 26, \rest], [0.5, 0.25, 0.25], inf),
	dur: 1/3,
	legato: 0.01,
	amp: Pwhite(0.01, 0.1),
	tempo:120/60,
]).trace.play;

)


// "Pn" = REPEATEDLY EMBED A PATTERN
// AN EXAMPLE WITH BOTH "Pn" and "Pshuf"

(
Pbind(*[
	// freq: Pgeom(440, Prand([4/3, 3/4, 3/2, 2/3], inf), inf),// moving up/down just-tuned fourths or fifths
	// freq: Pseries(440, 4, 60),
	// freq: Prand([1, 5/6, 6/5, 10/9, 9/10, 2/3, 3/4, 4/3, 3/2] * 440.0, inf),
	note: Pn(Pshuf((0..12),4), inf),
	// note: Pslide([0,1,2,3,4,5,6],inf,2,1),
	dur: Prand([0.25, 0.125], inf),
	amp: Pwhite(0.2, 0.6)
]).trace.play;

)





// --------------------------------------------------------------------------------
// BASIC SYNTH STUFF

// MULTI CHANNEL EXPANSION
// ! 2 exands to two channels

// NOTE THAT FUNCTION ITSELF IMPLEMENTS PLAY... HERE'S A NONSENSE EXAMPLE
a = {"Yo".postln;}
a.play;

(
{ SinOsc.ar(
	freq: MouseX.kr(100,1000).poll,
	mul: MouseY.kr(0.1, 0.6)
	) ! 2;
}.play;

)

// RECORDING:
s.record;
s.stopRecording;







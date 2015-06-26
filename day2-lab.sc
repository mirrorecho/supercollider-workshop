{SinOsc.ar(amp:0.4);}.play;

{SinOsc.ar(freq: LFNoise1.kr(10).range(500, 1500), mul:0.1);}.play;

//PBIND TYPES: \note, \rest, \midi

// COMPARE
(
Pbind(
	\type, \rest,
	\myname,  "Randall West".scramble.postln;
	\dur, Pwhite(0.5, 2)
).trace.play;
)
// with
(
Pbind(
	\type, \rest,
	\myname, Pfunc({
		"Randall West".scramble.postln; }),
		\dur, Pwhite(0.5, 2)
	).trace.play;
)

// MIDI (note some midi initialization with supercollider
MIDIClient.init;
o = MIDIOut(0)
(
Pbind(
	\type, \midi,
	\midinote, Pwrand([48,50], [0.8,0.2], inf),
	\amp, 0.1,
	\dur, 1,
	\legato, 0.9,
	\midiout, o
).trace.play;
)

// MORE MIDI
MIDIIn.connectAll; // connect synth


// "Pkey" STORE VALUE FOR USE LATER IN "Pbind"
(
Pbind(
	\degree, Pwhite(0,10),
	\dur, Pkey(\degree)
).trace.play;
)
// note that this won't work:
(
Pbind(
	\degree, Pwhite(0,10),
	\dur, \degree
).trace.play;
)


// QUANTIZE Pbind start
(
Pbind(
	\degree, Pwhite(0,9),
	\dur, 0.5
).play(quant:1);
)
(
Pbind(
	\degree, Pwhite(8,20),
	\dur, 0.25
).play(quant:0.5);
)

// SEQUENCING PATTERNS
// fork creates a routine Routine:
(
{
	2.wait;
	"randall west".scramble.postln;
	4.wait;
	"ian le".scramble.postln;
}.fork;
)
//make this work
(
{
	~player1 = ~score1.play;
	2.wait;
	~player2 = ~score2.play;
	2.wait;
	"FORK IS DONE".postln;
}
)

// SPAWNER
(
Pspawner({ arg sp;
	sp.par(~infScore0);
	sp.seq(~score1);
	sp.seq(~score2);
	sp.wait(2);
	sp.seq(Ppar([~score3, ~score4]));

	9.do {
		sp.par(~score2);
		sp.wait(0.4);
	};
	sp.wait();

	sp.suspendAll;
})
)


Pbind(
	\degree, Pwhite(0,10),
	\whatever, Pfunc({arg ev; a = ev.at(\degree) }) // this works because Pfunc by default takes the current event as an argument
)


// good technique for
~masterAmp = 0.1;
(
Pbind(
	\amp, Pwhite(0.1, 0.9) * ~masterAmp;
).trace.play;
)

// easy volume scaling:
s.volume.gui;

// ANOTHER LIST FUNCTION:
[0,1,2,3,4].mirror;




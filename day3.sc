

{ SinOsc.ar(mul:0.2) }.play;

{ SinOsc.ar(mul:0.2) }.scope;

{ SinOsc.ar(mul:0.2) }.plot(minval: -1, maxval:1);

{ SinOsc.ar(freq:1) }.plot(minval: -1, maxval:1, duration:1);

{ SinOsc.kr(freq:0.1).poll }.play;

{ Saw.ar(mul:0.1) }.scope;

{ Saw.kr(freq:1) }.plot(minval: -1, maxval:1, duration:1); // note that this produces odd results... try this LFsaw instead:
{ LFSaw.kr(freq:1) }.plot(minval: -1, maxval:1, duration:1);

{ MouseX.kr(0, 100).poll }.play;

//Nyquist theorem example (freq goes down.... ):
(
{
	SinOsc.ar(
		freq:Line.kr(100, 40000,10),
		mul:0.05
	)
}.play;
)


// kr is every 64 seconds
(
{
	SinOsc.ar(
		freq:LFSaw.kr(freq:2).range(100,800),
		mul:0.1);
}.play;
)

// range, exprange, linlin, etc. are your friends for scaling (rather than always using mul/add!!
a = Array.series(20,0,1);
a.linlin(0, 20, 0, 50);



a = { SinOsc.ar(mul:Line.kr(0.4, 0, 1, doneAction:2)) }.play;
b = { SinOsc.ar(freq:550, mul:0.1) }.play;
a.free;

// THIS LOOKS AT THE SERVER!
s.plotTree;
s.freeAll;

// u number in bottom right is the total # of unit generators
// s in bottom right is total # of synths

// function that creates a synth:
(
b = { arg out = 55, freq=1220, amp=0.4;
	Out.ar(55, SinOsc.ar(freq: freq, mul:Line.kr(amp, 0, 0.04, doneAction:2)));
}.play;
)

(
{
	Splay.ar(SinOsc.ar(freq:[200,400,800,1600], mul:0.1), spread:1);
}.play;
)


// why is this not working...?
m = { FreeVerb2.ar(in:SoundIn.ar(0), room:0.8, mix:0.5); }.play;

r = { FreeVerb.ar(in:In.ar(55), room:0.8, mix:0.5); }.play;

b.set(\freq, 660);
b.free;

a = { Out.ar(1, SinOsc.ar(mul:0.1)); }.play;

// AUDIO BUSES....
~masterBus = Bus.audio(s, 2);

// PANNING... also look at page 80 for example...

// SYNTH DEFS
(
SynthDef("mysynth", { arg freq, amp, pan=0;
	Out.ar(0,
		Pan2.ar(SinOsc.ar(freq, mul:amp),pan);
	);
}).add;
)

Synth("mysynth",[\freq, 430, \amp, 0.3, \pan, 0.8]);

(
PmonoArtic("mysynth",
	\degree, Pseq([1,2,3,4,5], inf)
).play;
)

{ SinOsc.ar }.asSynthDef;

// with params, can type comma, new line, then tab, to cycle through them in the intellisense


(
SynthDef("coolsynth", { arg freq=440, amp=0.4;
	Out.ar(0, Saw.ar(freq!2, mul:Line.kr(amp, 0, 1, doneAction:2)));
}
).add;
)


Synth("coolsynth")
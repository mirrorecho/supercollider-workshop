// AUDIO FILES
// buffer

b = Buffer.read(s, "/System/Library/Sounds/Purr.aiff");

b.play;
b.numChannels;
b.numFrames; // # of (frames of) samples
b.duration; // seconds

// free all buffers
Buffer.freeAll;

{PlayBuf.ar(2, bufnum:b, rate:0.5, doneAction:2);}.play; // done action is important!

(
{
	PlayBuf.ar(2,
		bufnum:b,
		rate:LFNoise2.ar(0.5).range(0.5, 2), // negative rate is backwards!
		loop:1,
)* LFPulse.kr(MouseX.kr(1,10), width:0.2).range(0,1);
}.play; // done action is important!
)

// LFPulse ... fun pulsing a signal


// can use multichannel expansion as a great way to create many signals

{SinOsc.ar * Line.kr(0.5, 0, 2, doneAction:2) }.play;
// note same as:
{SinOsc.ar(mul: Line.kr(0.5, 0, 2, doneAction:2) ) }.play;


// Array.series is helpful

s.plotTree;

// ENVELOPE EXAMPLES:
// fixed time examples:
// curve examples
Env.perc(curve:-2).plot;
Env.perc(curve:2).plot;
Env.triangle.plot;

Env.linen(0.3, 2, 1).plot;
Env.pairs([[0,0], [2, 0.5], [4, 0.1], [5, 0]], -6).plot;

// remember Kyong Mee's lecture and use these!!!!!!!!!!!!!!!!!!!!!!!!!!

(
{
	var sig, env;
	sig = SinOsc.ar;
	env = Env.perc(level:0.6, releaseTime:6).kr(doneAction:2).poll;
	sig = sig * env;
	Out.ar(0, sig!2);
}.play;
)

s.plotTree;

// testing synth devs / envelopes:

(
SynthDef("ya", { arg freq=440, amp=0.8, releaseTime=3, attackTime=0.1;
	var sig, env;
	sig = SinOsc.ar(freq, mul:amp);
	env = Env.perc(attackTime:attackTime, releaseTime:releaseTime).kr(doneAction:2);
	sig = sig * env;
	Out.ar(0, sig);
}).add;
)

Env.adsr.plot;


x = Synth("ya", [\releaseTime, 20]);

x = Synth("ya", [\releaseTime, 0.5]);

// note that envelope properties can't be changed once synth is running... (maybe because Env is actually not a Ugen?)
x.set(\releaseTime, 0.5);
x.set(\freq, 880);

(
SynthDef("sus", { arg freq=440, amp=0.8, gate=1;
	var sig, env;
	sig = Saw.ar(freq, mul:amp);
	env = EnvGen.kr(Env.adsr, gate:gate, doneAction:2);
	sig = sig * env;
	Out.ar(0,sig);
}).add;
)

y = Synth("sus");
y.set(\freq, 880);
y.set(\gate, 0);

// when using SynthDef, these params are auto sent:
// freq (from note, degree, scale, etc.)
// !!! Look at Pattern Guide 07: Value Conversions


true.not;
// ( .not ) reverses logical value...

// ORDER OF EXECUTION:
m.set(\amp, 0.1);

~fxBus = Bus.audio(s, 1);
~masterBus = Bus.audio(s, 1);

// create SynthDefs
(
SynthDef("noise", {Out.ar(~fxBus, WhiteNoise.ar(0.5))}).add;

SynthDef("filter", {Out.ar(~masterBus, BPF.ar(in: In.ar(~fxBus), freq:MouseY.kr(1000, 5000), rq:0.1))}).add;

SynthDef("masterOut", {arg amp = 1;
	Out.ar(0, In.ar(~masterBus) * Lag.kr(amp, 1))}).add;
)

// NOTE THAT THESE MUST BE RUN IN THIS ORDER....
m = Synth("masterOut");
f = Synth("filter");
n = Synth("noise");
/// by default, run in reverse order... I.E. (if X depends on Y, run Y first).
// EVEN BETTER:
n = Synth("noise", addAction:'addToHead');
m = Synth("masterOut", addAction:'addToTail')
f = Synth("filter", addAction:'addAfter', target:n);


// read the plt tree from top to bottom... that's the order that things are filtered!!!!
s.plotTree;


// EVEN BETTER THAN THAT: define groups
(
~sources = Group;
~effects = Group.new(~sources, \addAfter);
~master = Group.new(~effects, \addAfter);
)

// useful for creating and destroying things independantly!!!

{Out.ar(~effec)}.add(target: ~sources)

// CONTROL BUSES: (e.g. could control paramaters on several synths all at once)
~myControl = Bus.control(s, 1);



// (e.g. could control from cell phone!!!!!)

// what's the pirate pad link again?
// FOR TOMORROW ... MAKE A DOUBLE CLICK...


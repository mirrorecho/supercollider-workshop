
// MIDI
MIDIIn.connectAll;

// LINUX NOTE
// KxStudio ... Catia applicaiton shows all jack connections

// See if it's working...
MIDIFunc.trace(false);

// Finally...
(
MIDIdef.noteOn(\test, { arg vel, midinote;
	[vel, midinote].postln;
	Pbind(
		\midinote, Pseq([midinote]),
		\amp, vel.linlin(0, 44, 0.000001, 0.8),
		\legato, 0.2,
	).play;
	}
);
)

// playing multiple MIDI notes at once
(
SynthDef("midiSynth", { arg freq=440, amp=0.1, gate=1;
	var sig, env;
	sig = Saw.ar(freq:freq, mul:amp);
	sig = RLPF.ar(sig, LFNoise2.kr(9).range(freq, 9900), 0.2);
	env = EnvGen.kr(Env.adsr(0.001, 0.1, 0.4, 2), gate:gate, doneAction:2);
	sig = sig * env;
	Out.ar(~masterBus, sig!2);
}).add;
)

x = Synth("midiSynth");
x.set(\gate, 1);
x.set(\gate, 0);
x.release;

// A way to keep track of synths for midi notes as they are played
// there are quarks to do this for you... but doesn't seem to be a built-in library for this.\:
(
~noteArray = Array.newClear(128);

MIDIdef.noteOn(\noteOn, {arg vel, midinote;
	~noteArray[midinote] = Synth("midiSynth", [
		\freq, (midinote + rand(0.2) - 0.1).midicps,
		\amp, vel.linexp(0, 127, 0.01, 0.69)
	]);
	}
);

MIDIdef.noteOff(\noteOff, {arg vel, midinote;
	midinote.postln;
	~noteArray[midinote].release; // same as ~noteArray[midinote].set(\gate, 0);
});
)


// MIDI control values:
(
MIDIdef.cc(\thisCC, { arg val, num;
	[val, num].postln; // just to see an example;
	},
	ccNum: 10, // how to specify specific control #
	chan:0 // how to specify only a specific channel
);
)

// SENDING MIDI out
// See: "http://piratepad.net/supercollider-2015-MIDI"
// (set up under Audio MIDI set in mac utilities)

// OSC Messaging (communications protocal)
/*
MSG looks like:
/stuff/yo/note 60
... the "/stuff/yo/note" can be anything, e.g. "/yo/ha/place" etc.
*/
// to send:
~bruno = NetAddr("192.168.177.91", 57120);
~bruno.sendMsg('/pineapple/blue', 22);
// to receive:
OSCdef(\listen1, func:{arg msg;
	var freq, sig;
	msg.postln;
	freq = Line.kr(msg[1], msg[2], msg[3], doneAction:2);
	sig = Saw.ar(freq:freq * [0.99, 1.01], mul: 0.1);
	// LFnoise1.kr ...
}, path:'/pineapple/wow');

// OSC msgs with control bus
// receiving:
OSCdef(\listen1, func:{arg msg;
	~controlBus.value = msg[1];
}, path:'/pineapple/wow');

~controlBus = Bus.control(s,1);

{ In.kr(~controlBus).poll }.play; // to see the

// LOADING FILES:
"filename.scd".loadRelative;

// sending... serialized objects (probably just connect language to another server)

// check out Instrument (Instr) ... separate package (compiles every time it runs the synth) ...

// quick way to assign from array to variables
#w,c,v,f=[1,2,3,4];

// how to do Pbind callbacks when finished?

// Look into this for quantizing Pbind;
Quant;

// check these out... (oscillators postulated by Xenakis)
Gendy1
Gendy2
Gendy3

Pitch;
// Tartini... more complex feature analysis

// look into redUniverse Quark

// QUARKS (language side):
Quarks.gui;

// ALSO CAN LOOK INTO PLUGINS:

// DSP coding... could do it in CPP, or look into ... Faust language

// SuperNova is another server option (as opposed to scsynth)... auto load-balances cores

// call this WITHIN A "Routine" in order to wait:
s.sync;

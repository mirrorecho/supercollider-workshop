
SynthDef( \noise, {
	arg freq=440, gate=1, amp=1.0, slideTime = 1.0;
	var sig, sig2, env;
	freq = Lag.kr(freq, slideTime);
	sig = Resonz.ar(WhiteNoise.ar(1.98!2), freq, 0.04, 12) +
	Resonz.ar(WhiteNoise.ar(0.6!2), freq * 2, 0.01, 6);
	sig = sig * amp;
	sig2 = Splay.ar(sig, spread:0.9);
	sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.4);
	env = EnvGen.kr(Env.asr(8, 1, 8, \sine), gate:gate, doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;

SynthDef( \pop, {
	arg freq=440, gate=1, amp=1.0, slideTime = 1.0;
	var sig, sig2, env;
	freq = Lag.kr(freq, slideTime);
	sig = Resonz.ar(WhiteNoise.ar(1.98!2), freq, 0.04, 12) +
	Resonz.ar(WhiteNoise.ar(0.6!2), freq * 2, 0.01, 6);
	sig = sig * amp;
	sig2 = Splay.ar(sig, spread:0.9);
	sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.4);
	env = EnvGen.kr(Env.perc, gate:gate, doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;

"LOADED DAY 2 SOUNDS".postln;
(

SynthDef( \noisyVoiceSwell, {
	arg freq=220,
	gate=1,
	amp=0.4,
	notedur=4;
	//slideTime = 1.0;
	var sig, sig2, env;
	//freq = Lag.kr(freq, slideTime);  // remove this?
	sig = Resonz.ar(Crackle.ar(1.98!2), freq, 0.04, 12) +
	Resonz.ar(WhiteNoise.ar(0.6!2), freq * 2, 0.01, 6) +
	Resonz.ar(WhiteNoise.ar(0.2!2), 300, 0.001, 4) +
	Resonz.ar(WhiteNoise.ar(0.1!2), 870, 0.001, 2) +
	Resonz.ar(WhiteNoise.ar(0.04!2), 2250, 0.001, 1);
	sig = sig * amp;
	sig2 = Splay.ar(sig, spread:0.9);
	//sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.4); // HOW TO MAKE THIS UNIVERSAL?
	env = EnvGen.kr(Env.perc(attackTime:notedur, releaseTime:0.01, level:1, curve:4), doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;

SynthDef( \pop, {
	arg freq=440, gate=1, amp=0.5, notedur=1;
	var sig, sig2, env;
	//freq = Lag.kr(freq, slideTime);
	sig = Resonz.ar(WhiteNoise.ar(1.98!2), freq, 0.04, 12) +
	Resonz.ar(WhiteNoise.ar(0.6!2), freq * 2, 0.01, 6);
	sig = sig * amp;
	sig2 = Splay.ar(sig, spread:0.9);
	//sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.4);
	env = EnvGen.kr(Env.perc(releaseTime:notedur, curve:-9), gate:gate, doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;

SynthDef( \noise, {
	arg freq=440, gate=1, amp=0.5, slideTime=0.01;
	var sig, sig2, env;
	freq = Lag.kr(freq, slideTime);
	sig = Resonz.ar(WhiteNoise.ar(1.98!2), freq, 0.09, 12);
	sig = sig * amp;
	sig2 = Splay.ar(sig, spread:0.9);
	//sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.4);
	env = EnvGen.kr(Env.asr(slideTime, amp, slideTime, \sine), gate:gate, doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;

SynthDef( \wonky, {
	arg freq=220, jiFreq=110, gate=1, amp=0.4, slideTime = 0.2;
	var sig, sig2, env, jiOsc1, jiOsc2, jiOsc3, ampOsc, sirenSig, jiSig;
	freq = Lag.kr(freq, slideTime);
	// fundamental osc:
	jiOsc1 = (SinOsc.kr((0.01!2), 0, 0.5, 0.5) * jiFreq * 10/9) + jiFreq;
	// formant osc:
	jiOsc2 = (SinOsc.kr((0.133!2), 0, 0.5, 0.5) * jiFreq) + jiFreq;
	// width osc:
	jiOsc3 = (SinOsc.kr((0.2!2), 0, 0.5, 0.5) * jiFreq * 8) + jiFreq;
	ampOsc = SinOsc.ar(freq, 0, 0.5, 0.5);
	sirenSig = Formant.ar(jiOsc1, freq , jiOsc3, 0.04);
	jiSig = Formant.ar(jiFreq, jiOsc2, freq * 2, 0.25 * ampOsc);
	sig = jiSig + sirenSig;
	//sirenSig = sirenSig +
	sig = sig + Klank.ar(`[[freq, jiFreq, freq*2, 800, 1071, 1353, 1723], nil, [1, 1, 1, 1, 1, 1, 1]], PinkNoise.ar(0.001));
	sig2 = Splay.ar(sig, spread:0.9);
	sig2 = FreeVerb2.ar(sig2[0], sig2[1], mix:0.9);
	env = EnvGen.kr(Env.asr(0.2, amp, 2, \sine), gate:gate, doneAction:2);
	sig2 = sig2 * env;
	Out.ar(0, sig2);
}).add;


"LOADED SOUNDS".postln;
)
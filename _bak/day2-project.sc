(
// MAIN SETUP
// could load sounds from external file:
// "~/Code/supercollider-workshop/day2-sounds.sc".loadPaths;

// funny pop sound...
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


// master environment variables (tempo, amp, etc.):
~masterTempo = 120/60;
~masterAmp = 0.8;
~masterFreq = 220; // default reference frequency that everything else is relative to...

"LOADED MAIN ENVIRONMENT VARIABLES AND SYNTHS".postln;


// ---------------------------------------------------------------
// can just use Array.fill instead of making this big function...!
/*
~freqList = {
	arg interval = 3/2, // default to perfect fifths
	number = 4,
	freq = 440.0;
	var retList = [freq];
	(number-1).do { arg counter;
		retList = retList ++ (retList[counter] * interval);
	};
	retList;
}; */

// ---------------------------------------------------------------

// "SCORES" and PLAYER:
// this one in progress...
/*~noiseScore = PmonoArtic(
	"noise",
	\dur, Pseq([20,40,20])
);*/

~popScoreFunc = {
	arg freq = 120, interval=3/2, number=4, dur=1/4,
	amp=0.5, reverse=false;
	Pbind(
		\instrument, "pop",
		\freq, Pseq(Array.fill(number, {arg counter; freq * (interval ** counter) } )),
		\dur, dur,
		// this makes the pops get quieter for each one....
		\amp, Pif(reverse, Pseq(1 / (number..1)), Pseq(1 / (1..number))) * amp * ~masterAmp,
	);
};

~lickFastSweepUp = {arg sp;
	sp.par(~popScoreFunc.value(
		freq:~masterFreq*2,
		interval:5/4,
		number:20,
		dur:1/16,
	));
	/*sp.par(~popScoreFunc.value(
		freq:~masterFreq*2,
		interval:9/8,
		number:10,
		dur:1/8,
	));*/
	sp.seq(~popScoreFunc.value(
		freq:~masterFreq*8,
		interval:1,
		number:20,
		amp: 0.2,
		dur:1/8,
	));
	"LICK: FAST SWEEP UP"
};

// TO DO... make functions (or patterns or something) for these little licks, so that they could be better controlled, repeated with parameters, etc.
~masterScore = Pspawner({arg sp, refFreq=~masterFreq;
	//sp.par(~noiseScore);
	~lickFastSweepUp.value(sp);
	~lickFastSweepUp.value(sp);
	~lickFastSweepUp.value(sp);

	sp.seq(~popScoreFunc.value(
		freq:refFreq/4,
		interval:12/11,
		number:44,
		dur:1/16,
		reverse:true
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq/2,
		interval:12/11,
		number:44,
		amp: 0.4,
		dur:1/4,
		reverse:true
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*8,
		interval:11/12,
		number:22,
		amp: 0.4,
		dur:1/2,
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq*2,
		interval:8/7,
		number:14,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*2,
		interval:7/8,
		number:7,
		dur:1/4,
	));

	sp.seq(~popScoreFunc.value(
		freq:refFreq*8,
		interval:1,
		number:3,
		amp: 0.2,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*8,
		interval:1,
		number:2,
		amp: 0.2,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*8,
		interval:1,
		number:1,
		amp: 0.2,
		dur:1/8,
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq*16,
		interval:1,
		number:8,
		amp: 0.2,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:8/9,
		number:8,
		amp: 0.2,
		dur:1/8,
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq*16,
		interval:1,
		number:4,
		amp: 0.2,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:8/9,
		number:4,
		amp: 0.2,
		dur:1/8,
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq*16,
		interval:1,
		number:16,
		amp: 0.2,
		dur:1/8,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:8/9,
		number:16,
		amp: 0.2,
		dur:1/8,
	));
	// ----------------------------------------------
	sp.par(~popScoreFunc.value(
		freq:refFreq/2,
		interval:16/15,
		number:66,
		amp: 0.4,
		dur:1/16,
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:15/16,
		number:66,
		amp: 0.4,
		dur:1/16,
	));
	sp.par(~popScoreFunc.value(
		freq:refFreq/2,
		interval:16/15,
		number:55,
		amp: 0.2,
		dur:1/8,
		reverse:true
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:15/16,
		number:55,
		amp: 0.2,
		dur:1/8,
		reverse:true
	));
	sp.seq(~popScoreFunc.value(
		freq:refFreq*16,
		interval:15/16,
		number:55,
		amp: 0.2,
		dur:1/8,
		reverse:true
	));

	//sp.wait(12);
	sp.suspendAll;
});

~player = ~masterScore.play(TempoClock(~masterTempo));
"LOADED SCORES AND PLAYER"
)

// ---------------------------------------------------------------
// PLAY / RECORD CONTROLS:
~player.reset;~player.play;
~player.resume;
~player.stop;

s.record;
s.stopRecording;
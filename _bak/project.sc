(
// MAIN SETUP
// could load sounds from external file:
(thisProcess.nowExecutingPath.dirname ++ "/project-sounds.sc").loadPaths;

// master environment variables (tempo, amp, etc.):
~masterTempo = 92/60;
~masterAmp = 0.8;
~masterFreq = 220; // default reference frequency that everything else is relative to...

"LOADED MAIN ENVIRONMENT VARIABLES AND SYNTHS"
)

// ---------------------------------------------------------------

// THE FRAGMENTS AND "SCORES" (Patterns... )
(
~swellPopFunc = {arg note=24, durs=[2,1/2,3/2], amps=[0.4, 0.2, 0], repeats=1;
	Pbind(
		\instrument, Pseq(["noisyVoiceSwell", "pop", "pop"],repeats),
		\note, Pseq([[note, note+7], note+12, \rest], repeats),
		\dur, Pseq(durs, repeats),
		\notedur, Pkey(\dur) / ~masterTempo,
		\amp, Pseq(amps * ~masterAmp, repeats)
	);
};

~clickerFunc = {arg note=24, dur=1/8, amp=0.4, interval=1, number=8, repeats=1, reverseAmp=false;
	Pbind(
		\instrument, "pop",
		\freq, Pseq(Array.fill(number, {arg counter; (note+60).midicps * (interval ** counter) } ), repeats),
		\dur, dur,
		\notedur, Pkey(\dur) / ~masterTempo,
		// this makes the pops get quieter (or louder) for each one....
		\amp, amp * ~masterAmp * Pif(reverseAmp,
			Pseq(1 / (number..1)),
			Pseq(1 / (1..number), repeats)
		),
	);
};

~wonkySlideFunc = {arg notes=[0], durs=[1], amps=[0.4], repeats=1;
	PmonoArtic(
		"wonky",
		\note, Pseq(notes, repeats),
		\amp, Pseq(amps, inf),
		\dur, Pseq(durs, inf),
	);
};

~wonkyLick1 = {arg note = 9, interval=24, durs=[1/4, 7/4], amps=[0.09,0.2], repeats = 4;
	~wonkySlideFunc.value(notes:[note, note+interval], durs:durs, amps:amps, repeats:repeats);
};

~noiseFunc = {arg notes=[0], durs=[1], amps=[0.4], slideTimes=[0.01], repeats=1;
	PmonoArtic(
		"noise",
		\note, Pseq(notes, repeats),
		\amp, Pseq(amps, inf),
		\dur, Pseq(durs, inf),
		\slideTime, Pseq(slideTimes, inf),
	);
};

~score = Pspawner({ arg sp;
	sp.par(~swellPopFunc.value(durs:[8,1,3], note:9));
	sp.wait(4);
	sp.par(~swellPopFunc.value(durs:[4,1,3], note:21, amps:[0.49, 0.1, 0]));
	sp.par(~wonkySlideFunc.value(notes:[36], durs:[4], amps:[0.1]));
	sp.wait(4);
	sp.par(~noiseFunc.value(durs:[4,24], amps:[0.09, 0.02], notes:[18,36]));
	sp.par(~swellPopFunc.value(durs:[4,1,3], note:-2, amps:[0.49, 0.02, 0]));
	sp.par(~swellPopFunc.value(durs:[4,1,3], note:-3, amps:[0.49, 0.2, 0]));
	sp.par(~clickerFunc.value(dur:1/8, number:32, interval:31/32));
	sp.wait(4);
	sp.par(~clickerFunc.value(dur:1/4, number:36, interval:31/32));
	sp.par(~clickerFunc.value(note:12, dur:3/8, number:24, interval:31/32));
	sp.par(~wonkyLick1.value(note:18, repeats:12));
	sp.par(~clickerFunc.value(note:-15, dur:1/4, number:96, interval:32/31, reverseAmp:true));
	sp.par(~swellPopFunc.value(durs:[24,1,1], note:9));
	sp.wait(24);
	sp.par(~clickerFunc.value(dur:1/16, number:32, interval:31/32, note:12, amp:0.2));
	sp.par(~clickerFunc.value(dur:1/8, number:16, interval:31/32));
	sp.wait(2);
	// sp.par(~clickerFunc.value(dur:1/16, number:32, interval:31/32));
	sp.par(~clickerFunc.value(note:36, dur:1/8, number:16, interval:31/32));
	sp.par(~swellPopFunc.value(durs:[2,1,1], note:18));
	sp.wait(4);
});

~player = ~score.play(TempoClock(~masterTempo)).stop;
)

// ---------------------------------------------------------------
// FOR TRYING OUT LITTLE FRAGMENTS:
~clickerFunc.value(dur:1/8, number:32).trace.play(TempoClock(~masterTempo));

~swellPopFunc.value.trace.play(TempoClock(~masterTempo));

~wonkySlideFunc.value(durs:[1,2,3], notes:[24,9,10,12,22,24]).trace.play(TempoClock(~masterTempo));

~noiseFunc.value.trace.play(TempoClock(~masterTempo));

~wonkyLick1.value.trace.play(TempoClock(~masterTempo));

// MAIN PLAY / RECORD CONTROLS:
~player.reset;~player.play;
~player.resume;
~player.stop;

s.record;
s.stopRecording;







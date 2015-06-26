(// STARTUP 1
s.freeAll;
~masterTempoClock = TempoClock(92/60);
~masterAmp = 0.8; // even use this???
~masterFreq = 220; // even use this? ...default reference frequency that everything else is relative to...
~masterBus = Bus.audio(s,2);
(thisProcess.nowExecutingPath.dirname ++ "/project2-synths.sc").loadPaths;
)// ---------------------------------------------------------------------------
(// STARTUP 2
(thisProcess.nowExecutingPath.dirname ++ "/project2-setup.sc").loadPaths;
)// ---------------------------------------------------------------------------
(// DEFAULT MATERIAL
m = (
	octaves:[-12,0,12,24],
	phrase1dur:[1.5, 1.5, 1, 3, 1, 0.5, 0.5, 2, 1] * 1/2,
	phrase1note:[5,4,0,7,\rest,4,5,12, \rest],
	commonNotes:[-12,-10,-5,-2,0,2,4,5,7,10,12,14],

);
l = (
	phrase1:[instrument:"sin", dur:Pseq(m.phrase1dur), note:Pseq(m.phrase1note),
		amp:Pwhite(0.6,0.7)],
	randomBass:[instrument:"bass", dur:1/4, note:Pwrand([-36,-20], [0.8,0.2], inf),
		amp:Pseq(Array.series(60, 0, 0.01) ++ Pn(0.6)) * Pwhite(0.1, 0.4)],
	randomSin:[instrument:"sin", dur:1/4, note:Prand(m.commonNotes,inf),
		amp:Pwhite(0.1,0.4)],
	randomNoise:[instrument:"noise", note:Prand(m.commonNotes,inf),
		dur: Prand([8,4,3,2,1], inf), amp:Pwhite(0.2,0.3)],
	// swell4:[instrument:"voiceSwell",
); "-------- SET DEFAULT MATERIAL --------"
)// ---------------------------------------------------------------------------
// EXAMPLE CODE TO RUN



p = Pbind(\instrument, "blip", \dur, 1/4, \note, Pseq([0,5,7,12], 4));
~add.value(\yo, p);

~lick.value(\highPop, instrument:"pop", dur:1/4, amp:0.6, ampMul:1, number:8, note:36, freqMul:1, repeats:inf);
~lick.value(\highPop, instrument:"blip", dur:1/8, amp:0.2, ampMul:0.9, number:8, note:48, freqMul:1, repeats:inf);

~lick.value(\start1, instrument:"pop", dur:1/4, amp:0.8, ampMul:0.8, number:8, note:12, freqMul:15/16, repeats:inf);
~lick.value(\start1, instrument:"pop", dur:1/8, amp:0.04, ampMul:1.5, number:8, note:12, freqMul:16/15, repeats:inf);
~lick.value(\start1, instrument:"blip",note:-12, freqMul:2, repeats:inf); // up in octaves
~lick.value(\start1, instrument:"blip", dur:3/4, note:2, ampMul:0.9, freqMul:3/2, number:8, repeats:inf); // up in "fifths"
~lick.value(\start1, instrument:"sin",note:0, amp:0.4, ampMul:1/2, freqMul:2, repeats:inf);

~bind.value(\bass, l.randomBass);
~bind.value(\start2, l.randomSin);
~bind.value(\start1, l.phrase1);

~mono.value(\start1, "noise", l.randomNoise);

~clear.value(\highPop);

~sched.value(\bass, delayTime:8); // MAIN SCHEDULING HERE....
~sched.value(\bass, delayTime:4, afterSymbol:\start1); // THIS ONE DOES NOT WORK....

~stop.value(\start1, atSymbol:\highPop);
~stop.value(\highPop);

~stopAll.value; // stops all pattern players


// ---------------------------------------------------------------------------




// TO DO...
// - some more default phrase material
// - stop individual actions
// - lead ins
// - more sounds (esp with PmonoArtic)
// - practice!!!!!



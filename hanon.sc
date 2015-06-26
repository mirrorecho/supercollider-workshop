
{SinOsc.ar([440,444], mul:0.1)}.play;

(
Pbind(
	\note, Pseq([4,6,11,13,14,4,2], inf),
	\dur, 0.15
).play;
)


e = (freq:440); // an event
(freq:440).play;
f=440.0;(freq:rrand(f,f*2)).play;

(note:[0,3,7].choose).play;

(
"This is the first line".postln;
(8 + 8).postln;
"This is the last line";
)
(
Pbind(
	// solution:
	// \midinote, Prand([60,63,67,70],inf),
	// similar, but always plays all 4 notes before re-shuffling:
	\midinote, Pn(Pshuf([60,63,67,70],1)),
	\dur, 0.1,
	\legato, 0.06
).play;
)


Symbol
Function.new();
Thunk

(
var scale = [60, 60.5, 61, 61.5, 62];

Pbind(
	\midinote, Pseq(scale.reverse, inf),
	\dur, 1/3,
	\amp, 0.6
).play;

)



{ SinOsc.ar(freq:MouseX.kr(100.0,2000.0), mul:MouseY.kr(0.6, 0.0)) }.play;

// #10
Array.fill(11, {rrand(60, 72)} );

// #11
(
Pbind(
	\midinote, Prand( (60..72), inf)
).trace.play;
)








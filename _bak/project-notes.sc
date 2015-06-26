
p = ~player.value;
p.on = true;
p.isOn.value;

~a = [1,2,3,4,5];
~a.removeAt(0);
~a;


// a = Pbind(\degree, Pseq([1, 2, 3, 4]), \dur, 1);
// b = Pbind(\degree, Pseq([10, 11,12,13]), \dur, 0.4);
// c = Ppar([a, b]);
// c.play;
//
// ~add.value('yo', Pbind(\instrument, "blip",\note,Pn(2,12)));
// ~add.value('yo', Pbind(\note,Pn(0)));
//
// ~schedule.value('yo', 10);
// ~patterns;
// ~addAction.value('yo', 14, {"yo".postln;});
// ~addAction.value('yo', 24, {"yo2".postln;});
//
//
// ~stopAll.value;
//
// ~actionFunctions;

// ~p = {var myEvent = (
// 	sound:false,
// 	on:false,
// 	instrument:"blip",
// 	notes:[0,12],
// 	dur:1,
// 	amp:0.5,
// 	isOn: { arg self; self.on; },
// 	patternF: { arg self;
// 		Pbind(
// 			\instrument, Pfunc({self.instrument}),
// 			\note, Pseq(self.notes, inf),
// 			\amp, Pfunc({self.amp}),
// 			\dur, Pfunc({self.dur}),
// 			\resting, Pif(Pfunc({self.sound}), false, Rest),
// 			// \on, Pif(({self.on}), true, nil)
// 		);
// 	},
// 	playF: {arg self;
// 		self.patternF.value.trace.play;
// 	},
// 	// play: pattern.play
// 	);
// 	myEvent.player = self.myEvent.patternF.value.trace.play(~masterTempoClock,quant:1).stop;
// 	myEvent;
// }
// // myEvent['pattern'] = myEvent.patternF.value;
// // myEvent['player'] = myEvent.pattern.trace.play.stop;
// // myEvent;
// // }
// )
//
// p = ~p.value;
// p.player.value;
// p.player.stop;
// p.sound = true;
// p.notes = [0,5,7,12]
//
//
// q = ~p.value;
// p.patternF.value.trace.play;
// p.dur = 1;
// p.sound = true;
// p.amp = 0.1;
// q.patternF.value.trace.play;
// q.sound = true;
//
// p.pattern;
// p.pattern.trace.play;
//
//
// p.on = true;
// p.player.resume;
// p.sound = true;
// p.on = true;
// p.sound = true;
// p.patternF.value;
// p.pattern.trace.play;
// p.playF.value;
// p.player.resume;
// p.player.stop;
// p.sound = true;
// p.dur = 1/4;
// p.on = false;
//


	/*myPlayer.sound = false;
	myPlayer.hello = myPlayer.sound;
	myPlayer.play = {
	};*/
	/*myPlayer.pattern = Pbind(
			\instrument, Pseq(instruments, inf),
			\note, Pseq(notes, inf),
			\amps, Pseq(amps, inf),
			\dur, Pseq(durs, inf),
			\resting, Pif(Pfunc(sound), false, Rest),
			\on, Pif(Pfunc(on), true, nil);*/
/*
		sound: false,
		trace: false,
		on: false,
		startsIn:0,
		notes: [],
		durs: [],

		player: Pbind(
			\instrument, Pseq(instruments, inf),
			\note, Pseq(notes, inf),
			\amps, Pseq(notes, inf),
			\dur, Pseq(durs, inf),
			\resting, Pif(Pfunc(sound), false, Rest),
			\on, Pif(Pfunc(on), true, nil),
	).trace.play(~masterTempoClock,quant:1);
  )
};*/
// }
// )

p = ~player.value;
p.hello;
p.sound = true;



(


// NOTE CAN'T WORK THIS WAY...
// ~noteFunc = {Pseq([\rest], inf)};
// ~durFunc = {Pseq([\rest], inf)};

~seqFactory = {
	arg instruments=["sin"], durs=[1/2], notes=[0,12],on={true},sound={false};
	Pbind(
		\instrument, Pseq(instruments, inf),
		\note, Pseq(notes, inf),
		\amps, Pseq(notes, inf),
		\dur, Pseq(durs, inf),
		\resting, Pif(Pfunc(sound), false, Rest),
		\on, Pif(Pfunc(on), true, nil),
	).trace.play(~masterTempoClock,quant:1);
};

// ~masterPlay = ~masterPattern.play(~masterTempoClock);

// TO DO ... ADD A TEST

// DEFINE "upcoming" ACTIONS
)

// OLD WORKSPACE STUFF:


~aSound=true;
~aOn=true;
~seqFactory.value(on:{~aOn}, sound:{~aSound});
~aSound =false;

~aSound=true;

~bOn=true;
~seqFactory.value(on:{~bOn}, sound:{~bSound}, instruments:["blip"], notes:[-12,0,12,24],durs:[1/4]);
~bSound=false;






~sound.value;

(~sound.value==nil) || (~sound.value==false);

nil==nil;



a.keys;
a = (hi:\sausage, yo:\sausagesssss);
(
// START ALWAYS-RUNNING SYNTHS, PBINDS, ETC
~masterOut = Synth("masterOut");

~actions = [];
// may be better to convert actions to an Event (psuedo-object)
//~actions = ();
//~actions.keys.do({arg key; key.postln;});

//~patterns = ();

~players = ();

~sched = { arg symbol=\default, delayTime=4, afterSymbol=nil;
	if (afterSymbol==nil,{
		~actions = ~actions ++ [[delayTime, symbol]];
		"SCHEDULING '" ++ symbol ++ "' in:" + delayTime;
		}, {
			~players[afterSymbol].schedules = ~players[symbol].schedules ++ [[delayTime, symbol]];
		}
	);
};

~add = { arg symbol=\default, eventPattern;
	if ( ~players[symbol] == nil, {
		~players[symbol] = ();
		~players[symbol].eventPatterns = [];
		~players[symbol].stops = [];
		~players[symbol].schedules = [];
		~players[symbol].player = nil; // just to remember the structure
	});
	~players[symbol].eventPatterns = ~players[symbol].eventPatterns ++ eventPattern;
	"ADDED" + eventPattern + "to: '" ++ symbol ++ "'";
};

~bind = {arg symbol=\default, args;
	~add.value(symbol, Pbind(*args));
};

~mono = {arg symbol=\default, instrument="noise", args;
	~add.value(symbol, PmonoArtic(instrument, *args));
};

~clear = { arg symbol=\default;
	if( ~patterns[symbol] != nil, {~patterns[symbol] = [] });
	"CLEARED all patterns from: '" ++ symbol ++ "'";
};

~stopAll = {
	~players.keys.do({ arg key; ~players[key].player.stop; });
	"STOPPING ALL RUNNING PATTERNS";
};

~stop = { arg stopSymbol=\default, atSymbol=nil;
	if(atSymbol==nil, {
		~players[stopSymbol].player.stop;
		}, {
			~players[atSymbol].stops = ~players[atSymbol].stops ++ stopSymbol;
		}
	);
	"STOPPING: '" ++ stopSymbol ++ "'";
};

// just in case an earlier instance is running...
~stopAll.value;
if(~actionPlayer!=nil, {~actionPlayer.stop;});

~lick = {arg symbol=\default, instrument="blip", dur=1/2, note=0, freqMul=1, amp=0.8, ampMul=0.6, number=4, repeats=1;
	var freqArray, ampArray;
	freqArray = Array.geom(number, (note + 60).midicps, freqMul);
	ampArray = Array.geom(number, amp, ampMul);
	~add.value(symbol, Pbind(
		\instrument, instrument,
		\dur, dur,
		\freq, Pseq(freqArray, repeats),
		\amp, Pseq(ampArray, repeats),
	));
};

// player with pbind running function every beat to evaluate "actions":
~actionPlayer = Pbind(
	\dur, 1,
	\type, \rest,
	\actions, Pfunc({
		~actions.do({arg action, counter;
			action.postln;
			if( action[0]==0,
				{
					// starting/adding any new event stream players (with Pspawner) for any action starting now!
					~players[action[1]].player = Pspawner({ arg sp;
						~players[action[1]].eventPatterns.do({ arg pattern;
							sp.par(pattern);
						});
					}).play(~masterTempoClock,quant:1);
					// stopping any other action that stops when this one starts!
					~players[action[1]].stops.do(
						{arg stopSymbol; ~players[stopSymbol].player.stop;}
					);
					// schedule any things after this:
					~players[action[1]].schedules.do(
						{arg schedArray; ~sched.value(schedArray[0], schedArray[1]);}
					);
					// now clear the action!
					~actions.removeAt(counter);
				},
				{action[0] = action[0]-1;}
			)
		});
	})
).play(~masterTempoClock,quant:1);

"-------- STARTED ACTION PLAYER --------"
)

// TO DO ... SCHEDULE SWELL...
// SCHEDULE ON CYCLE START OF ANOTHER ACTION


	
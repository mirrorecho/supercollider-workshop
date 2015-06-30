

`[1,2,3,4,5,6];
b = (array:[1,2,3,4,5,6]);

b.class;

c = `[0];
c.class;

a = 5;
5.asRef;

y = `0;
z = y + 14;
z.value;
x = 0;
z = x + 14;
z.value;

~yo = "ho";
~x = { RLPF.ar(Impulse(4) * 20, [850,950], 0.2, mul:0.1) };
~x.index;
~x.bus;

p = ProxySpace.push(s);
currentEnvironment === p;


Environment

currentEnvironment.postln;

x = currentEnvironment;
x.know = true;
x.yo;


z = {w} + 10;
z.value;
w=4;
z.class;

Environment;

y = NodeProxy.audio(s,20);
y.play;
y.source = y.sin * 0.2;

ProxySpace

Pdef
Pdefn
PbindProxy
Pbindef

Function

~z2 = {LFNoise0.kr([1,2,3,4]) };
~z2.bus.postln;

~k = 0.5;
~k.class;
~k.bus.postln;
~a2.ar(3);
~a2.bus;
~a2;
~yoyoyo.class;
p.push;
p === currentEnvironment;
p.pop;
p.clear;
currentEnvironment;

~y.ar;
~z = (~y * pi).sin * 0.1 * {LFSaw.kr(LFNoise1.kr(0.1!3).sum * -18).max(0.2) };

~y[1] = {Saw.ar([400,401.3], 0.4) };
~y[0] = {Saw.ar([300, 301], 0.4) };

~y[1] = nil;

f = {|num|num}
f.postcs;

~y.objects[0].source.postcs;
Order.class;
Meta_Order.class;
Function.class;
Class.class.class.class.class;

~y;

o = Order[];
o[27] = "yo";
o[27];
o[1];










	
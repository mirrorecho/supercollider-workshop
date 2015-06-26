// SC language is a pure object oriented language

// SC classes always start with capital letter

// SC class tree is huge

// when to create a new class? (typically wouldn't do this for bread & butter stuff)

// sclang = interpreter
// scsynth = server
// ... comunicates via OSC

// server doesn't know about variables, conditions, etc. that are parts of the language (it just knows about unit generators

// rebooting interpreter cleans up entire environment, etc.

// cannot add a class in realtime (entire class library loads when interpreter starts)

// can also pull up documentation for messages, e.g. pull help on this
scramble([1,2,3,4,5]);

Method
Platform.helpDir;

// STRINGS vs SYMBOLS
// string:
"I am a string";
// symbol:
\IAmASymbol;
// or is also a symbol:
'IAmASymbol';
// string concatenation... + adds space, ++ does not:
"yo:" + "another string";
"yo:" ++ "another string";


n = Nil.new;
n;
Nil.browse;

a = [1,2,3,4].add(10);
a.mirror;
a.reverse;

t = Array.new;
t;
t.class;
p = new(t.class);
p.add(1);

// FUNCTIONAL NOTATION:
// these are the same:
scramble([1,2,3,4,5,6]);
[1,2,3,4,5,6].scramble;


// this is possible, but VERY not standard
play( {ar(SinOsc, mul:0.1)});
// more standard to do this:
p = Pbind(\degree, 0);
p = Pbind.new(\degree, 0);
p = new(Pbind, \degree, 0);

// also equivalent
5.dup(20);
dup(5, 20);

// also equivalent
100.0.rand.round(0.01).dup(4);
dup(round(rand(100.0), 0.01),4);

//FUNCTIONAL NOTATION:
// compare:
exprand(1,100).dup(5);
{ exprand(1,100); }.dup(5); // (so function implements dup in a sepecial way)...

~raining = true;
~raining = false;

//CONDITIONALS:
if(~raining, {"bring an umbrella"}, {"go to beach"});
// note that the two arguments are actually functions!
// this is standard functional notation... could also use object notation! for example:
~raining.if({"bring an umbrella"}, {"go to beach"});

// case msg with syntactic sugar to remove:
(
case
{ ~raining } { 1 }
{2 == 2} { 2 };
)

Collection

(
case( *[
	{~raining}: {1},
	{2 == 2}: {2}
	]
);
)

// FUNCTIONS:
f = {arg num1=1, num2=2; num1 + num2;};
// can also do this:
(
f = { | num1=1, num2=2 |
	var myNum = rrand(1, 10);
	num1 + num2 + myNum; // last line is the return value...
};
)

f.value(4);
f.help;
2.help;
true.help;
value(f);
// note this works ("yo" argument does nothing)... extra params go nowhere
value(f, 5, 7, "yo");

// note that all objects (I think) implement value:
4.value;


// note that this doesn't work (because now it's trying to send msg "f" to an Integer 7!)
f(7);
// but this does:
f.(7);
// as does this...
f.();

// MORE ARRAYS:
Collection
Array.series(10,0,-0.1);
x = Array.geom(1000,1,1.01);
x.size;
x.at(4);
at(x, 4);
x[4];
[1,2,3,4,5,6,7,8][2];
Array.fill(10, {rrand(44,55)} );
// compare with:
Array.fill(10, rrand(44,55));
Array.fill(9, { arg counter; counter ** 2});
a = Array.newClear(9);
// same as:
a = nil.dup(9);
a.put(0, 99);
a[1] = 88;
// OR ! is the same AGAIN as dupe... !!!
a = nil!9;
(2..6);

a = Array.fill(10, {arg num; 440 * (num+1);});
a.do({ arg item, counter; (item + counter).postln;}); // note do always passes these two item and counter arguments by default
// also look into "collect"






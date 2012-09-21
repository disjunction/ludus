Reyode.Word = function(sign, spelling) {
	this.sign = sign;
	this.spelling = spelling;
};

Reyode.Word.fromArray = function(data) {
	return new Reyode.Word(data[0], data[1]);
};
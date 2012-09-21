Reyad.Word = function(sign, spelling) {
	this.sign = sign;
	this.spelling = spelling;
}

Reyad.Word.fromArray = function(data) {
	return new Reyad.Word(data[0], data[1]);
}
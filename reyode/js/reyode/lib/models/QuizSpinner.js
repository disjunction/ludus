Reyode.QuizSpinner = function(id, description, variants, correctAnswer) {
	this.id = id;
	this.description = description;
	this.variants = variants;
	this.correctAnswer = correctAnswer;
};

// DYNAMIC PART

Reyode.QuizSpinner.prototype.draw = function() {
	var result = "<p>" + this.description + "</p>";
	for (var i in this.variants) {
		result += '<input type="button" class="variant" value="' + this.variants[i] + '" onclick="clickVariant(this)" />';
	}
	return result;
};

// STATIC PART
Reyode.QuizSpinner.fromArray = function(data) {
	return new Reyode.QuizSpinner(data[0], data[1], data[2], data[3]);
};
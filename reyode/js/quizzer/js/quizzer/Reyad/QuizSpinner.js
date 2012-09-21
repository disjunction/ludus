Reyad.QuizSpinner = function(description, variants, correctAnswer) {
	this.description = description;
	this.variants = variants;
	this.correctAnswer = correctAnswer;
}

// DYNAMIC PART

Reyad.QuizSpinner.prototype.draw = function() {
	var result = "<p>" + this.description + "</p>";
	for (var i in this.variants) {
		result += '<input type="button" class="variant" value="' + this.variants[i] + '" onclick="clickVariant(this)" />';
	}
	return result;
}

// STATIC PART

Reyad.QuizSpinner.fromArray = function(data) {
	return new Reyad.QuizSpinner(data[0], data[1], data[2]);
}
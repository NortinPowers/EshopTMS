function CustomValidation() {
    this.invalidities = [];
    this.validityChecks = [];
}

CustomValidation.prototype = {
    addInvalidity: function (message) {
        this.invalidities.push(message);
    },
    getInvalidities: function () {
        return this.invalidities.join('. \n');
    },
    checkValidity: function (input) {
        for (var i = 0; i < this.validityChecks.length; i++) {
            var isInvalid = this.validityChecks[i].isInvalid(input);
            if (isInvalid) {
                this.addInvalidity(this.validityChecks[i].invalidityMessage);
            }
            var requirementElement = this.validityChecks[i].element;
            if (requirementElement) {
                if (isInvalid) {
                    requirementElement.classList.add('invalid');
                    requirementElement.classList.remove('valid');
                } else {
                    requirementElement.classList.remove('invalid');
                    requirementElement.classList.add('valid');
                }
            }
        }
    }
};
var nameValidityChecks = [
    {
        isInvalid: function (input) {
            return input.value.length < 3;
        },
        invalidityMessage: 'This input needs to be at least 4 characters',
        element: document.querySelector(
            'label[for="name"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function (input) {
            var illegalCharacters = input.value.match(/[^a-zA-Z]/g);
            return illegalCharacters ? true : false;
        },
        invalidityMessage: 'Only letters are allowed',
        element: document.querySelector(
            'label[for="name"] .input-requirements li:nth-child(2)')
    }
];
var surnameValidityChecks = [
    {
        isInvalid: function (input) {
            return input.value.length < 3;
        },
        invalidityMessage: 'This input needs to be at least 4 characters',
        element: document.querySelector(
            'label[for="surname"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function (input) {
            var illegalCharacters = input.value.match(/[^a-zA-Z]/g);
            return illegalCharacters ? true : false;
        },
        invalidityMessage: 'Only letters are allowed',
        element: document.querySelector(
            'label[for="surname"] .input-requirements li:nth-child(2)')
    }
];
function checkInput(input) {
    input.CustomValidation.invalidities = [];
    input.CustomValidation.checkValidity(input);
    if (input.CustomValidation.invalidities.length == 0 && input.value != '') {
        input.setCustomValidity('');
    } else {
        var message = input.CustomValidation.getInvalidities();
        input.setCustomValidity(message);
    }
}

var nameInput = document.getElementById('name');
var surnameInput = document.getElementById('surname');
nameInput.CustomValidation = new CustomValidation();
nameInput.CustomValidation.validityChecks = nameValidityChecks;
surnameInput.CustomValidation = new CustomValidation();
surnameInput.CustomValidation.validityChecks = surnameValidityChecks;
var inputs = document.querySelectorAll('input:not([type="submit"])');
var submit = document.querySelector('input[type="submit"]');
for (var i = 0; i < inputs.length; i++) {
    inputs[i].addEventListener('keyup', function () {
        checkInput(this);
    });
}
submit.addEventListener('click', function () {
    for (var i = 0; i < inputs.length; i++) {
        checkInput(inputs[i]);
    }
});

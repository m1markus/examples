
console.log("parsing jscript.js")

document.addEventListener("DOMContentLoaded", onPageLoad)

class Person {
    constructor(firstname, familyname) {
        this.firstname = firstname
        this.familyname = familyname

        this.greeting = function() {
            return "Hello... firstname=" + this.firstname + " familyname=" + this.familyname
        }
    }
}

function onPageLoad() {
    console.log("in onPageLoad()")

    let button1 = document.getElementById("button-1")
    button1.addEventListener("click", onButtonClick)
}

function logToConsole(textMessage) {
    console.log(textMessage)
}

function onButtonClick(evt) {
    evt.preventDefault()
    console.log("onButtonClick ", evt.target)

    let me = new Person("Markus", "Müller")
    console.log("new Person created... me=" + me.greeting())
    console.log("only firstname=" + me.firstname)
}

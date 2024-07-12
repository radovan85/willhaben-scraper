function verifyCarAdded(){
	var carAdded = document.getElementById(`carAdded`).value;
	if(carAdded === "true"){
		emitAlertSound();	
	}
}

function emitAlertSound() {
    var context = new (window.AudioContext || window.webkitAudioContext)();
    var oscillator = context.createOscillator();
    var gainNode = context.createGain();

    oscillator.connect(gainNode);
    gainNode.connect(context.destination);

    oscillator.type = 'sine';
    oscillator.frequency.setValueAtTime(440, context.currentTime); // A4 note
    gainNode.gain.setValueAtTime(1, context.currentTime);

    oscillator.start();
    setTimeout(function() {
        oscillator.stop();
    }, 500); // Emit sound for 500ms
};

verifyCarAdded();
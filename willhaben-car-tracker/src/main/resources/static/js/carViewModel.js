var cachedCars = null; 

function CarViewModel(data) {
    var self = this;
    
    self.price = ko.observable(data.price);
    self.location = ko.observable(data.location);
    self.country = ko.observable(data.country);
    self.brand = ko.observable(data.brand);
    self.model = ko.observable(data.model);
    self.mileage = ko.observable(data.mileage);
    self.address = ko.observable(data.address);
    self.postcode = ko.observable(data.postcode);
    self.state = ko.observable(data.state);
    self.district = ko.observable(data.district);
    self.carYear = ko.observable(data.carYear);
    self.fuel = ko.observable(data.fuel);
    self.transmission = ko.observable(data.transmission);
    self.carImageUrl = ko.observable(data.carImageUrl);
    self.engine = ko.observable(data.engine);
    self.published = ko.observable(data.published);
    self.description = ko.observable(data.description);
    self.seoUrl = ko.observable(data.seoUrl);
}

var carListViewModelInstance = null;

function CarListViewModel() {
    var self = this;
    self.cars = ko.observableArray([]);
    self.retryDelay = ko.observable(60000);
    self.maxRetries = ko.observable(0);
    var intervalId;

    self.loadCars = async function() {
        if (self.maxRetries() >= 5) {
            console.log('Previše pokušaja, prekidam učitavanje.');
            return;
        }

        try {
            const response = await axios.get('http://localhost:8080/api/cars');
            cachedCars = response.data; 
            self.cars(cachedCars.map(item => new CarViewModel(item)));
            self.maxRetries(0);
            self.retryDelay(60000); 

            self.checkIfCarAdded();
        } catch (error) {
            if (error.response && error.response.status === 429) {
                console.warn('Previše zahteva, čekam pre ponovnog pokušaja...');
                self.maxRetries(self.maxRetries() + 1);
                self.retryDelay(self.retryDelay() * 2); 
            } else {
                console.error('Greška pri učitavanju automobila:', error);
            }
        }

        self.resetInterval();
    };

    self.checkIfCarAdded = async function() {
        try {
            const response = await axios.get('http://localhost:8080/api/cars/isCarAdded');
            if (response.data === true) {
                emitAlertSound();
            }
        } catch (error) {
            console.error('Greška pri proveri da li je dodat novi auto:', error);
        }
    };

    self.startInterval = function() {
        if (!intervalId) {
            intervalId = setInterval(self.loadCars, self.retryDelay());
        }
    };

    self.resetInterval = function() {
        clearInterval(intervalId);
        intervalId = setInterval(self.loadCars, self.retryDelay());
    };

    self.stopInterval = function() {
        clearInterval(intervalId);
        intervalId = null;
    };

    if (cachedCars) {
        self.cars(cachedCars.map(item => new CarViewModel(item))); 
    }
    self.loadCars().then(self.startInterval);
}

function loadNewContent() {
    var contentDiv = document.getElementById('axiosLoadedContent');
    if (contentDiv) {
        ko.cleanNode(contentDiv); 
        if (!carListViewModelInstance) {
            carListViewModelInstance = new CarListViewModel();
            ko.applyBindings(carListViewModelInstance, contentDiv);
        } else {
            ko.applyBindings(carListViewModelInstance, contentDiv);
        }
    } else {
        console.error('Element sa ID-em `axiosLoadedContent` nije pronađen.');
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
}


loadNewContent();

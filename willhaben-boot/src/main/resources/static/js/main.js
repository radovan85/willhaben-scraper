let retryDelay = 5000; // Početno kašnjenje između pokušaja u milisekundama
let maxRetries = 12; // Maksimalan broj pokušaja

function redirectUrlPath(path, retries = 0) {
    axios.get(path)
        .then(response => {
			retryDelay = 5000;
            document.getElementById(`axiosLoadedContent`).innerHTML = response.data;
        })
        .catch(error => {
            console.error(`Error loading home page:`, error);
            if (error.response && error.response.status === 429 && retries < maxRetries) {
                console.warn('Too many requests, waiting before retrying...');
                setTimeout(() => redirectUrlPath(path, retries + 1), retryDelay);
                // Povećajemo vreme čekanja za naredni pokušaj
                retryDelay *= 2; // Možete dodati i neki maksimalni limit ako želite
            }
        });
}

window.onload = function() {
    setInterval(function() {
        console.time('redirectAllCarsTime');
        redirectAllCars();
        console.timeEnd('redirectAllCarsTime');
    }, retryDelay); // Poziva funkciju na svakih 5000 milisekundi (5 sekundi)
};

function redirectAllCars() {
    redirectUrlPath(`/willhaben-boot2/mvc/cars/allCars`);
}

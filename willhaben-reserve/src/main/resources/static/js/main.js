

window.onload = function() {
    setInterval(function() {
        redirectAllCars();
    }, 1000);
};

function redirectAllCars() {
    redirectUrlPath(`/mvc/cars/allCars`);
}

function redirectUrlPath(path) {
	axios.get(path)
		.then(response => {
			document.getElementById(`axiosLoadedContent`).innerHTML = response.data;
		})
		.catch(error => {
			console.error(`Error loading home page:`, error);
		});
};

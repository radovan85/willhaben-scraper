
function redirectAllCars() {
	redirectUrlPathWithScript(`/mvc/cars/allCars`);
}


window.onload = redirectHome;

function redirectHome() {
	redirectUrlPath(`/home`);
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

function redirectUrlPathWithScript(path) {
	axios.get(path)
		.then(response => {
			var contentDiv = document.getElementById(`axiosLoadedContent`);
			contentDiv.innerHTML = response.data;

			var scripts = contentDiv.querySelectorAll(`script`);
			scripts.forEach((oldScript) => {
				var newScript = document.createElement(`script`);
				newScript.type = `text/javascript`;
				if (oldScript.src) {
					newScript.src = oldScript.src;
				} else {
					newScript.textContent = oldScript.textContent;
				}
				document.body.appendChild(newScript);
				oldScript.parentNode.removeChild(oldScript);
			});
		})
		.catch(error => {
			console.error(`Error loading login page:`, error);
		});
};



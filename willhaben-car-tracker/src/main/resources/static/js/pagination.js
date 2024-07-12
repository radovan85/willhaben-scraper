var carsPerPage = 8;
var currentPage = 1;
var carCards = document.querySelectorAll('.car-card');
var totalPages = Math.ceil(carCards.length / carsPerPage);

function showPage(page) {
	carCards.forEach((car, index) => {
		car.style.display = (index >= (page - 1) * carsPerPage && index < page * carsPerPage) ? 'block' : 'none';
	});
	document.getElementById('pageNumber').innerText = `Page ${page} of ${totalPages}`;
	document.getElementById('prevPage').style.display = page === 1 ? 'none' : 'inline';
	document.getElementById('nextPage').style.display = page === totalPages ? 'none' : 'inline';
}

function prevPage() {
	if (currentPage > 1) {
		currentPage--;
		showPage(currentPage);
	}
}

function nextPage() {
	if (currentPage < totalPages) {
		currentPage++;
		showPage(currentPage);
	}
}

showPage(currentPage);
let retryDelay = 60000; 
let maxRetries = 5; 

function cacheData(key, data) {
    localStorage.setItem(key, JSON.stringify(data));
    localStorage.setItem(key + '_timestamp', Date.now());
}

function getCachedData(key, maxAge) {
    const cachedData = localStorage.getItem(key);
    const timestamp = localStorage.getItem(key + '_timestamp');

    if (cachedData && timestamp && (Date.now() - timestamp < maxAge)) {
        return JSON.parse(cachedData);
    }

    return null;
}

function redirectUrlPathWithScript(path, retries = 0) {
    const cachedData = getCachedData(path, retryDelay);

    if (cachedData) {
        var contentDiv = document.getElementById('axiosLoadedContent');
        contentDiv.innerHTML = cachedData;
        loadScripts(contentDiv);
        return;
    }

    axios.get(path)
        .then(response => {
            var contentDiv = document.getElementById('axiosLoadedContent');
            contentDiv.innerHTML = response.data;
            cacheData(path, response.data);
            loadScripts(contentDiv);
            retryDelay = 5000; 
        })
        .catch(error => {
            console.error('Error loading home page:', error);
            if (error.response && error.response.status === 429 && retries < maxRetries) {
                console.warn('Too many requests, waiting before retrying...');
                setTimeout(() => redirectUrlPathWithScript(path, retries + 1), retryDelay);
                retryDelay *= 2; 
            }
        });
}


function loadScripts(contentDiv) {
    var scripts = contentDiv.querySelectorAll('script');
    scripts.forEach((oldScript) => {
        var newScript = document.createElement('script');
        newScript.type = 'text/javascript';
        if (oldScript.src) {
            newScript.src = oldScript.src;
        } else {
            newScript.textContent = oldScript.textContent;
        }
        document.body.appendChild(newScript);
        oldScript.parentNode.removeChild(oldScript);
    });
}

window.onload = function() {
    setInterval(redirectAllCars, retryDelay); 
};

function redirectAllCars() {
    redirectUrlPathWithScript('/mvc/cars/allCars');
}

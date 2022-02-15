let token, pin, url, hash, xhr = new XMLHttpRequest();

function deleteShort() {
    url = new URL('http://localhost:8080/deleteShortLink');
    pin = document.getElementById("deletePin").value
    hash = document.getElementById("hashForDelete").value
    url.searchParams.set('hash', hash);
    url.searchParams.set('pin', pin);
    makeAndSendRequest(url, 'DELETE')
}

function updateAccessToken() {
    url = new URL('http://localhost:8080/api/auth/token');
    console.log("Acc")
    url.searchParams.set('refreshToken', document.getElementById("refreshToken").value);
    makeAndSendRequest(url, 'POST')
}

function updateAllToken() {
    url = new URL('http://localhost:8080/api/auth/refresh');
    console.log("All")
    url.searchParams.set('refreshToken', document.getElementById("refreshToken").value);
    makeAndSendRequest(url, 'POST')
}

function makeAndSendRequest(URL, method) {
    token = document.getElementById("accessToken").value;
    xhr = new XMLHttpRequest();
    xhr.open(method, URL, false);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    if (xhr.status !== 200) {
        alert(xhr.status + ': ' + xhr.statusText); // пример вывода: 404: Not Found
    } else {
        alert(xhr.responseText); // responseText -- текст ответа.
    }
}

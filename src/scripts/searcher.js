(function () {
    searcher();
    let lastInf;

    function getInf() {
        let search = document.getElementById("search");
        return search.value;
    }

    function getType() {
        let e = document.getElementById("type");
        return e.options[e.selectedIndex].value;
    }

    function searcher() {
        let type = getType();
        let inf = getInf();
        if (inf !== "") {
            let scrollHeight = getScrollHeight();
            if (inf !== lastInf || inBottom(scrollHeight)) {
                if (type !== "Book by Genre") {
                    if (type === "Book by partial coincidence") {
                        type = "partial";
                    } else {
                        type = "full";
                    }
                }
                window.param = getResult(type, inf);
                lastInf = inf;
            } else {
                window.param = null;
            }
        } else {
            window.param = null;
        }
        setTimeout(function () {
            searcher()
        }, 500);
    }

    function getResult(type, inform) {
        let table = document.getElementById("table");
        let rowsNumber = table.rows.length;
        let xhr = new XMLHttpRequest();
        open(xhr, type, inform, rowsNumber);
        if (xhr.readyState !== 0) {
            xhr.send();
            if (xhr.status === 200) {
                let json = JSON.parse(xhr.responseText);
                if (json["_embedded"] != null) {
                    return json["_embedded"]["bookModelList"];
                }
            } else {
                alert(xhr.status + ': ' + xhr.statusText);
            }
        }
    }

    function open(xhr, type, inform, rowsNumber) {
        if (type !== "Book by Genre") {
            if (rowsNumber === 0) {
                xhr.open('GET', 'http://localhost:8080/books?limit=24&offset=0&title=' + inform + '&type=' + type, false);
            } else {
                xhr.open('GET', 'http://localhost:8080/books?limit=4&offset=' + rowsNumber * 4 + '&title=' + inform + '&type=' + type, false);
            }
        } else {
            let genreId = getGenreId(inform);
            if (genreId !== 0) {
                if (rowsNumber === 0) {
                    xhr.open('GET', 'http://localhost:8080/book_genre/books/' + genreId + '?limit=24&offset=0', false);
                } else {
                    xhr.open('GET', 'http://localhost:8080/book_genre/books/' + genreId + '?limit=4&offset=' + rowsNumber * 4, false);
                }
            }
        }
    }

    function getGenreId(genreName) {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8080/genres/' + genreName, false);
        xhr.send();
        if (xhr.status === 200) {
            let json = JSON.parse(xhr.responseText);
            if (json != null) {
                return json["genreId"];
            }
        } else {
            alert(xhr.status + ': ' + xhr.statusText);
        }
    }
})();
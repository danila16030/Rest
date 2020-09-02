(function a() {
    positioner();
    displayResult = debounce(displayResult, 500);

    function positioner() {
        let table = document.getElementById("table");
        let rowsNumber = table.rows.length;
        let scrollHeight = getScrollHeight();
        if (getInf() === "") {
            if (rowsNumber < 6 || inBottom(scrollHeight)) {
                let row = table.insertRow(rowsNumber);
                let resultList = getRow(rowsNumber);
                for (let item in resultList) {
                    let cell = row.insertCell(item - 1);
                    addCell(resultList[item], cell);
                }
            }
        } else {
            let result = window.param;
            if (result != null) {
                displayResult(result, table);
            }
        }
        setTimeout(function () {
            positioner()
        }, 0);
    }

    function displayResult(result, table) {
        let row;
        let cell;
        let rowsNumber = 0;
        let cellNumber = 0;
        for (let item in result) {
            if (item === 0 || item % 4 === 0) {
                rowsNumber = table.rows.length;
                row = table.insertRow(rowsNumber);
                cellNumber = 0;
            }
            cell = row.insertCell(cellNumber - 1);
            addCell(result[item], cell);
            ++cellNumber;
        }
    }

    function addCell(item, cell) {
        let newTd = document.createElement("td");
        newTd.style.backgroundColor = 'white';
        newTd.style.tableLayout = 'fixed';
        newTd.style.height = "400";
        newTd.style.width="250";
        newTd.style.position = "relative";
        newTd.innerHTML = "<img src='css/resources/lord.jpg' alt='not found' width='100%' height='50%'>"
        newTd.innerHTML += "<p class='bold small-text table-text table-name'  >" + item["title"] + "</p>";
        newTd.innerHTML += "<img src='css/resources/icons/like.svg' alt='like' class='table-like'>";
        newTd.innerHTML += "<p class='small-text table-description table-text'>" + item["description"] + "</p>";
        newTd.innerHTML += "<hr  size='1' class='light-grey' />";
        newTd.innerHTML += "<p class='bold semi-small table-text table-name'  >" + item["price"] + '$' + "</p>";
        newTd.innerHTML += "<button type='submit' class='padding margin table-btn' >Add to card</button>";
        cell.appendChild(newTd);
    }

    function getRow(rowNumber) {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8080/books?limit=4&offset=' + rowNumber * 4 + '&type=all', false);
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

    function getInf() {
        let search = document.getElementById("search");
        return search.value;
    }

    function debounce(f, ms) {

        let isCooldown = false;

        return function () {
            if (isCooldown) return;

            f.apply(this, arguments);

            isCooldown = true;

            setTimeout(() => isCooldown = false, ms);
        };

    }
})
();